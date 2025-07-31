package org.example.models.natural_community.herbivores;

import org.example.enums.Emoji;
import org.example.models.natural_community.Animal;
import org.example.models.natural_community.CaterpillarKiller;
import org.example.models.natural_community.Herbivore;
import org.example.utils.SimulationSettings;
import org.example.utils.SimulationSettings.AnimalConfig;

public class Boar extends Herbivore implements CaterpillarKiller {

    public Boar() {
        super(AnimalConfig.BOAR);
    }

    @Override
    protected Animal createChild() {
        return new Boar();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.BOAR;
    }

    @Override
    public int getCaterpillarEatingChance() {
        return SimulationSettings.EatingChance.BOAR_CATERPILLAR;
    }
}
