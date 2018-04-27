package no.nav.fo.forenkletdeploy.util;

public class NotFoundException extends javax.ws.rs.NotFoundException {
    public NotFoundException(String message) {
        super(new Throwable(message));
    }
}
