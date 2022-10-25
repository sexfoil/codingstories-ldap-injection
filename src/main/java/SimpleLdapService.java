import com.unboundid.ldap.sdk.LDAPException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SimpleLdapService {

    private static final String DN_NAME = "ou=people,dc=springframework,dc=org";
    private InMemoryLdapServer server;
    private DirContext clientContext;


    public SimpleLdapService() {
        try {
            this.server = new InMemoryLdapServer();
            this.server.start();
            LdapClient client = new LdapClient(server.getConnectionHostPort());
            this.clientContext = client.getContext();
        } catch (LDAPException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public boolean authenticateClient(String user, String password) {

        String filter = "(&(uid=" + user + ")(userPassword=" + password + "))";

        try {
            NamingEnumeration<SearchResult> result = clientContext.search(DN_NAME, filter, new SearchControls());
            return result.hasMore();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<SearchResult> searchByUid(String uid) {

        String filter = "uid=" + uid;

        List<SearchResult> resultList = new ArrayList<>();
        try {
            NamingEnumeration<SearchResult> result = clientContext.search(DN_NAME, filter, new SearchControls());
            while (result.hasMore()) {
                resultList.add(result.next());
            }
            return resultList;
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void shutDownServer() {
        if (server != null) {
            try {
                server.stop();
            } catch (LDAPException e) {
                e.printStackTrace();
            }
        }
    }
}
