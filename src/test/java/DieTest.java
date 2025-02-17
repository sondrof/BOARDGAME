import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

    @Test
    void testRollForPositiveValues() {
        Die die = new Die();
        for (int i = 0; i < 50; i++) {
            int result = die.roll();
            assertTrue(result >= 1 && result <=6);
        }
    }





}