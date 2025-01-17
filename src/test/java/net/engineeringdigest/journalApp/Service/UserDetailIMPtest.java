package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;
@Disabled
public class UserDetailIMPtest {
    @InjectMocks
    private UserDetailIMP userDetailIMP;

@Mock
private UserRepository userRepository;

@BeforeEach
void setup(){
    MockitoAnnotations.initMocks(this);
}
    @Test
    void loadUserByUsername(){
        // Assuming userRepository is your mock object
        when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn((net.engineeringdigest.journalApp.entity.User) User.builder().username("sansu").password("dgsdvdsvs").build());

        UserDetails user = userDetailIMP.loadUserByUsername("sansu");
        Assertions.assertNotNull(user);


    }
}
