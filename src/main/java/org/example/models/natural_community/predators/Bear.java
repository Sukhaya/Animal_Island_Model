package org.example.models.natural_community.predators;

import org.example.enums.Emoji;
import org.example.models.Location;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Predator;
import org.example.models.natural_community.herbivores.*;
import org.example.utils.SimulationSettings;

import java.util.List;
import java.util.stream.Collectors;

public class Bear extends Predator {

    public Bear() {
        super(SimulationSettings.AnimalConfig.BEAR);
    }

    @Override
    protected int getProbabilityOfEating(Animal species) {
        if (species instanceof Boa) return SimulationSettings.EatingChance.BEAR_BOA;
        if (species instanceof Horse) return SimulationSettings.EatingChance.BEAR_HORSE;
        if (species instanceof Deer) return SimulationSettings.EatingChance.BEAR_DEER;
        if (species instanceof Rabbit) return SimulationSettings.EatingChance.BEAR_RABBIT;
        if (species instanceof Mouse) return SimulationSettings.EatingChance.BEAR_MOUSE;
        if (species instanceof Goat) return SimulationSettings.EatingChance.BEAR_GOAT;
        if (species instanceof Sheep) return SimulationSettings.EatingChance.BEAR_SHEEP;
        if (species instanceof Boar) return SimulationSettings.EatingChance.BEAR_BOAR;
        if (species instanceof Buffalo) return SimulationSettings.EatingChance.BEAR_BUFFALO;
        if (species instanceof Duck) return SimulationSettings.EatingChance.BEAR_DUCK;
        return 0;
    }

    @Override
    protected List<Animal> getPotentialFood(Location location) {
        return location.getAnimals().stream()
                .filter(a -> a instanceof Boa || a instanceof Horse
                        || a instanceof Deer || a instanceof Rabbit
                        || a instanceof Mouse || a instanceof Goat
                        || a instanceof Sheep || a instanceof Boar
                        || a instanceof Duck)
                .collect(Collectors.toList());
    }

    @Override
    protected Animal createChild() {
        return new Bear();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.BEAR;
    }
}
