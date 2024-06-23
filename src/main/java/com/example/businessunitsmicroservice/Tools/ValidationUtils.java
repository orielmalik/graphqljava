package com.example.businessunitsmicroservice.Tools;

import com.example.businessunitsmicroservice.Boundaries.UnitBoundary;
import com.example.businessunitsmicroservice.Entities.UnitEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ValidationUtils {


    public static boolean isEmailFormat(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String e = email.replace(" ", "");
        String numeric = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return e.matches(numeric);
    }

    public static String toStringdateFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);
        return (formattedDate);

    }

    public static boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = dateFormat.parse(dateString.replace(" ", ""));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public static Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.parse(dateStr);
    }

    public static LocalDate fromStringToLocalDatte(String dateString) {
        LocalDate parsedDate = null;
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


    public static UnitBoundary[] convertHashSetToArray(Set<UnitEntity> set) {
        if (set == null || set.isEmpty()) {
            return null;
        }


        // Create a new set to hold non-null values
        Set<UnitBoundary> nonNullSet = new HashSet<>();

        // Iterate over the set and add only non-null values to the new set
        Iterator<UnitEntity> iterator = set.iterator();
        while (iterator.hasNext()) {
            UnitBoundary item = new UnitBoundary( iterator.next());
            if (item != null) {
                nonNullSet.add(item);
            }
        }
        UnitBoundary[] arrayType = new UnitBoundary[nonNullSet.size()];
        return nonNullSet.toArray(arrayType);
    }


}
