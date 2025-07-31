package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.CaterpillarKiller;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;

public class Mouse extends Herbivore implements CaterpillarKiller {

    public Mouse() {
        super(SimulationSettings.AnimalConfig.MOUSE);
    }

    @Override
    protected Animal createChild() {
        return new Mouse();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.MOUSE;
    }

    @Override
    public int getCaterpillarEatingChance() {
        return SimulationSettings.EatingChance.MOUSE_CATERPILLAR;
    }
}
