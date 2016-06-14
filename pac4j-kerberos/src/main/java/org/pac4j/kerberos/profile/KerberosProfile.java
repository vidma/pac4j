package org.pac4j.kerberos.profile;

import org.ietf.jgss.GSSContext;
import org.pac4j.core.profile.CommonProfile;

/**
 * Represents a user profile based on a Kerberos authentication.
 *
 * @author Garry Boyce
 * @since 1.9.1
 */
public class KerberosProfile extends CommonProfile {

    private static final long serialVersionUID = -1388563485891552197L;
    public GSSContext gssContext = null;
}
