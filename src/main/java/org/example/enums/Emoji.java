package org.example.enums;

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
    PLANT ("🌱"),
    BIRTH ("\uD83C\uDF7C"),
    HUNGER_DEATH("⚰️"),
    DEATH("☠️"),
    ANIMAL_PAW("\uD83D\uDC3E ");

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
