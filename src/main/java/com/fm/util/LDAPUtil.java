package com.fm.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.fm.model.LDAPUser;

public class LDAPUtil {

    public static LDAPUser authenticate(String username, String password) {

        String server = ConfigUtil.getValue("LDAP_SERVER");
        String domain = ConfigUtil.getValue("LDAP_NETBIOS_DOMAIN");
        String port = ConfigUtil.getValue("LDAP_PORT");
        String baseDN = ConfigUtil.getValue("LDAP_BASE_DN");

        Hashtable<String, String> env = new Hashtable<>();

        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");

        env.put(Context.PROVIDER_URL,
                "ldap://" + server + ":" + port);

        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        env.put(Context.SECURITY_PRINCIPAL,
                domain + "\\" + username);

        env.put(Context.SECURITY_CREDENTIALS,
                password);

        try {

            DirContext ctx = new InitialDirContext(env);

            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration<SearchResult> results =
                    ctx.search(baseDN,
                            "(sAMAccountName=" + username + ")",
                            controls);

            if (results.hasMore()) {

                SearchResult result = results.next();

                Attributes attrs = result.getAttributes();

                LDAPUser user = new LDAPUser();

                user.setUsername(username);
                user.setDisplayName(getAttribute(attrs, "displayName"));
                user.setEmail(getAttribute(attrs, "mail"));
                user.setDepartment(getAttribute(attrs, "department"));

                ctx.close();

                return user;
            }

            ctx.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static String getAttribute(Attributes attrs, String attribute) {

        try {
            if (attrs.get(attribute) != null) {
                return attrs.get(attribute).get().toString();
            }
        } catch (Exception e) {
        }

        return "";
    }

}