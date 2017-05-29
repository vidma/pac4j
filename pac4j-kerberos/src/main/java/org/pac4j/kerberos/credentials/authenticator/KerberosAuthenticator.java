package org.pac4j.kerberos.credentials.authenticator;

import org.pac4j.core.credentials.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.kerberos.credentials.KerberosCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Authenticator for Kerberos. It creates the user profile and stores it in the credentials
 *
 * @author Garry Boyce
 * @since 2.1.0
 */
public class KerberosAuthenticator implements Authenticator<KerberosCredentials> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private KerberosTicketValidator ticketValidator;

    public KerberosAuthenticator() {
    }

    /**
     * Initializes the authenticator that will validate Kerberos tickets.
     *
     * @param ticketValidator The ticket validator used to validate the Kerberos ticket.
     * @since 2.1.0
     */
    public KerberosAuthenticator(KerberosTicketValidator ticketValidator) {
        this.ticketValidator = ticketValidator;
    }

    @Override
    public void validate(KerberosCredentials credentials) throws CredentialsException {
        logger.trace("Try to validate Kerberos Token:" + credentials.getKerberosTicketAsString());
        KerberosTicketValidation ticketValidation = this.ticketValidator.validateTicket(credentials.getKerberosTicket());
        logger.debug("Kerberos Token validated");

        String subject = ticketValidation.username();
        logger.debug("Succesfully validated " + subject);
        credentials.profileId = subject;
    }


    public KerberosTicketValidator getTicketValidator() {
        return ticketValidator;
    }

    public void setTicketValidator(KerberosTicketValidator ticketValidator) {
        this.ticketValidator = ticketValidator;
    }

}
