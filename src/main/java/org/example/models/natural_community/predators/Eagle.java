package org.example.models.natural_community.predators;

import org.example.enums.Emoji;
import org.example.models.Location;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Predator;
import org.example.models.natural_community.herbivores.*;
import org.example.utils.SimulationSettings;

import java.util.List;
import java.util.stream.Collectors;

public class Eagle extends Predator {

    public Eagle() {
        super(SimulationSettings.AnimalConfig.EAGLE);
    }

    @Override
    protected int getProbabilityOfEating(Animal species) {
        if (species instanceof Fox) return SimulationSettings.EatingChance.EAGLE_FOX;
        if (species instanceof Rabbit) return SimulationSettings.EatingChance.EAGLE_RABBIT;
        if (species instanceof Mouse) return SimulationSettings.EatingChance.EAGLE_MOUSE;
        if (species instanceof Duck) return SimulationSettings.EatingChance.EAGLE_DUCK;
        return 0;
    }

    @Override
    protected List<Animal> getPotentialFood(Location location) {
        return location.getAnimals().stream()
                .filter(a -> a instanceof Fox || a instanceof Rabbit
                        || a instanceof Mouse || a instanceof Duck)
                .filter(Animal::isAlive)
                .collect(Collectors.toList());
    }

    @Override
    protected Animal createChild() {
        return new Eagle();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.EAGLE;
    }
}
