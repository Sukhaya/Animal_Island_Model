package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.CaterpillarKiller;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;

public class Duck extends Herbivore implements CaterpillarKiller {

    public Duck() {
        super(SimulationSettings.AnimalConfig.DUCK);
    }

    @Override
    protected Animal createChild() {
        return new Duck();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.DUCK;
    }

    @Override
    public int getCaterpillarEatingChance() {
        return SimulationSettings.EatingChance.DUCK_CATERPILLAR;
    }
}
