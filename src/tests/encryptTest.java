package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.mohamed.utils.encrypt;

import java.security.NoSuchAlgorithmException;

public class encryptTest {

    @Test
    void should_encrypt() throws NoSuchAlgorithmException {
        String expected = "32d2c74b3528fe41bd7cbfbb4fd735cd89cad064863b02bbb924b7d1c20dbec98553cac82117c262220da1640fa84fb7c3096839168cd602088f1edcb292138b";
        String actual = new encrypt().securePass("testPass");
        assertEquals(expected, actual);
    }
//    @Test
//    void should_throw_an_exception() throws NoSuchAlgorithmException {
//        encrypt encrypt = new encrypt();
//        encrypt.algorithm = "asdf";
//        assertThrows(NoSuchAlgorithmException, encrypt.securePass("asdf"));
//    }
}
