package org.jcammm.demos.camel.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Contact {
    private String contactId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String streetAddress;

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

    public static void mainx(String[] args) {
        Contact c1 = new Contact("1", "uno", "one", "un", "eins", "um");
        Contact cx = new Contact("1", "uno", "one", "un", "eins", "um");
        Contact ca = new Contact("1", "uno", "*", "un", "eins", "um");
        Contact c2 = new Contact("2", "dos", "two", "deux", "zwei", "deus");
        Contact cb = new Contact("2", "*", "two", "deux", "zwei", "deus");

        System.out.println(c1.equals(c2));
        System.out.println(cx.equals(c1));
        System.out.println(c1.equals(ca));
        System.out.println(c1.equals(c2));
        System.out.println(cb.equals(c2));

    }
}
