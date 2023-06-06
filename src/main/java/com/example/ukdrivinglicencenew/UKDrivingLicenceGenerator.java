package com.example.ukdrivinglicencenew;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UKDrivingLicenceGenerator {

    public static final String SURNAME_PADDING = "99999";
    public static final String INITIALS_PADDING = "9";
    public static final String ARBITRARY_DIGIT = "9";
    public static final String COMPUTER_CHECK_DIGIT = "AA";
    public static final String FEMALE_CHARACTER = "F";

    private final StringBuilder licenceBuilder;

    public UKDrivingLicenceGenerator() {
        licenceBuilder = new StringBuilder();
    }

    public String generateLicence(final String[] input) {
        final var surname = generatePaddedSurname(input);
        final var year = generateDecadeDigit(input);
        final var month = generateMonth(input);
        final var day = generateDay(input);
        final var yearDigit = generateYearDigit(input);
        final var initials = generateInitials(input);

        licenceBuilder
                .append(surname)
                .append(year)
                .append(month)
                .append(day)
                .append(yearDigit)
                .append(initials)
                .append(ARBITRARY_DIGIT)
                .append(COMPUTER_CHECK_DIGIT);

        return licenceBuilder.toString();
    }

    private static String generateInitials(String[] input) {
        final var firstName = input[0];
        final var middleName = input[1];
        final var initials = new StringBuilder();

        initials.append(Character.toUpperCase(firstName.charAt(0)));

        if (!middleName.isEmpty()) {
            initials.append(middleName.charAt(0));
            return initials.toString();
        }

        initials.append(INITIALS_PADDING);
        return initials.toString();
    }

    private static String generateDay(String[] input) {
        final var birthDateParts = getBirthdateParts(input);
        return birthDateParts[0];
    }

    private static String generateMonth(String[] input) {
        final var birthDate = input[3];
        final var gender = input[4];

        final var formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        final var parsedBirthDate = LocalDate.parse(birthDate, formatter);
        final var month = parsedBirthDate.getMonthValue();

        if (gender.equals(FEMALE_CHARACTER)) {
            return String.valueOf(month + 50);
        }

        if (month < 10) {
            NumberFormat f = new DecimalFormat("00");
            return f.format(month);
        }
        return String.valueOf(month);

    }

    private static Character generateYearDigit(String[] input) {
        final var birthDateParts = getBirthdateParts(input);
        final var year = birthDateParts[2];
        return year.charAt(3);
    }

    private static Character generateDecadeDigit(String[] input) {
        final var birthDateParts = getBirthdateParts(input);
        final var year = birthDateParts[2];
        return year.charAt(2);
    }

    private String generatePaddedSurname(final String[] input) {
        final var surname = input[2].toUpperCase();
        final var paddedSurname = surname + SURNAME_PADDING;
        final var surnameSubStrSurname = paddedSurname.substring(0, 5);
        return surnameSubStrSurname;
    }

    private static String[] getBirthdateParts(final String[] input) {
        final var birthDate = input[3];
        return birthDate.split("-");
    }
}
