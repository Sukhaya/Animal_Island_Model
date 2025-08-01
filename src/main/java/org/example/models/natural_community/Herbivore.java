package org.example.models.natural_community;

import org.example.models.Location;
import org.example.models.natural_community.herbivores.Caterpillar;
import org.example.utils.Randomizer;
import org.example.utils.SimulationSettings;

import java.util.List;

public abstract class Herbivore extends Animal {

    public Herbivore(SimulationSettings.AnimalConfig config) {
        super(config);
    }

    protected abstract Animal createChild();

    protected boolean eatPlants(Location location) {
        double needed = config.foodNeeded - satiety; // Определяем недостаток пищи (потребность - сытость)
        if (needed <= 0) return false;
        // Пытаемся поесть растения в локации и получаем количество съеденных растений
        double eaten = location.eatPlants(needed); // Метод в Location, который уменьшает количество растений
        satiety += eaten;
        return eaten > 0; // Возвращаем true, если что-то было съедено, иначе false
    }

    @Override
    public void eat(Location location) {
        if (this instanceof CaterpillarKiller) {
            tryEatCaterpillar(location); // Пробуем есть гусеницу
            return;
        }
        eatPlants(location);
    }

    @Override
    public void multiply(Location location) {
        List<? extends Herbivore> sameSpecies = location.getAnimalsByType(this.getClass()); // Получаем всех травоядных того же типа
        // Если животных больше двух одного вида пробуем размножаться
        if (sameSpecies.size() >= 2 && Randomizer.getProbability(config.reproductionChance * 100)) {
            Animal offspring = createChild(); // Метод должен быть реализован в подклассах
            location.addAnimal(offspring); // Добавляем потомка в локацию (в эту же локацию)
        }
    }

    protected boolean tryEatCaterpillar(Location location) {
        if (!(this instanceof CaterpillarKiller eater)) return false;

        int chance = eater.getCaterpillarEatingChance();
        List<Caterpillar> caterpillars = location.getAnimalsByType(Caterpillar.class);

        // Если гусениц нет, возвращаем false
        if (caterpillars.isEmpty()) return false;

        // Проверяем, удалось ли травоядному поймать гусеницу на основе случайной вероятности
        // Это не является требованием в проекте я дописала его опционально
        if (Randomizer.getProbability(chance)) {
            Caterpillar caterpillar = Randomizer.randomItem(caterpillars);
            // Рассчитываем количество питательных веществ, которое травоядное получит от гусеницы
            double nutrition = Math.min(caterpillar.getWeight(), config.foodNeeded - satiety);
            satiety += nutrition; // Увеличиваем уровень сытости травоядного
            caterpillar.die(); // Уничтожаем гусеницу
            return true; // Возвращаем true, указывая на успешное поедание
        }
        return false; // Возвращаем false, если поедание не удалось
    }
}
