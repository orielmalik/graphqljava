package Tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtils {


    public static boolean isEmailFormat(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String e = email.replace(" ", "");
        String numeric = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return e.matches(numeric);
    }

    public static  String toStringdateFormat(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);
        return  (formattedDate);

    }public static boolean isValidLocalDateFormat(String dateString) {
        try {
            // Parse the String with a specific format pattern (replace with your desired format)
            LocalDate.parse(dateString.replace(" ",""), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    public static LocalDate fromStringToLocalDatte(String dateString )
    {
        LocalDate parsedDate =null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parse the String into LocalDate object
        //when we call this function we guess string at format

        parsedDate = LocalDate.parse(dateString, formatter);

        return parsedDate;
    }
    public static boolean hasTwoUppercaseLetters(String input) {
        int uppercaseCount = 0;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isUpperCase(ch)) {
                uppercaseCount++;
                if (uppercaseCount > 2) {
                    return false;
                }
            }
        }

        return uppercaseCount == 2;
    }
}
