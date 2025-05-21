package view.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResourceLoader {

  private static final String PLAYER_PATH = "/player/";
  private static final String TILE_PATH = "/tile/";
  private static final String BUTTON_PATH = "/button/";
  private static final String BACKGROUND_PATH = "/background/";

  private static final Map<String, Image> imageCache = new HashMap<>();

  private ResourceLoader() {
    // Private constructor to prevent instantiation
  }

  // Core generic image loader
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


  public static ImageView getIcon(String fullPath, double size) {
    Image image = getImage(fullPath);
    if (image == null) return null;

    ImageView view = new ImageView(image);
    view.setFitWidth(size);
    view.setFitHeight(size);
    return view;
  }

  public static void preloadStartMenuAssets() {
    getButtonImage("start_button.png");
    getBackground("start_background.png");
  }

  public static void preloadLadderGameAssets() {
    getTileImage("basicTile.png");
    getTileImage("ladder_up.png");
    getPlayerIcon("player1.png");
    getPlayerIcon("player2.png");
    getBackground("ladder_background.png");
  }


  // --- Category-Specific Access ---

  public static Image getPlayerIcon(String fileName) {
    return getImage(PLAYER_PATH + fileName);
  }

  public static ImageView getPlayerIconView(String fileName, double size) {
    return getIcon(PLAYER_PATH + fileName, size);
  }

  public static Image getTileImage(String fileName) {
    return getImage(TILE_PATH + fileName);
  }

  public static ImageView getTileIcon(String fileName, double size) {
    return getIcon(TILE_PATH + fileName, size);
  }

  public static Image getButtonImage(String fileName) {
    return getImage(BUTTON_PATH + fileName);
  }

  public static ImageView getButtonIcon(String fileName, double size) {
    return getIcon(BUTTON_PATH + fileName, size);
  }

  public static Image getBackground(String fileName) {
    return getImage(BACKGROUND_PATH + fileName);
  }

  public static Image getLadderSprite() {
    return getImage("/tile/ladder_sprite.png"); // eks: 20x100 px stige
  }
}