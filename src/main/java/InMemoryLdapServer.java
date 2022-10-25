import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class InMemoryLdapServer {

    private final InMemoryDirectoryServer server;
    private LDAPConnection connection;


    public InMemoryLdapServer() throws LDAPException, URISyntaxException {
        InMemoryDirectoryServerConfig serverConfig = new InMemoryDirectoryServerConfig(
                "dc=springframework,dc=org",
                "ou=groups,dc=springframework,dc=org",
                "ou=people,dc=springframework,dc=org",
                "ou=space cadets,dc=springframework,dc=org",
                "ou=\\\"quoted people\\\",dc=springframework,dc=org",
                "ou=otherpeople,dc=springframework,dc=org"
        );
        this.server = new InMemoryDirectoryServer(serverConfig);
        this.server.importFromLDIF(true, getFile("users.ldif"));
    }

    public String getConnectionHostPort() {
        assert this.connection != null;
        return this.connection.getHostPort();
    }

    public void start() throws LDAPException {
        this.server.startListening();
        this.connection = this.server.getConnection();
    }

    public void stop() throws LDAPException {
        this.server.shutDown(true);
    }

    private File getFile(String filename) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(filename);
        assert url != null;
        return new File(url.toURI());
    }
}
