package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;

public class Rabbit extends Herbivore {

    public Rabbit() {
        super(SimulationSettings.AnimalConfig.RABBIT);
    }

    @Override
    protected Animal createChild() {
        return new Rabbit();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.RABBIT;
    }
}
