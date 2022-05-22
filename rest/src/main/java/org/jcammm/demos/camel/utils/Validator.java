package org.jcammm.demos.camel.utils;

import java.util.UUID;
import java.util.regex.Pattern;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jcammm.demos.camel.config.RoutesConfig;
import org.jcammm.demos.camel.dto.Contact;
import org.jcammm.demos.camel.exception.DataOutOfFieldException;

public class Validator implements Processor {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+");
    private static final PhoneNumberUtil PHONE_PATTERN = PhoneNumberUtil.getInstance();

    public static void email(String email) throws DataOutOfFieldException {
        if (email.isEmpty())
            return;
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new DataOutOfFieldException("Email invalid.");
    }

    public static void name(String name) throws DataOutOfFieldException {
        if (name.isEmpty())
            return;
        if (!NAME_PATTERN.matcher(name).matches())
            throw new DataOutOfFieldException("Name invalid.");
    }

    public static void phone(String phone) throws DataOutOfFieldException {
        if (phone.isEmpty())
            return;
        try {
            PHONE_PATTERN.parse(phone, "US");
        } catch (NumberParseException e) {
            throw new DataOutOfFieldException("Phone invalid", e);
        }
    }

    public static void uuid(String uuid) throws DataOutOfFieldException {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException iae) {
            throw new DataOutOfFieldException("Id invalid (only 32 hex. UUID format accepted)", iae);
        }
    }

    @Override
    public void process(Exchange exchange) throws DataOutOfFieldException {
        String routeId = exchange.getFromRouteId();
        switch (routeId) {
            case RoutesConfig.POST:
            case RoutesConfig.PATCH:
            case RoutesConfig.PUT:
                Contact c = exchange.getIn().getBody(Contact.class);
                name(c.getFirstName());
                name(c.getLastName());
                phone(c.getPhoneNumber());
                email(c.getEmailAddress());
                break;

        }

        switch (routeId) {
            case RoutesConfig.PATCH:
            case RoutesConfig.PUT:
            case RoutesConfig.GETONE:
            case RoutesConfig.DELETE:
                String uuid = exchange.getIn().getHeader("uuid", String.class);
                uuid(uuid);
                break;
        }
    }
}
