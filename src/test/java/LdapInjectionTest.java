import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.directory.SearchResult;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LdapInjectionTest {

    private SimpleLdapService service;


    @BeforeEach
    void setUp() {
        service = new SimpleLdapService();
    }

    @AfterEach
    void destroy() {
        service.shutDownServer();
    }


    @Test
    void shouldAuthenticateUserWithCorrectInputTest() {
        String existingUserBobLogin = "bob";
        String existingUserBobPassword = "bobspassword";

        boolean isAuthenticate = service.authenticateClient(existingUserBobLogin, existingUserBobPassword);

        assertTrue(isAuthenticate);
    }

    @Test
    void shouldNotAuthenticateUserWithWrongInputTest() {
        String injectionInputLogin = "*)(uid=*))(|(uid=*";
        String anyPassword = "doesn't matter";

        boolean isAuthenticate = service.authenticateClient(injectionInputLogin, anyPassword);

        assertFalse(isAuthenticate);
    }

    @Test
    void shouldFindUserByUidWithCorrectInputTest() {
        String existingUid = "ben";
        String expected = "uid: " + existingUid;

        List<SearchResult> result = service.searchByUid(existingUid);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expected, result.get(0).getAttributes().get("uid").toString());
    }

    @Test
    void shouldNotFindUserByUidWithWrongInputTest() {
        String injectedSymbol = "*";

        List<SearchResult> result = service.searchByUid(injectedSymbol);

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
