import org.junit.Test;
import static org.junit.Assert.*;

public class LoginAndSignUpTest {

    @Test
    public void testLoginWithValidCredentials() {
        String validUsername = "Yakup34";
        String validPassword = "gndz13tr";
        boolean loginResult = performLogin(validUsername, validPassword);
        assertTrue(loginResult);
    }
    @Test
    public void testLoginWithInvalidCredentials() {
        String invalidUsername = "GeçersizKullanıcıAdı";
        String invalidPassword = "GeçersizŞifre";
        boolean loginResult = performLogin(invalidUsername, invalidPassword);
        assertFalse(loginResult);
    }

    @Test
    public void testPasswordHint() {
        String validUsername = "Yakup34";
        String expectedHint = "soyad";
        String actualHint = getPasswordHint(validUsername);

        // Alınan şifre ipucunu kontrol et
        assertEquals(expectedHint, actualHint);
    }
    private boolean performLogin(String username, String password) {
        Database database = new Database();
        return database.validateUser(username, password);
    }

    private String getPasswordHint(String username) {
        Database database = new Database();
        return database.getParolaIpucu(username);
    }
}