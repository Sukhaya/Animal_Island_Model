package org.example.enums;

import org.example.models.natural_community.Animal;

public enum Emoji {
    BUFFALO ("🐃"),
    BEAR ("🐻"),
    HORSE ("🐎"),
    DEER ("🦌"),
    BOAR ("🐗"),
    SHEEP ("🐑"),
    GOAT ("🐐"),
    WOLF ("🐺"),
    BOA ("🐍"),
    FOX ("🦊"),
    EAGLE ("🦅"),
    RABBIT ("🐇"),
    DUCK ("🦆"),
    MOUSE ("🐁"),
    CATERPILLAR ("🐛"),
    PLANT ("🌱");

    private final String emoji;

    Emoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }

    @Override
    public String toString() {
        return name() + " " + emoji + " ";
    }
}
