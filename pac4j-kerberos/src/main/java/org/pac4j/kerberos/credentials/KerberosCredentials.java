package org.pac4j.kerberos.credentials;

import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.util.CommonHelper;

import java.nio.charset.StandardCharsets;

/**
 * Credentials containing the kerberos ticket.
 *
 * @author Garry Boyce
 * @since 2.1.0
 */
public class KerberosCredentials extends Credentials {
    private byte[] kerberosTicket;
    // We cant validate the ticket twice, so store it here
    public String profileId = null;

    /**
     *
     */
    private static final long serialVersionUID = -4264156105410684508L;

    public KerberosCredentials(byte[] kerberosTicket, String clientName) {
        this.kerberosTicket = kerberosTicket.clone();
        this.setClientName(clientName);
    }

    public byte[] getKerberosTicket() {
        return kerberosTicket.clone();
    }

    public String getKerberosTicketAsString() {
        return getTicketAsString(kerberosTicket);
    }

    @Override
    public String toString() {
        return CommonHelper.toString(this.getClass(), "token", this.kerberosTicket, "clientName", getClientName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        KerberosCredentials that = (KerberosCredentials) o;

        return !(kerberosTicket != null ? !getTicketAsString(kerberosTicket).equals(getTicketAsString(that.kerberosTicket)) : that.kerberosTicket != null);

    }

    @Override
    public int hashCode() {
        return kerberosTicket != null ? getTicketAsString(kerberosTicket).hashCode() : 0;
    }

    private String getTicketAsString(byte[] kerberosTicket) {
        return new String(kerberosTicket, StandardCharsets.UTF_8);
    }


}
