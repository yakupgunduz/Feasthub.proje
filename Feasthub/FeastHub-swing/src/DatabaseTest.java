import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatabaseTest {

    @Test
    public void testValidateUser_ValidCredentials() {
        Database database = new Database();
        boolean result = database.validateUser("Yakup34", "gndz13tr");
        assertTrue(result);
    }
        @Test
        public void testValidateUser_InvalidCredentials () {
            Database database = new Database();
            boolean result = database.validateUser("invalidUsername", "invalidPassword");
            assertFalse(result);
        }
}