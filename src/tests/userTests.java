package tests;

import static org.junit.jupiter.api.Assertions.*;

import com.mohamed.user;
import org.junit.jupiter.api.*;

public class userTests {
    user testUser = new user("testUser", "testName", "testSurname", "testPassword");
    @Test
    void should_change_user_vars(){
        user testUserExpected = new user("testUserEdited", "testNameEdited", "testSurnameEdited", "testPassEdited");
        testUser.updateUser("testUserEdited", "testNameEdited", "testSurnameEdited", "testPassEdited");
        assertEquals(testUserExpected.getUsername(), testUser.getUsername());
        assertEquals(testUserExpected.getName(), testUser.getName());
        assertEquals(testUserExpected.getSurname(), testUser.getSurname());
        assertEquals(testUserExpected.getPassword(), testUser.getPassword());
    }
    @Test
    void should_return_username(){
        assertEquals("testUser", testUser.getUsername());
    }
    @Test
    void should_return_name(){
        assertEquals("testName", testUser.getName());
    }
    @Test
    void should_return_surname(){
        assertEquals("testSurname", testUser.getSurname());
    }
    @Test
    void should_return_password(){
        assertEquals("testPassword", testUser.getPassword());
    }

}