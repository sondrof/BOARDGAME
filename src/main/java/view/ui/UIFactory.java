package view.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


//Metoder som gjør at vi enklere kan lage scenes med finere kode. Men trenger UIRenderer og UIElementController

public class UIFactory {
  private UIFactory() {}

  public static Button button(String text, Runnable action) {
    Button button = new Button(text);
    button.setOnAction(e -> action.run());
    return button;
  }

  /**
   * Creates a VBox containing buttons with spacing, alignment, and padding.
   * @param alignment alignment for the group (e.g. Pos.CENTER)
   * @param spacing vertical space between buttons
   * @param padding padding inside the VBox
   * @return a configured VBox
   */
  public static VBox groupButtons(Pos alignment, double spacing, Insets padding, Button... buttons) {
    // " ... " Betyr at vi har en ubestembt mengde parameter av Button som kan bli lagt til. Derfor tar metoden så mange Button parameter du trenger.
    VBox group = new VBox(spacing, buttons);
    group.setAlignment(alignment);
    group.setPadding(padding);
    return group;
  }



  public static void setOffset(Node node, double offsetX, double offsetY) {
    node.setTranslateX(offsetX);
    node.setTranslateY(offsetY);
  }




  public static VBox groupButtons(Pos alignment, double spacing, Button... buttons) {
    return groupButtons(alignment, spacing, new Insets(20), buttons);
  }
}
