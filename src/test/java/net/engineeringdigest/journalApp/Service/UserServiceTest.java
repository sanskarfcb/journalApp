package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Disabled
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Disabled
    @Test
    public void findbyusername(){

        assertNotNull(userRepository.findByUsername("sansu"));
    }
    @ParameterizedTest
    @CsvSource({
            "1,3,4",
            "4,5,9",
            "1,3,2"

    })
    public void test(int a , int b,int expexted){
        assertEquals(expexted,a+b);
    }
}
