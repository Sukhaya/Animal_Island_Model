package org.example.models.natural_community.predators;

import org.example.enums.Emoji;
import org.example.models.Location;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.CaterpillarKiller;
import org.example.models.natural_community.Predator;
import org.example.models.natural_community.herbivores.*;
import org.example.utils.SimulationSettings;

import java.util.List;
import java.util.stream.Collectors;

public class Fox extends Predator implements CaterpillarKiller {

    public Fox() {
        super(SimulationSettings.AnimalConfig.FOX);
    }

    @Override
    protected int getProbabilityOfEating(Animal species) {
        if (species instanceof Rabbit) return SimulationSettings.EatingChance.FOX_RABBIT;
        if (species instanceof Mouse) return SimulationSettings.EatingChance.FOX_MOUSE;
        if (species instanceof Duck) return SimulationSettings.EatingChance.FOX_DUCK;
        return 0;
    }

    @Override
    protected List<Animal> getPotentialFood(Location location) {
        return location.getAnimals().stream()
                .filter(a -> a instanceof Rabbit || a instanceof Mouse
                        || a instanceof Duck || a instanceof Caterpillar)
                .filter(Animal::isAlive)
                .collect(Collectors.toList());
    }

    @Override
    protected Animal createChild() {
        return new Fox();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.FOX;
    }

    @Override
    public int getCaterpillarEatingChance() {
        return SimulationSettings.EatingChance.FOX_CATERPILLAR;
    }
}
