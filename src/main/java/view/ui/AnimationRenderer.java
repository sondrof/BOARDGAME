// src/main/java/view/ui/AnimationRenderer.java
package view.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class for handling game animations in the view layer.
 * Provides functionality for rendering dice roll animations and other visual effects.
 *
 * <p>This class manages:
 * <ul>
 *     <li>Dice roll animations with random face transitions</li>
 *     <li>Timeline-based animation sequences</li>
 *     <li>Callback handling for animation completion</li>
 * </ul>
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class AnimationRenderer {

  /**
   * Plays a dice roll animation on two ImageView components.
   * The animation shows random dice faces changing rapidly before settling on the final values.
   *
   * <p>The animation sequence:
   * <ol>
   *     <li>Loads all six dice face images</li>
   *     <li>Creates a timeline that changes faces every 80ms</li>
   *     <li>Runs for 12 cycles (approximately 1 second)</li>
   *     <li>Generates final random values and calls the callback</li>
   * </ol>
   *
   * @param die1   ImageView for the first die
   * @param die2   ImageView for the second die
   * @param onDone Callback that receives the final dice values (a,b) when animation completes
   */
  public static void playDiceRoll(ImageView die1,
                                  ImageView die2,
                                  java.util.function.BiConsumer<Integer,Integer> onDone) {
    // Last inn alle seks ansikter:
    List<Image> faces = IntStream.rangeClosed(1, 6)
            .mapToObj(i -> ResourceLoader.getDiceImage("die_" + i + ".png"))
            .collect(Collectors.toList());

    // Timeline: bytt ansikt hvert 80ms, 12 ganger
    Timeline t = new Timeline(
            new KeyFrame(Duration.ZERO, e -> {
              die1.setImage(faces.get(ThreadLocalRandom.current().nextInt(6)));
              die2.setImage(faces.get(ThreadLocalRandom.current().nextInt(6)));
            }),
            new KeyFrame(Duration.millis(80))
    );
    t.setCycleCount(12);
    t.setOnFinished(e -> {
      // Når animasjonen er ferdig: gjør selve kastet
      int a = ThreadLocalRandom.current().nextInt(1,7);
      int b = ThreadLocalRandom.current().nextInt(1,7);
      onDone.accept(a, b);
    });
    t.play();
  }
}
