package lk.ijse.freshBite.regex;

import java.util.regex.Pattern;

public class RegexPattern {
    private static final Pattern namePattern = Pattern.compile("^[a-zA-Z '.-]{4,}$");
    private static final Pattern idPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");
    private static final Pattern registrationIdPattern = Pattern.compile("^[I][T][0-1]{1,}$");
    private static final Pattern emailPattern = Pattern.compile("(^[a-zA-Z0-9_.-]+)@([a-zA-Z]+)([\\.])([a-zA-Z]+)$");
    private static final Pattern cityPattern = Pattern.compile("[a-zA-Z]{4,}$");
    private static final Pattern mobilePattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");
    private static final Pattern addressPattern = Pattern.compile("^[A-Za-z0-9'\\/\\.\\,]{5,}$");
    private static final Pattern postalCodePattern = Pattern.compile("^\\d{5}$");
    private static final Pattern oldIDPattern = Pattern.compile("^[0-9]{9}[vVxX]$");
    private static final Pattern doublePattern = Pattern.compile("^[0-9]+\\.?[0-9]*$");

    private static final Pattern intPattern = Pattern.compile("^[1-9][0-9]?$|^100$");
    public static Pattern getRegistrationIdPattern() {return registrationIdPattern;}

    public static Pattern getIntPattern() {return intPattern;}
    public static Pattern getOldIDPattern() {return oldIDPattern;}
    public static Pattern getPostalCodePattern() {return postalCodePattern;}
    public static Pattern getAddressPattern() {return addressPattern;}
    public static Pattern getMobilePattern() {
        return mobilePattern;
    }
    public static Pattern getCityPattern() {
        return cityPattern;
    }
    public static Pattern getEmailPattern() {
        return emailPattern;
    }
    public static Pattern getNamePattern() {
        return namePattern;
    }
    public static Pattern getIdPattern() {
        return idPattern;
    }

    public static Pattern getDoublePattern() {
        return doublePattern;
    }
}
