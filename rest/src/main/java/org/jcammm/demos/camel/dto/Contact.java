package org.jcammm.demos.camel.dto;


import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Builder.Default
    private String contactId        = "";
    @Builder.Default
    private String firstName        = "";
    @Builder.Default
    private String lastName         = "";
    @Builder.Default
    private String phoneNumber      = "";
    @Builder.Default
    private String emailAddress     = "";
    @Builder.Default
    private String streetAddress    = "";

    /**
     * The equals method is overrided here to make more readable the code when making 
     * queries to our cache.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        boolean result = true;
        if (o instanceof Contact) {
            Contact c = (Contact) o;
            result &= atomicCompare(contactId,      c.contactId);
            result &= atomicCompare(firstName,      c.firstName);
            result &= atomicCompare(lastName,       c.lastName);
            result &= atomicCompare(phoneNumber,    c.phoneNumber);
            result &= atomicCompare(emailAddress,   c.emailAddress);
            result &= atomicCompare(streetAddress,  c.streetAddress);
        }
        return result;
    }

    /**
     * The hash code computation must be consistent with the equals method.ß
     */
    @Override
    public int hashCode() {
        return this.hashCode();
    }

    /**
     * This method makes the code more readable
     * @param s1 string to compare
     * @param s2 string to compare
     * @return true if one of the strings is the any string '*' of if both
     * are equals.
     */
    public static boolean atomicCompare(String s1, String s2) {
        return s1.equals("*") || s2.equals("*") || s1.equals(s2);
    }

    public void update(Contact update) {
        for (Field f : Contact.class.getDeclaredFields()) {
            try {
                f.setAccessible(true);
                String val = (String) f.get(update);
                if (val != null && !val.equals("*"))
                    f.set(this, val);
            } catch (IllegalAccessException iae) {
                throw new RuntimeException("This will never happen!");
            }
        }
    }
}
