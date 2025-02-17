import java.util.ArrayList;
import java.util.List;

public class Dice {
    private List<Die> dice;

    public Dice(int numberOfDice) {
        dice = new ArrayList<>();
        for (int i = 0; i < numberOfDice; i++) {
            dice.add(new Die());
        }
    }

    public int roll() {
        int total = 0;
        for (Die die : dice) {
            total += die.roll();
        }
        return total;
    }

    public int getDie(int dieNumber) {
        if (dieNumber < 0 || dieNumber >= dice.size()) {
            throw new IllegalArgumentException("Invalid die number");
        }
        return dice.get(dieNumber).getValue();
    }
}
