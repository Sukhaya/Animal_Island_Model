package org.example.models; // Указываем, что класс принадлежит пакету model

import org.example.utils.SimulationSettings;

public class Island {
    private final int width;
    private final int height;
    private final Location[][] locations;

    public Island() {
        this.width = SimulationSettings.ISLAND_WIDTH;
        this.height = SimulationSettings.ISLAND_HEIGHT;
        this.locations = new Location[width][height];

        initializeLocations();
        spawnInitialAnimals();
    }

    private void initializeLocations() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y] = new Location(x, y);
            }
        }
    }

    private void spawnInitialAnimals() {
        for (SimulationSettings.AnimalConfig config : SimulationSettings.AnimalConfig.values()) {
            for (int i = 0; i < config.getInitialCount(); i++) {
                Location loc = getRandomLocation();
                loc.addAnimal(config.createAnimal());
            }
        }
    }

    public Location getRandomLocation() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        return locations[x][y];
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return locations[x][y];
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Location[][] getLocations() {
        return locations;
    }
}