package org.example.models.natural_community;

import org.example.enums.Direction;
import org.example.enums.Emoji;
import org.example.models.Island;
import org.example.models.Location;
import org.example.statistic.Statistics;
import org.example.utils.Randomizer;
import org.example.utils.SimulationSettings;
import org.example.utils.SimulationSettings.AnimalConfig;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Animal {

    protected final AnimalConfig config;
    protected double satiety;
    protected AtomicBoolean isAlive = new AtomicBoolean(true);
    protected Location location;

    public Animal(AnimalConfig config) {
        this.config = config;
        this.satiety = config.foodNeeded * SimulationSettings.INITIAL_SATIETY_PERCENT;
    }

    public abstract void eat(Location location);

    public abstract void multiply(Location location);

    public void move(Island island) {
        if (!isAlive.get() || location == null || Randomizer.nextDouble() > 0.7) return;

        // Выбираем случайное направление из enum Direction
        Direction direction = Randomizer.randomEnum(Direction.class);
        // Определяем количество шагов (от 1 до maxSpeed)
        int steps = Randomizer.nextInt(1, config.maxSpeed + 1);

        // Рассчитываем новые координаты с проверкой границ острова
        int newX = Math.max(0, Math.min(location.x + direction.getX() * steps, island.getWidth() - 1));
        int newY = Math.max(0, Math.min(location.y + direction.getY() * steps, island.getHeight() - 1));

        // Получаем новую локацию и проверяем ее
        Location newLocation = island.getLocation(newX, newY);
        if (newLocation != null && newLocation != location) {
            // Удаляем из старой локации
            location.removeAnimal(this);
            // Добавляем в новую
            newLocation.addAnimal(this);
            // Обновляем ссылку на текущую локацию
            location = newLocation;
        }
    }


    public void die() {
        if (isAlive.getAndSet(false)) {
            if (location != null) {
                location.removeAnimal(this);
            }

            // Статистика должна сработать в любом случае
            if (satiety <= 0) {
                // Регистрация смерти от голода
                Statistics.recordDeathByHunger(this);
            } else {
                // Регистрация смерти от хищника
                Statistics.recordDeathByPredation(this);
            }
        }
    }

    public void satiety() { //сытость
        satiety -= config.foodNeeded * SimulationSettings.DAILY_SATIETY_LOSS;
        if (satiety <= 0) die();
    }

    public double getWeight() {
        return config.weight;
    }

    public boolean isAlive() {
        return isAlive.get();
    }

    public Location getLocation() {
        return location;
    }

    public abstract Emoji getEmoji();
}
