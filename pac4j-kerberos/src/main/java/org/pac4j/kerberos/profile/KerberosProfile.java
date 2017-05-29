package org.pac4j.kerberos.profile;

import org.ietf.jgss.GSSContext;
import org.pac4j.http.profile.HttpProfile;

/**
 * Represents a user profile based on a Kerberos authentication.
 *
 * @author Garry Boyce
 * @since 2.1.0
 */
public class KerberosProfile extends HttpProfile {

    private static final long serialVersionUID = -1388563485891552197L;
    public GSSContext gssContext = null;
}
