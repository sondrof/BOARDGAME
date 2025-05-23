package view.ui;

import javafx.scene.control.Button;


/**
 * Utility class for creating and configuring UI components.
 * Provides factory methods for common UI element creation and layout.
 *
 * <p>This class offers:
 * <ul>
 *     <li>Button creation with action handlers</li>
 *     <li>Button group layout configuration</li>
 *     <li>Node positioning and offset utilities</li>
 *     <li>Consistent UI element styling</li>
 * </ul>
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class UiFactory {
  private UiFactory() {}

  /**
   * Creates a button with the specified text and action handler.
   *
   * @param text The text to display on the button
   * @param action The action to execute when the button is clicked
   * @return A configured Button instance
   */
  public static Button button(String text, Runnable action) {
    Button button = new Button(text);
    button.setOnAction(e -> action.run());
    return button;
  }
}
