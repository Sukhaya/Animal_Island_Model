package org.example.simulation;

import org.example.models.Island;
import org.example.models.Location;
import org.example.models.natural_community.Animal;
import org.example.statistic.Statistics;
import org.example.utils.SimulationSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Simulation {
    private final Island island;
    // Создаю планировщик, который будет выполнять задачи по расписанию с 3 потоками
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    // Создаю пул потоков для выполнения задач, связанных с животными, с использованием механизма "воровства задачи"
    private final ExecutorService animalsExecutor = Executors.newWorkStealingPool();
    private volatile boolean isRunning = false;
    private final AtomicInteger dayCounter = new AtomicInteger(0);


    public Simulation(Island island) {
        this.island = island;
    }

    public void startSimulation() {
        if (isRunning) return;
        isRunning = true;

        // Запускаем планировщик для выполнения метода processDay с заданным интервалом
        scheduler.scheduleAtFixedRate(
                this::processDay, // Метод, который будет вызываться каждый интервал
                0, // Начальная задержка перед первым выполнением (0 миллисекунд)
                SimulationSettings.DAY_DURATION_MS, // Интервал между вызовами метода (из настроек симуляции)
                TimeUnit.MILLISECONDS // Единица времени для интервала
        );
    }

    // Метод для обработки одного дня симуляции
    private void processDay() {
        // Увеличиваем счетчик дней и получаем текущее значение
        int currentDay = dayCounter.incrementAndGet();
        System.out.println("\n=== Today is the Day № " + currentDay + " ==="); // Выводим номер текущего дня в консоль

        // Сначала обновляем рост растений на острове
        int totalPlants = 0; // Переменная для хранения общего количества растений на острове

        // Проходим по всем локациям на острове
        for (Location[] row : island.getLocations()) {
            for (Location location : row) {
                location.growPlants();
                totalPlants += location.getPlantCount();
            }
        }
        Statistics.updatePlants(totalPlants); // Обновляем статистику по количеству растений на острове

        // Затем обрабатываем действия животных
        List<Callable<Void>> tasks = new ArrayList<>(); // Создаем список задач для выполнения в пуле потоков
        // Проходим по всем локациям на острове
        for (Location[] row : island.getLocations()) {
            for (Location location : row) { // Для каждой локации в строке
                // Для каждого животного в локации создаем задачу
                for (Animal animal : location.getAnimals()) {
                    // Добавляем задачу в список

                    tasks.add(() -> { // Лямбда-выражение для создания Callable задачи
                        if (animal.isAlive()) { // Проверяем, живо ли животное
                            if (!animal.isAlive()) return null; // если умерло от голода — не продолжать
                            animal.eat(location); // Животное пытается поесть в текущей локации
                            animal.multiply(location); // Животное пытается размножиться в текущей локации
                            animal.move(island); // Животное перемещается по острову
                            animal.satiety(); // Уменьшаем уровень сытости животного
                        }
                        return null; // Возвращаем null, так как Callable<Void> не возвращает значение
                    });
                }
            }
        }

        // Пытаемся выполнить все задачи в пуле потоков
        try {
            animalsExecutor.invokeAll(tasks); // Выполняем все задачи из списка
        } catch (InterruptedException e) { // Обработка исключения, если поток был прерван
            Thread.currentThread().interrupt(); // Восстанавливаем состояние прерывания
        }

        // Обновляем статистику животных
        int animalCount = 0; // Переменная для хранения общего количества животных на острове
        for (Location[] row : island.getLocations()) {
            for (Location location : row) {
                animalCount += (int) location.getAnimals().stream()
                        .filter(Animal::isAlive)
                        .count();
            }
        }
        Statistics.updateAnimals(animalCount);

        // Печатаем статистику через заданные интервалы
        if (currentDay % SimulationSettings.STATISTICS_PRINT_INTERVAL_DAYS == 0) {
            Statistics.printStatistics(); // Печатаем общую статистику
        }
    }

    // Метод для остановки симуляции
    public void stopSimulation() {
        isRunning = false; // Устанавливаем флаг, указывающий на то, что симуляция остановлена
        scheduler.shutdownNow(); // Останавливаем планировщик, чтобы больше не выполнять задачи
        animalsExecutor.shutdownNow(); // Останавливаем пул потоков для животных, чтобы завершить выполнение всех задач
        System.out.println("\nИтоговое количество дней: " + dayCounter.get()); // Выводим итоговое количество прожитых дней в симуляции
    }
}

/**
 * Дополнительные пояснения по использованию Executors
 * В коде я использую ScheduledExecutorService для управления выполнением задач, которые должны выполняться через регулярные промежутки времени. Этот класс позволяет:
 * Планировать задачи: Мы можем задать начальную задержку перед первым выполнением (в данном случае 0 миллисекунд) и интервал, через который задача будет повторяться.
 * Обеспечивать многопоточность: Используя пул потоков, мы можем выполнять несколько задач одновременно, что особенно важно для симуляции, где много животных и растений взаимодействуют друг с другом.
 * Упрощать управление временем выполнения: Вместо того чтобы вручную управлять потоками и временными задержками, планировщик автоматически вызывает метод processDay, что снижает вероятность ошибок и упрощает код.
 * Использование ExecutorService с WorkStealingPool:
 *
 * Я использую newWorkStealingPool() для создания пула потоков, который оптимально распределяет задачи между доступными потоками. Это имеет несколько преимуществ:
 * Эффективное распределение нагрузки: Если один поток завершает свою задачу, он может "украсть" задачу у другого потока, который может быть занят. Это помогает избежать простаивания потоков и повышает общую производительность.
 * Гибкость: Пул потоков автоматически настраивается в зависимости от нагрузки, что позволяет оптимально использовать ресурсы.
 * Упрощение многопоточности: Пул потоков управляет созданием и завершением потоков, позволяя разработчику сосредоточиться на логике приложения, а не на управлении потоками.
 */