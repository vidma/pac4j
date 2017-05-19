package org.pac4j.kerberos.client.direct;


import org.pac4j.kerberos.credentials.KerberosCredentials;
import org.pac4j.kerberos.credentials.authenticator.KerberosAuthenticator;
import org.pac4j.kerberos.credentials.extractor.KerberosExtractor;
import org.pac4j.kerberos.profile.KerberosProfile;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.client.DirectClient;
import org.pac4j.core.profile.creator.ProfileCreator;

/**
 * <p>This class is the client to authenticate users directly based on Kerberos ticket.</p>
 *
 * @author Garry Boyce
 * @since 1.9.1
 */
public class KerberosClient extends DirectClient<KerberosCredentials, KerberosProfile> {

    public KerberosClient() {
    }

    public KerberosClient(final Authenticator authenticator) {
        setAuthenticator(authenticator);
    }

    public KerberosClient(final Authenticator authenticator, final ProfileCreator<KerberosCredentials, KerberosProfile> profileCreator) {
        setAuthenticator(authenticator);
        setProfileCreator(profileCreator);
    }

    @Override
    protected void clientInit(final WebContext context) {
    	setCredentialsExtractor(new KerberosExtractor(getName()));
        super.internalInit(context);
        setAuthenticator(new KerberosAuthenticator());
        // FIXME: is this needed? assertAuthenticatorTypes(KerberosAuthenticator.class);
    }

}
