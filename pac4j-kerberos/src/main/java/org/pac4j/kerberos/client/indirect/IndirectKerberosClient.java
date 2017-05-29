package org.pac4j.kerberos.client.indirect;

/**
 * @author Vidmantas Zemleris, at Kensu.io
 *
 * @since 2.1.0
 */

import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.Mechanism;
import org.pac4j.core.client.RedirectAction;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.kerberos.credentials.KerberosCredentials;
import org.pac4j.kerberos.credentials.extractor.KerberosExtractor;
import org.pac4j.kerberos.profile.KerberosProfile;

import static org.pac4j.core.client.Mechanism.KERBEROS_MECHANISM;
import static org.pac4j.core.util.CommonHelper.assertNotNull;

public class IndirectKerberosClient extends BaseClient<KerberosCredentials, KerberosProfile> {
    public KerberosExtractor extractor = null;

    @Override
    protected void internalInit() {
        CommonHelper.assertNotNull("authenticator", getAuthenticator());
    }

    public IndirectKerberosClient(final Authenticator authenticator) {
        extractor = new KerberosExtractor(getName());
        setAuthenticator(authenticator);
    }

    @Override
    protected RedirectAction retrieveRedirectAction(final WebContext context) {
        return RedirectAction.redirect(getContextualCallbackUrl(context));
    }

    @Override
    protected BaseClient<KerberosCredentials, KerberosProfile> newClient() {
        // used in cloning
        return new IndirectKerberosClient(getAuthenticator());
    }

    @Override
    protected boolean isDirectRedirection() {
        return true; // like in AbstractHeaderClient
    }

    @Override
    protected KerberosCredentials retrieveCredentials(final WebContext context) throws RequiresHttpAction {
        assertNotNull("credentialsExtractor", extractor);
        assertNotNull("authenticator", getAuthenticator());

        final KerberosCredentials credentials;
        try {
            // retrieve credentials
            credentials = extractor.extract(context);
            logger.debug("kerberos credentials : {}", credentials);
            if (credentials == null) {
                throw RequiresHttpAction.unauthorizedNegotiate("Kerberos Header not found", context);
            }
            // validate credentials
            getAuthenticator().validate(credentials);
        } catch (final CredentialsException e) {
            throw RequiresHttpAction.unauthorizedNegotiate("Kerberos auth failed", context);
        }

        return credentials;
    }

    @Override
    protected KerberosProfile retrieveUserProfile(final KerberosCredentials credentials, final WebContext context) {
        // create the user profile
        KerberosProfile profile = new KerberosProfile();
        assertNotNull("extracted KerberosCredentials.profileId", credentials.profileId);
        profile.setId(credentials.profileId);
        return profile;
    }

    @Override
    public Mechanism getMechanism() {
        return KERBEROS_MECHANISM;
    }

    @Override
    public String toString() {
        return CommonHelper.toString(this.getClass(), "callbackUrl", this.callbackUrl, "name", getName(),
            "extractor",  "authenticator", getAuthenticator());
    }
}
