package com.example.ukdrivinglicencenew;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UKDrivingLicenceGeneratorTests {

    private UKDrivingLicenceGenerator ukDrivingLicenceGenerator;

    @BeforeEach
    void setUp() {
        ukDrivingLicenceGenerator = new UKDrivingLicenceGenerator();
    }
    //SMITH001010JJ9AA


    @ParameterizedTest()
    @MethodSource("provideLicenceNumbers")
    public void it_should_return_first_5_chars_of_surname_even_its_longer_than_5_chars(final String[] input, String expected) {

        // given & when
        final var result = ukDrivingLicenceGenerator.generateLicence(input);
        final var lastNameSubstr = result.substring(0, 5);

        // then
        assertEquals(expected, lastNameSubstr);
    }

    @Test()
    public void it_should_return_decade_digit_from_the_year_of_birth() {

        // given
        final var input = new String[]{"John", "James", "Smith", "01-Jan-1987", "M"};

        //when
        final var result = ukDrivingLicenceGenerator.generateLicence(input);
        final var birthDateSubstr = result.substring(5, 6);

        // then
        assertEquals("8", birthDateSubstr);
    }

    @ParameterizedTest()
    @MethodSource("provideLicenceNumbersForMonth")
    public void it_should_return_month_of_birth_when_person_is_male(final String[] input, String expected) {

        // given & when
        final var result = ukDrivingLicenceGenerator.generateLicence(input);
        final var monthDateSubstr = result.substring(6, 8);

        // then
        assertEquals(expected, monthDateSubstr);
    }

    @Test()
    public void it_should_return_date_of_month() {
        // given
        final var input = new String[]{"Johanna", "", "Gibbs", "13-Dec-1981", "F"};
        //SMITH001010JJ9AA

        //when
        final var result = ukDrivingLicenceGenerator.generateLicence(input);
        final var dateOfMonthSubstr = result.substring(8, 10);

        // then
        assertEquals("13", dateOfMonthSubstr);
    }

    @Test()
    public void it_should_return_year_digit_from_the_year_of_birth() {

        // given
        final var input = new String[]{"John", "James", "Smith", "01-Jan-1987", "M"};

        //when
        final var result = ukDrivingLicenceGenerator.generateLicence(input);
        final var birthDateSubstr = result.substring(10, 11);

        // then
        assertEquals("7", birthDateSubstr);
    }

    @Test()
    public void it_should_return_initials_of_first_and_middle_name() {

        // given
        final var input = new String[]{"John", "James", "Smith", "01-Jan-1987", "M"};

        //when
        final var result = ukDrivingLicenceGenerator.generateLicence(input);
        final var initialsSubstr = result.substring(11, 13);

        // then
        assertEquals("JJ", initialsSubstr);
    }

    @Test()
    public void it_should_return_initials_of_first_name_padded_with_9() {

        // given
        final var input = new String[]{"John", "", "Smith", "01-Jan-1987", "M"};

        //when
        final var result = ukDrivingLicenceGenerator.generateLicence(input);
        final var initialsSubstr = result.substring(11, 13);

        // then
        assertEquals("J9", initialsSubstr);
    }

    @Test()
    public void it_should_return_add_9_as_arbitrary_digit() {

        // given
        final var input = new String[]{"John", "", "Smith", "01-Jan-1987", "M"};

        //when
        final var result = ukDrivingLicenceGenerator.generateLicence(input);
        final var arbitraryDigit = result.substring(13, 14);

        // then
        assertEquals("9", arbitraryDigit);
    }

    @Test()
    public void it_should_return_add_AA_as_computer_check_digits() {

        // given
        final var input = new String[]{"John", "", "Smith", "01-Jan-1987", "M"};

        //when
        final var result = ukDrivingLicenceGenerator.generateLicence(input);
        final var computerCheckDigits = result.substring(14, 16);

        // then
        assertEquals("AA", computerCheckDigits);
    }

    private static Stream<Arguments> provideLicenceNumbers() {
        return Stream.of(
                Arguments.of(new String[]{"John", "James", "Smith", "01-Jan-2000", "M"}, "SMITH"),
                Arguments.of(new String[]{"John", "James", "Smithk", "01-Jan-2000", "M"}, "SMITH"),
                Arguments.of(new String[]{"John", "James", "Smit", "01-Jan-2000", "M"}, "SMIT9")
        );
    }

    private static Stream<Arguments> provideLicenceNumbersForMonth() {
        return Stream.of(
                Arguments.of(new String[]{"John", "James", "Smith", "01-Sep-1987", "M"}, "09"),
                Arguments.of(new String[]{"Johanna", "", "Gibbs", "13-Dec-1981", "F"}, "62")
                );
    }
}
