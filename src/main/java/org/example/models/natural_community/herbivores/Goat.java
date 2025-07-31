package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;

public class Goat extends Herbivore {

    public Goat() {
        super(SimulationSettings.AnimalConfig.GOAT);
    }

    @Override
    protected Animal createChild() {
        return new Goat();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.GOAT;
    }
}
