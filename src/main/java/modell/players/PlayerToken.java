package modell.players;

public enum PlayerToken {
    DEFAULT("🎲", "Default"),
    CAR("🚗", "Car"),
    HAT("🎩", "Hat"),
    BOAT("⛵", "Boat"),
    DOG("🐕", "Dog"),
    CAT("🐱", "Cat"),
    ROCKET("🚀", "Rocket"),
    CROWN("👑", "Crown"),
    STAR("⭐", "Star"),
    SWORD("⚔️", "Sword"),
    BANDANA("🎀", "Bandana");

    private final String emoji;
    private final String displayName;

    PlayerToken(String emoji, String displayName) {
        this.emoji = emoji;
        this.displayName = displayName;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return emoji + " " + displayName;
    }
}