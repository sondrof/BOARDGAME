package dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of dice used in board games.
 * This class manages multiple dice and provides methods to roll
 * all dice and access individual die values.
 */
public class Dice {
    private static final int MAX_DICE = 10;
    private static final int MIN_DICE = 1;
    /** List containing all dice in the collection */
    private List<Die> dice;

    /**
     * Constructs a new collection of dice.
     *
     * @param numberOfDice the number of dice to create
     * @throws IllegalArgumentException if numberOfDice is less than 1
     */
    public Dice(int numberOfDice) {
        if (numberOfDice < MIN_DICE) {
            throw new IllegalArgumentException(
                    "Number of dice must be at least " + MIN_DICE);
        }
        if (numberOfDice > MAX_DICE) {
            throw new IllegalArgumentException(
                    "Number of dice cannot exceed " + MAX_DICE);
        }
        dice = new ArrayList<>();
        for (int i = 0; i < numberOfDice; i++) {
            dice.add(new Die());
        }
    }

    /**
     * Rolls all dice and returns their sum.
     * Each die will generate a random number between 1 and 6.
     *
     * @return the sum of all dice rolls
     */
    public int roll() {
        int total = 0;
        for (Die die : dice) {
            total += die.roll();
        }
        return total;
    }

    /**
     * Returns the current value of a specific die.
     *
     * @param dieNumber the index of the die (0-based)
     * @return the current value of the specified die
     * @throws IllegalArgumentException if dieNumber is invalid
     */
    public int getDie(int dieNumber) {
        if (dieNumber < 0) {
            throw new IllegalArgumentException(
                    "dice.Die number cannot be negative, got: " + dieNumber);
        }
        if (dieNumber >= dice.size()) {
            throw new IllegalArgumentException(
                    "dice.Die number " + dieNumber + " exceeds number of dice (" +
                            dice.size() + ")");
        }
        return dice.get(dieNumber).getValue();
    }

    /**
     * Returns the total number of dice in this collection.
     *
     * @return the number of dice
     */
    public int getNumberOfDice() {
        return dice.size();
    }
}
