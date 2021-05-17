package com.programming.techie;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {
    ContactManager contactManager;

    @BeforeAll
    public void setupAll(){
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setup(){
        contactManager = new ContactManager();
    }

    @Test
    @Disabled
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
        .filter(contact -> contact.getFirstName().equals("John") &&
                contact.getLastName().equals("Doe") &&
                contact.getPhoneNumber().equals("0123456789"))
        .findAny().isPresent());
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowRunTimeExceptionWhenFirstNameIsNull(){
        ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class, ()->{
            contactManager.addContact(null, "Doe", "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowRunTimeExceptionWhenLastNameIsNull(){
        Assertions.assertThrows(RuntimeException.class, ()->{
            contactManager.addContact("John", null, "0123456789");
        });

    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowRunTimeExceptionWhenPhoneNumberIsNull(){
        Assertions.assertThrows(RuntimeException.class, ()->{
            contactManager.addContact("John", "Doe", null);
        });
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("Should be executed at the end of the test");
    }

    @Test
    @DisplayName("Should Create Contact Only on Windows OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enable only on MAX OS")
    public void shouldCreateContactOnMAC() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Should Not Create Contact Only on Windows OS")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable only on MAX OS")
    public void shouldNotateContactOnWindows() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    public void shouldCreateContactOnDEV() {
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());

    }


    @Nested
    class RepeatedNestedTest {
        @DisplayName("Repeat ContactCreation Test for 5 Times")
        @RepeatedTest(value = 5, name = "Repeating Test {currentRepetition} of {totalRepetitions}")
        public void shouldCreateContactRepeatedly() {
            contactManager.addContact("John", "Doe", "0123456789");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
    }

    @Nested
    class ParameterizedNestedTest {
        @DisplayName("Value Source - Repeat ContactCreation Test for 5 Times")
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456781", "0123456782"})
        public void shouldCreateContactUsingValueSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("CSV Source - Repeat ContactCreation Test for 5 Times")
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456781", "0123456782"})
        public void shouldCreateContactUsingCsvSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("CSV File Source - Repeat ContactCreation Test for 5 Times")
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void shouldCreateContactUsingCsvFileSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
    }

    @DisplayName("Method Source - Repeat ContactCreation Test for 5 Times")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldCreateContactUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456781", "0123456782");
    }

}