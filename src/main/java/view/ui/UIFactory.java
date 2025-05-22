package view.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

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
public class UIFactory {
  private UIFactory() {}

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

  /**
   * Creates a VBox containing buttons with specified layout properties.
   * Groups buttons vertically with consistent spacing and alignment.
   *
   * @param alignment The alignment for the button group (e.g., Pos.CENTER)
   * @param spacing The vertical space between buttons
   * @param padding The padding inside the VBox
   * @param buttons The buttons to include in the group
   * @return A configured VBox containing the buttons
   */
  public static VBox groupButtons(Pos alignment, double spacing, Insets padding, Button... buttons) {
    // " ... " Betyr at vi har en ubestembt mengde parameter av Button som kan bli lagt til. Derfor tar metoden så mange Button parameter du trenger.
    VBox group = new VBox(spacing, buttons);
    group.setAlignment(alignment);
    group.setPadding(padding);
    return group;
  }

  /**
   * Sets the offset position of a node relative to its current position.
   *
   * @param node The node to position
   * @param offsetX The horizontal offset in pixels
   * @param offsetY The vertical offset in pixels
   */
  public static void setOffset(Node node, double offsetX, double offsetY) {
    node.setTranslateX(offsetX);
    node.setTranslateY(offsetY);
  }

  /**
   * Creates a VBox containing buttons with default padding.
   * Convenience method that uses standard padding of 20 pixels.
   *
   * @param alignment The alignment for the button group
   * @param spacing The vertical space between buttons
   * @param buttons The buttons to include in the group
   * @return A configured VBox containing the buttons
   */
  public static VBox groupButtons(Pos alignment, double spacing, Button... buttons) {
    return groupButtons(alignment, spacing, new Insets(20), buttons);
  }
}
