package com.chandebois.repositories;

import com.chandebois.boot.Application;
import com.chandebois.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nonok on 08/06/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void should_return_an_empty_list_of_users() throws Exception {
        //When
        List<User> users = userRepository.findAll();
        //Then
        assertThat(users).isEmpty();
    }

    @Test
    public void should_return_a_user_by_object_after_creation() throws Exception {
        //Given
        User user = new User();
        user.setNickName("acs_test");
        //When
        User userCreated = userRepository.insert(user);
        //Then
        assertThat(userCreated.getId()).isNotNull();
        assertThat(userCreated.getNickName()).isEqualTo("acs_test");
    }

    @Test
    public void should_find_a_user_by_nickname_after_creation() throws Exception {
        //Given
        User user = new User();
        user.setNickName("acs_test");
        //When
        userRepository.insert(user);
        User userCreated = userRepository.findByNickName("acs_test");
        //Then
        assertThat(userCreated.getId()).isNotNull();
        assertThat(userCreated.getNickName()).isEqualTo("acs_test");
    }

    @Test
    public void should_return_an_updated_user_after_modification() throws Exception {
        //Given
        User user = new User();
        user.setNickName("acs_test");
        User userCreated = userRepository.insert(user);
        //When
        userCreated.setNickName("acs_test_updated");
        User userUpdated = userRepository.save(userCreated);
        //Then
        assertThat(userUpdated.getId()).isNotNull();
        assertThat(userUpdated.getNickName()).isEqualTo("acs_test_updated");
    }

    @Test
    public void should_delete_a_user_after_insertion() throws Exception {
        //Given
        User user = new User();
        user.setNickName("acs_test");
        User userCreated = userRepository.insert(user);
        //When
        userRepository.delete(userCreated);
        //Then
        User userDeleted = userRepository.findByNickName("acs_test");
        assertThat(userDeleted).isNull();
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }
}
