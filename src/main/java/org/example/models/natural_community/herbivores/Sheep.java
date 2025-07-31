package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;

public class Sheep extends Herbivore {

    public Sheep() {
        super(SimulationSettings.AnimalConfig.SHEEP);
    }

    @Override
    protected Animal createChild() {
        return new Sheep();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.SHEEP;
    }
}
