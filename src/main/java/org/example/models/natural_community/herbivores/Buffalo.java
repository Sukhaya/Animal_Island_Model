package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;

public class Buffalo extends Herbivore {

    public Buffalo() {
        super(SimulationSettings.AnimalConfig.BUFFALO);
    }

    @Override
    protected Animal createChild() {
        return new Buffalo();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.BUFFALO;
    }
}
