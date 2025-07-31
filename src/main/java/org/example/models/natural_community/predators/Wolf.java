package org.example.models.natural_community.predators;

import org.example.enums.Emoji;
import org.example.models.Location;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Predator;
import org.example.models.natural_community.herbivores.Boar;
import org.example.models.natural_community.herbivores.Buffalo;
import org.example.models.natural_community.herbivores.Deer;
import org.example.models.natural_community.herbivores.Duck;
import org.example.models.natural_community.herbivores.Goat;
import org.example.models.natural_community.herbivores.Horse;
import org.example.models.natural_community.herbivores.Mouse;
import org.example.models.natural_community.herbivores.Rabbit;
import org.example.models.natural_community.herbivores.Sheep;
import org.example.utils.SimulationSettings;

import java.util.List;
import java.util.stream.Collectors;

public class Wolf extends Predator {

    public Wolf() {
        super(SimulationSettings.AnimalConfig.WOLF);
    }

    @Override
    protected int getProbabilityOfEating(Animal species) {
        if (species instanceof Horse) return SimulationSettings.EatingChance.WOLF_HORSE;
        if (species instanceof Deer) return SimulationSettings.EatingChance.WOLF_DEER;
        if (species instanceof Rabbit) return SimulationSettings.EatingChance.WOLF_RABBIT;
        if (species instanceof Mouse) return SimulationSettings.EatingChance.WOLF_MOUSE;
        if (species instanceof Goat) return SimulationSettings.EatingChance.WOLF_GOAT;
        if (species instanceof Sheep) return SimulationSettings.EatingChance.WOLF_SHEEP;
        if (species instanceof Boar) return SimulationSettings.EatingChance.WOLF_BOAR;
        if (species instanceof Buffalo) return SimulationSettings.EatingChance.WOLF_BUFFALO;
        if (species instanceof Duck) return SimulationSettings.EatingChance.WOLF_DUCK;
        return 0;
    }

    @Override
    protected List<Animal> getPotentialFood(Location location) {
        return location.getAnimals().stream()
                // Фильтр по конкретным классам-жертвам (все травоядные)
                .filter(a -> a instanceof Rabbit || a instanceof Mouse
                        || a instanceof Goat || a instanceof Sheep
                        || a instanceof Horse || a instanceof Deer
                        || a instanceof Boar || a instanceof Buffalo
                        || a instanceof Duck)
                // Исключаем уже мертвых животных
                .filter(Animal::isAlive)
                .collect(Collectors.toList());
    }

    @Override
    protected Animal createChild() {
        return new Wolf();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.WOLF;
    }
}
