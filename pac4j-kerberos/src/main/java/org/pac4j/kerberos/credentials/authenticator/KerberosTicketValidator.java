package org.pac4j.kerberos.credentials.authenticator;

import org.pac4j.core.exception.CredentialsException;

public interface KerberosTicketValidator {

    /**
     * Validates a Kerberos/SPNEGO ticket.
     *
     * @param token Kerbeos/SPNEGO ticket
     * @return authenticated kerberos principal
     * @throws CredentialsException if the ticket is not valid
     */
    KerberosTicketValidation validateTicket(byte[] token) throws CredentialsException;
}
