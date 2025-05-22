package modell.dice;

import java.util.Random;

/**
 * Represents a single six-sided die used in board games.
 * This class simulates a die that can be rolled to generate
 * random numbers between 1 and 6.
 *
 * @author didrik
 * @version 1.0
 */
public class Die {
    /** Stores the current face value of the die */
    private int lastRolledValue;

    /** Random number generator for die rolls */
    private Random random;

    /**
     * Constructs a new die.
     * Initializes the random number generator and sets initial value.
     */
    public Die() {
        random = new Random();
        lastRolledValue = 0;
    }

    /**
     * Simulates rolling the die.
     * Generates a random number between 1 and 6.
     *
     * @return the result of the die roll (1-6)
     */
    public int roll() {
        lastRolledValue = random.nextInt(6) + 1;
        return lastRolledValue;
    }

    /**
     * Returns the current face value of the die.
     * This value is from the last roll and doesn't generate a new roll.
     *
     * @return the current face value of the die
     */
    public int getValue() {
        return lastRolledValue;
    }




}
