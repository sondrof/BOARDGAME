package dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for dice.Die functionality.
 * Tests the behavior of a single six-sided die, including
 * rolling and value retrieval.
 */
class DieTest {

    /**
     * Tests that rolling a die always returns values between 1 and 6.
     * Performs multiple rolls to ensure consistent behavior.
     */
    @Test
    void testRollForPositiveValues() {
        Die die = new Die();
        for (int i = 0; i < 50; i++) {
            int result = die.roll();
            assertTrue(result >= 1 && result <= 6,
                    "dice.Die roll should return value between 1 and 6");
        }
    }

    /**
     * Tests that getValue returns the same value as the last roll.
     * Verifies that getValue accurately reflects the die's current state.
     */
    @Test
    void testGetValue() {
        Die die = new Die();
        int rollResult = die.roll();
        assertEquals(rollResult, die.getValue(),
                "getValue should return the last rolled value");
    }

    /**
     * Tests that getValue returns the correct value after multiple rolls.
     * Ensures the die maintains correct state even after several rolls.
     */
    @Test
    void testGetValueAfterMultipleRolls() {
        Die die = new Die();
        die.roll();  // First roll
        die.roll();  // Second roll
        int finalRoll = die.roll();  // Third roll
        assertEquals(finalRoll, die.getValue(),
                "getValue should return the most recent roll");
    }


    /**
     * Tests that multiple rolls produce different values over time.
     * While not guaranteed due to randomness, this test helps ensure
     * the die isn't stuck on a single value.
     */
    @Test
    void testRandomness() {
        Die die = new Die();
        int firstRoll = die.roll();
        boolean differentFound = false;

        // Roll multiple times to check if we get different values
        for (int i = 0; i < 25 && !differentFound; i++) {
            if (die.roll() != firstRoll) {
                differentFound = true;
            }
        }

        assertTrue(differentFound,
                "dice.Die should produce different values over multiple rolls");
    }
}