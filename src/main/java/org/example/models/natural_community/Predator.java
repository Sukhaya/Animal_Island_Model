package org.example.models.natural_community;

import org.example.models.Location;
import org.example.statistic.Statistics;
import org.example.utils.Randomizer;
import org.example.utils.SimulationSettings;

import java.util.List;

public abstract class Predator extends Animal {

    public Predator(SimulationSettings.AnimalConfig config) {
        super(config);
    }

    protected abstract int getProbabilityOfEating(Animal species);

    protected abstract Animal createChild();

    protected abstract List<Animal> getPotentialFood(Location location);

    @Override
    public void eat(Location location) {
        List<Animal> preyList = getPotentialFood(location);

        // Если нет доступной пищи, возвращаем false
        if (preyList.isEmpty()) return;

        // Выбираем случайную жертву из списка потенциальной пищи
        // Используем Randomizer для выбора случайной жертвы
        Animal prey = Randomizer.randomItem(preyList);

        // Получаем вероятность поедания для выбранной жертвы
        int chance = getProbabilityOfEating(prey);

        // Проверяем, удалось ли хищнику поймать жертву на основе случайной вероятности
        if (Randomizer.getProbability(chance)) {
            // Рассчитываем количество питательных веществ, которое хищник получит от жертвы
            double nutrition = Math.min(prey.getWeight(), config.foodNeeded - satiety);
            // Обновляем уровень сытости хищника
            satiety += nutrition;
            prey.die(); // Уничтожаем жертву, вызывая метод die()
            // Записываем факт убийства жертвы в статистику
            Statistics.recordDeathByPredation(prey);
        }
    }

    @Override
    public void multiply(Location location) {
        List<? extends Predator> sameSpecies = location.getAnimalsByType(this.getClass());
        // Если животных больше двух одного вида пробуем размножаться
        if (sameSpecies.size() >= 2 && Randomizer.getProbability(config.reproductionChance * 100)) {
            Animal offspring = createChild(); // Метод должен быть реализован в подклассах
            location.addAnimal(offspring); // Добавляем потомка в локацию (в эту же локацию)
            Statistics.recordBirth(offspring);
        }
    }
}
