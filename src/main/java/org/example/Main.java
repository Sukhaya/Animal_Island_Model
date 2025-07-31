package org.example;

import org.example.enums.Emoji;
import org.example.models.Island;
import org.example.simulation.Simulation;
import org.example.utils.SimulationSettings;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1. Инициализация основных компонентов
        Island island = new Island(); // Создание модели острова
        Simulation simulation = new Simulation(island); // Создание движка симуляции

        // 2. Запуск симуляции
        simulation.startSimulation(); // Старт потока выполнения симуляции

        // 3. Тайминг выполнения программы
        try {
            // Перевод минут в миллисекунды:
            // MINUTES * 60 секунд * 1000 миллисекунд
            Thread.sleep(
                    SimulationSettings.SIMULATION_DURATION_MINUTES * 60 * 1000L
            );
        } catch (InterruptedException e) {
            // Обработка прерывания сна потока
            Thread.currentThread().interrupt(); // Восстановление флага прерывания
            System.err.println("Симуляция была прервана досрочно!");
        }

        // 4. Завершающие действия
        simulation.stopSimulation(); // Остановка потока симуляции

        // 5. Визуализация результатов
        System.out.println("\n=== Финальное состояние острова ===");
        //IslandRenderer.render(island); // Вывод ASCII-представления острова
    }
}