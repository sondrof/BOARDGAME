package view.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for managing and loading game resources.
 * Provides centralized access to images, icons, and other visual assets.
 *
 * <p>This class handles:
 * <ul>
 *     <li>Image caching for improved performance</li>
 *     <li>Resource preloading for different game scenes</li>
 *     <li>Category-specific resource access (players, tiles, buttons, etc.)</li>
 *     <li>Fallback handling for missing resources</li>
 * </ul>
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class ResourceLoader {

  private static final String PLAYER_PATH = "/player/";
  private static final String TILE_PATH = "/tile/";
  private static final String BUTTON_PATH = "/button/";
  private static final String BACKGROUND_PATH = "/background/";
  private static final String DICE_PATH = "/dice/";

  private static final Map<String, Image> imageCache = new HashMap<>();

  private ResourceLoader() {
    // Private constructor to prevent instantiation
  }

  /**
   * Core method for loading images from the resource path.
   * Implements caching and fallback handling for missing resources.
   *
   * @param fullPath The full path to the image resource
   * @return The loaded Image, or a fallback image if loading fails
   */
  public static Image getImage(String fullPath) {
    if (imageCache.containsKey(fullPath)) return imageCache.get(fullPath);

    try (InputStream stream = ResourceLoader.class.getResourceAsStream(fullPath)) {
      if (stream == null) {
        System.err.println("Mangler bilde: " + fullPath);
        return getImage("/fallback/missing.png"); // fallback
      }
      Image img = new Image(stream);
      imageCache.put(fullPath, img);
      return img;
    } catch (Exception e) {
      System.err.println("Feil ved lasting av bilde: " + fullPath);
      return getImage("/fallback/missing.png");
    }
  }

  /**
   * Creates an ImageView with the specified image and size.
   *
   * @param fullPath The full path to the image resource
   * @param size The desired size for the image (width and height)
   * @return An ImageView configured with the specified image and size
   */
  public static ImageView getIcon(String fullPath, double size) {
    Image image = getImage(fullPath);
    if (image == null) return null;

    ImageView view = new ImageView(image);
    view.setFitWidth(size);
    view.setFitHeight(size);
    return view;
  }

  /**
   * Preloads all assets required for the start menu scene.
   * Improves performance by loading resources before they are needed.
   */
  public static void preloadStartMenuAssets() {
    getButtonImage("start_button.png");
    getBackground("start_background.png");
  }

  /**
   * Preloads all assets required for the ladder game scene.
   * Includes tiles, player icons, backgrounds, and dice faces.
   */
  public static void preloadLadderGameAssets() {
    getTileImage("basicTile.png");
    getTileImage("ladder_up.png");
    getPlayerIcon("player1.png");
    getPlayerIcon("player2.png");
    getBackground("ladder_background.png");
    for (int i = 1; i <= 6; i++) {
      getDiceImage("die_" + i + ".png");
    }
  }

  /**
   * Loads a dice face image.
   *
   * @param fileName The name of the dice face image file
   * @return The loaded dice face image
   */
  public static Image getDiceImage(String fileName) {
    return getImage(DICE_PATH + fileName);
  }

  /**
   * Loads a player icon image.
   *
   * @param fileName The name of the player icon file
   * @return The loaded player icon image
   */
  public static Image getPlayerIcon(String fileName) {
    return getImage(PLAYER_PATH + fileName);
  }

  /**
   * Creates an ImageView for a player icon with the specified size.
   *
   * @param fileName The name of the player icon file
   * @param size The desired size for the icon
   * @return An ImageView configured with the player icon
   */
  public static ImageView getPlayerIconView(String fileName, double size) {
    return getIcon(PLAYER_PATH + fileName, size);
  }

  /**
   * Creates an ImageView for a dice icon with the specified size.
   *
   * @param fileName The name of the dice icon file
   * @param size The desired size for the icon
   * @return An ImageView configured with the dice icon
   */
  public static ImageView getDiceIcon(String fileName, double size) {
    return getIcon(DICE_PATH + fileName, size);
  }

  /**
   * Loads a tile image.
   *
   * @param fileName The name of the tile image file
   * @return The loaded tile image
   */
  public static Image getTileImage(String fileName) {
    return getImage(TILE_PATH + fileName);
  }

  /**
   * Creates an ImageView for a tile icon with the specified size.
   *
   * @param fileName The name of the tile icon file
   * @param size The desired size for the icon
   * @return An ImageView configured with the tile icon
   */
  public static ImageView getTileIcon(String fileName, double size) {
    return getIcon(TILE_PATH + fileName, size);
  }

  /**
   * Loads a button image.
   *
   * @param fileName The name of the button image file
   * @return The loaded button image
   */
  public static Image getButtonImage(String fileName) {
    return getImage(BUTTON_PATH + fileName);
  }

  /**
   * Creates an ImageView for a button icon with the specified size.
   *
   * @param fileName The name of the button icon file
   * @param size The desired size for the icon
   * @return An ImageView configured with the button icon
   */
  public static ImageView getButtonIcon(String fileName, double size) {
    return getIcon(BUTTON_PATH + fileName, size);
  }

  /**
   * Loads a background image.
   *
   * @param fileName The name of the background image file
   * @return The loaded background image
   */
  public static Image getBackground(String fileName) {
    return getImage(BACKGROUND_PATH + fileName);
  }

  /**
   * Loads the ladder sprite image used for rendering ladders on the game board.
   *
   * @return The loaded ladder sprite image
   */
  public static Image getLadderSprite() {
    return getImage("/tile/ladder_sprite.png"); // eks: 20x100 px stige
  }
}