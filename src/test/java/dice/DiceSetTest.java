package dice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DiceSet functionality.
 * Tests creation, rolling, and value retrieval of multiple dice.
 */
class DiceSetTest {

    /**
     * Tests that dice can be created with valid numbers.
     */
    @Test
    void testDiceCreationWithValidNumber() {
        DiceSet diceSet = new DiceSet(2);
        assertEquals(2, diceSet.getNumberOfDice(),
                "Should create correct number of dice");
    }

    /**
     * Tests that creating dice with invalid numbers throws exceptions.
     */
    @Test
    void testDiceCreationWithInvalidNumber() {
        assertThrows(IllegalArgumentException.class, () -> new DiceSet(0),
                "Should throw exception when creating dice with 0");
        assertThrows(IllegalArgumentException.class, () -> new DiceSet(-1),
                "Should throw exception when creating dice with negative number");
        assertThrows(IllegalArgumentException.class, () -> new DiceSet(11),
                "Should throw exception when creating dice exceeding maximum");
    }

    /**
     * Tests that rolling returns valid sums for multiple rolls.
     */
    @Test
    void testRollReturnsValidSum() {
        DiceSet diceSet = new DiceSet(2);
        for (int i = 0; i < 100; i++) {
            int result = diceSet.roll();
            assertTrue(result >= 2 && result <= 12,
                    "Roll with 2 dice should return sum between 2 and 12");
        }
    }

    /**
     * Tests accessing individual die values with valid indices.
     */
    @Test
    void testGetDieWithValidIndex() {
        DiceSet diceSet = new DiceSet(2);
        diceSet.roll();

        int firstDie = diceSet.getDie(0);
        int secondDie = diceSet.getDie(1);

        assertTrue(firstDie >= 1 && firstDie <= 6,
                "First die value should be between 1 and 6");
        assertTrue(secondDie >= 1 && secondDie <= 6,
                "Second die value should be between 1 and 6");
    }

    /**
     * Tests that accessing invalid die indices throws exceptions.
     */
    @Test
    void testGetDieWithInvalidIndex() {
        DiceSet diceSet = new DiceSet(2);

        assertThrows(IllegalArgumentException.class, () -> diceSet.getDie(-1),
                "Should throw exception when accessing negative index");
        assertThrows(IllegalArgumentException.class, () -> diceSet.getDie(2),
                "Should throw exception when accessing index >= number of dice");
    }

    /**
     * Tests that rolling updates all dice values and they change over time.
     */
    @Test
    void testRollUpdatesAllDice() {
        DiceSet diceSet = new DiceSet(2);

        // First roll to get initial values
        diceSet.roll();
        int initialFirst = diceSet.getDie(0);
        int initialSecond = diceSet.getDie(1);

        // Roll multiple times to ensure values change
        boolean firstChanged = false;
        boolean secondChanged = false;

        for (int i = 0; i < 100 && !(firstChanged && secondChanged); i++) {
            diceSet.roll();
            if (diceSet.getDie(0) != initialFirst) firstChanged = true;
            if (diceSet.getDie(1) != initialSecond) secondChanged = true;
        }

        assertTrue(firstChanged && secondChanged,
                "Both dice should change values over multiple rolls");
    }

    /**
     * Tests that the sum of individual die values equals the total roll.
     */
    @Test
    void testSumMatchesIndividualDice() {
        DiceSet diceSet = new DiceSet(2);
        int total = diceSet.roll();
        assertEquals(total, diceSet.getDie(0) + diceSet.getDie(1),
                "Sum should equal individual die values");
    }

}