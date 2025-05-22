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
 * Hjelpeklasse i view-laget for å rendere terning-animasjoner.
 */
public class AnimationRenderer {

  /**
   * Spiller en enkel terning-animasjon på de to ImageView-ene.
   * Når den er ferdig, kaster to tilfeldige terninger og kaller callback med (a,b).
   *
   * @param die1   ImageView for første terning
   * @param die2   ImageView for andre terning
   * @param onDone Callback som mottar (a,b) når animasjonen er ferdig
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
