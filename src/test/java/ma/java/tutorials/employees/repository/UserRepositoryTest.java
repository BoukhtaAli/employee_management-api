package ma.java.tutorials.employees.repository;

import ma.java.tutorials.employees.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should Find User by Username")
    void findByUsername() {

        User user = new User();
        user.setUsername("zerthue");

        this.userRepository.save(user);

        assertThat(this.userRepository.findByUsername(user.getUsername()),
                is(notNullValue()));
    }

    @AfterEach
    void deleteAll(){
        this.userRepository.deleteAll();
    }
}