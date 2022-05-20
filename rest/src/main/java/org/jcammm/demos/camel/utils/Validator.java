package org.jcammm.demos.camel.utils;

import java.util.regex.Pattern;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jcammm.demos.camel.dto.Contact;
import org.jcammm.demos.camel.exception.DataOutOfFieldException;

public class Validator implements Processor {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+");
    private static final PhoneNumberUtil PHONE_PATTERN = PhoneNumberUtil.getInstance();

    public static void email(String email) throws DataOutOfFieldException {
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new DataOutOfFieldException("Email invalid.");
    }

    public static void name(String name) throws DataOutOfFieldException {
        if (!NAME_PATTERN.matcher(name).matches())
            throw new DataOutOfFieldException("Name invalid.");
    }

    public static void phone(String phone) throws DataOutOfFieldException {
        try {
            PHONE_PATTERN.parse(phone, "US");
        } catch (NumberParseException e) {
            throw new DataOutOfFieldException("Phone invalid", e);
        }
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Contact c = exchange.getIn().getBody(Contact.class);
        name(c.getFirstName());
        name(c.getLastName());
        phone(c.getPhoneNumber());
        email(c.getEmailAddress());
    }
}
