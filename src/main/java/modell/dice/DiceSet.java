package modell.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of dice used in board games.
 * This class manages multiple dice and provides methods to roll
 * all dice and access individual die values. Commonly used in games
 * like Snakes and Ladders, Yahtzee, etc.
 *
 * <p>The number of dice must be between {@value #MIN_DICE} and {@value #MAX_DICE}.
 * Each die generates random numbers between 1 and 6.
 *
 * <p>Example usage:
 * <pre>
 * DiceSet diceSet = new DiceSet(2);  // Create two dice
 * int total = diceSet.roll();        // Roll both dice and get sum
 * int firstDie = diceSet.getDie(0);  // Get first die's value
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class DiceSet {
  /** Maximum number of dice allowed in the collection */
  private static final int MAX_DICE = 10;
  /** Minimum number of dice required */
  private static final int MIN_DICE = 1;
  /** List containing all dice in the collection */
  private final List<Die> dice;

  /**
   * Constructs a new collection of dice.
   * Creates the specified number of dice, each capable of
   * generating random numbers between 1 and 6.
   *
   * @param numberOfDice the number of dice to create (between {@value #MIN_DICE} and {@value #MAX_DICE})
   * @throws IllegalArgumentException if numberOfDice is less than {@value #MIN_DICE} or greater than {@value #MAX_DICE}
   */
  public DiceSet(int numberOfDice) {
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
   * Rolls all dice simultaneously and returns their sum.
   * Each die will generate a random number between 1 and 6.
   * For example, with 2 dice, the possible sum range is 2-12.
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
   * The die number is zero-based, so the first die is 0,
   * the second die is 1, etc.
   *
   * @param dieNumber the index of the die (0-based)
   * @return the current value of the specified die
   * @throws IllegalArgumentException if dieNumber is negative or exceeds the number of dice
   */
  public int getDie(int dieNumber) {
    if (dieNumber < 0) {
      throw new IllegalArgumentException(
          "Die number cannot be negative, got: " + dieNumber);
    }
    if (dieNumber >= dice.size()) {
      throw new IllegalArgumentException(
          "Die number " + dieNumber + " exceeds number of dice (" +
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