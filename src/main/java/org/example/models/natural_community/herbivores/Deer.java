package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;

public class Deer extends Herbivore {

    public Deer() {
        super(SimulationSettings.AnimalConfig.DEER);
    }

    @Override
    protected Animal createChild() {
        return new Deer();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.DEER;
    }
}
