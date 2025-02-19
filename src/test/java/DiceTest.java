import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Dice functionality.
 * Tests creation, rolling, and value retrieval of multiple dice.
 */
class DiceTest {

    /**
     * Tests that dice can be created with a valid number of dice.
     */
    @Test
    void testDiceCreationWithValidNumber() {
        Dice dice = new Dice(2);
        assertEquals(2, dice.getNumberOfDice(), "Should create correct number of dice");
    }

    /**
     * Tests that creating dice with invalid numbers throws exceptions.
     */
    @Test
    void testDiceCreationWithInvalidNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Dice(0),
                "Should throw exception when creating dice with 0");
        assertThrows(IllegalArgumentException.class, () -> new Dice(-1),
                "Should throw exception when creating dice with negative number");
    }

    /**
     * Tests that rolling returns valid sums for multiple rolls.
     */
    @Test
    void testRollReturnsValidSum() {
        Dice dice = new Dice(2);
        for (int i = 0; i < 50; i++) {
            int result = dice.roll();
            assertTrue(result >= 2 && result <= 12,
                    "Roll with 2 dice should return sum between 2 and 12");
        }
    }

    /**
     * Tests accessing individual die values with valid indices.
     */
    @Test
    void testGetDieWithValidIndex() {
        Dice dice = new Dice(2);
        dice.roll();

        int firstDie = dice.getDie(0);
        int secondDie = dice.getDie(1);

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
        Dice dice = new Dice(2);

        assertThrows(IllegalArgumentException.class, () -> dice.getDie(-1),
                "Should throw exception when accessing negative index");
        assertThrows(IllegalArgumentException.class, () -> dice.getDie(2),
                "Should throw exception when accessing index >= number of dice");
    }

    /**
     * Tests that rolling updates all dice values and they change over time.
     */
    @Test
    void testRollUpdatesAllDice() {
        Dice dice = new Dice(2);

        // First roll to get initial values
        dice.roll();
        int initialFirst = dice.getDie(0);
        int initialSecond = dice.getDie(1);

        // Roll multiple times to ensure values change
        boolean firstChanged = false;
        boolean secondChanged = false;

        for (int i = 0; i < 100 && !(firstChanged && secondChanged); i++) {
            dice.roll();
            if (dice.getDie(0) != initialFirst) firstChanged = true;
            if (dice.getDie(1) != initialSecond) secondChanged = true;
        }

        assertTrue(firstChanged && secondChanged,
                "Both dice should change values over multiple rolls");
    }

    /**
     * Tests that the sum of individual die values equals the total roll.
     */
    @Test
    void testSumMatchesIndividualDice() {
        Dice dice = new Dice(2);
        int total = dice.roll();
        assertEquals(total, dice.getDie(0) + dice.getDie(1),
                "Sum should equal individual die values");
    }
}