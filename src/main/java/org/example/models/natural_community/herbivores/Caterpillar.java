package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;

public class Caterpillar extends Herbivore {

    public Caterpillar() {
        super(SimulationSettings.AnimalConfig.CATERPILLAR);
    }

    @Override
    protected Animal createChild() {
        return new Caterpillar();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.CATERPILLAR;
    }
}
