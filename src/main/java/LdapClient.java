import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class LdapClient {

    private static final String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private static final String HOST_PREFIX = "ldap://";
    private static final String SIMPLE_AUTH = "simple";

    private final Hashtable<String, String> env = new Hashtable<>();

    private DirContext context;

    public LdapClient(String hostPortInfo) {
        String url = HOST_PREFIX.concat(hostPortInfo);
        env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_AUTHENTICATION, SIMPLE_AUTH);
        initContext();
    }

    private boolean initContext() {
        try {
            context = new InitialDirContext(env);
            return true;

        } catch (NamingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DirContext getContext() {
        return context;
    }
}
