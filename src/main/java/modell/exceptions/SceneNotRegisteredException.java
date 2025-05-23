package modell.exceptions;

/**
 * Exception thrown when attempting to switch to a scene
 * that has not been registered in the SceneManager.
 *
 * <p>This indicates a configuration error where the
 * requested scene name was never associated with a view.
 *
 * @author didrik
 * @version 1.0
 */
public class SceneNotRegisteredException extends RuntimeException {
  /**
   * Constructs the exception with the missing scene name.
   *
   * @param sceneName the name of the scene that was not registered
   */
  public SceneNotRegisteredException(String sceneName) {
    super("Scene not registered: " + sceneName);
  }
}
