package modell.players;

/**
 * Enumeration of available player tokens for UI representation.
 * Each token combines an emoji icon with a human-readable display name.
 * <p>
 * Available tokens:
 * <ul>
 *   <li>DEFAULT: Generic dice icon</li>
 *   <li>CAR: Car icon</li>
 *   <li>HAT: Top hat icon</li>
 *   <li>BOAT: Sailboat icon</li>
 *   <li>DOG: Dog icon</li>
 *   <li>CAT: Cat icon</li>
 *   <li>ROCKET: Rocket icon</li>
 *   <li>CROWN: Crown icon</li>
 *   <li>STAR: Star icon</li>
 *   <li>SWORD: Crossed swords icon</li>
 *   <li>BANDANA: Bandana icon</li>
 * </ul>
 *
 * <p>Usage example:
 * <pre>{@code
 * PlayerToken token = PlayerToken.BOAT;
 * System.out.println(token); // prints "⛵ Boat"
 * }</pre>
 *
 * @author didrik
 * @version 1.0
 */
public enum PlayerToken {
  /** Default dice token. */
  DEFAULT("🎲", "Default"),
  /** Car-shaped token. */
  CAR("🚗", "Car"),
  /** Top hat token. */
  HAT("🎩", "Hat"),
  /** Sailboat token. */
  BOAT("⛵", "Boat"),
  /** Dog-shaped token. */
  DOG("🐕", "Dog"),
  /** Cat-shaped token. */
  CAT("🐱", "Cat"),
  /** Rocket-shaped token. */
  ROCKET("🚀", "Rocket"),
  /** Crown-shaped token. */
  CROWN("👑", "Crown"),
  /** Star-shaped token. */
  STAR("⭐", "Star"),
  /** Crossed swords token. */
  SWORD("⚔️", "Sword"),
  /** Bandana-shaped token. */
  BANDANA("🎀", "Bandana");

  private final String emoji;
  private final String displayName;

  /**
   * Creates a new token with the given icon and name.
   *
   * @param emoji the emoji icon for the token
   * @param displayName the human-readable name of the token
   */
  PlayerToken(String emoji, String displayName) {
    this.emoji = emoji;
    this.displayName = displayName;
  }

  /**
   * Returns the emoji icon and display name for this token.
   *
   * @return a combined string of emoji and name, e.g. "⛵ Boat"
   */
  @Override
  public String toString() {
    return emoji + " " + displayName;
  }
}
