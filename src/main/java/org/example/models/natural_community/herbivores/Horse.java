package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;

public class Horse extends Herbivore {

    public Horse() {
        super(SimulationSettings.AnimalConfig.HORSE);
    }

    @Override
    protected Animal createChild() {
        return new Horse();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.HORSE;
    }
}
