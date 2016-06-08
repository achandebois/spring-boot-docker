package com.chandebois.rest;

import com.chandebois.boot.Application;
import com.chandebois.entities.User;
import com.chandebois.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nonok on 08/06/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class UserControllerUnitTest {

    @Autowired
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepositoryMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_not_find_any_user() throws Exception {
        //Given
        Mockito.when(userRepositoryMock.findOne("1")).thenReturn(null);
        //When
        Response res = userController.getUser("1");
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void should_return_all_user() throws Exception {
        //Given
        Mockito.when(userRepositoryMock.findAll()).thenReturn(Arrays.asList(new User()));
        //When
        Response res = userController.getAllUser();
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void should_return_no_content_when_no_users_in_database() throws Exception {
        //Given
        Mockito.when(userRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        //When
        Response res = userController.getAllUser();
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void should_find_one_user() throws Exception {
        //Given
        Mockito.when(userRepositoryMock.findOne("1")).thenReturn(new User());
        //When
        Response res = userController.getUser("1");
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void should_not_insert_a_user() throws Exception {
        //Given
        User user = null;
        //When
        Response res = userController.createUser(user);
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void should_insert_a_user() throws Exception {
        //Given
        User user = new User();
        user.setNickName("acs_rest");
        //When
        Response res = userController.createUser(user);
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        Mockito.verify(userRepositoryMock).save(user);
    }

    @Test
    public void should_update_a_user() throws Exception {
        //Given
        User user = new User();
        user.setId("99");
        user.setNickName("acs_rest");
        //When
        Response res = userController.updateUser(user);
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
        Mockito.verify(userRepositoryMock).save(user);
    }

    @Test
    public void should_not_update_a_user_if_not_exists() throws Exception {
        //Given
        User user = new User();
        user.setNickName("acs_rest");
        //When
        Response res = userController.updateUser(user);
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }


    @Test
    public void should_delete_a_user() throws Exception {
        //Given
        Mockito.when(userRepositoryMock.exists("1")).thenReturn(true);
        //When
        Response res = userController.deleteUser("1");
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
        Mockito.verify(userRepositoryMock).delete("1");
    }

    @Test
    public void should_not_delete_a_user_if_not_exists() throws Exception {
        //Given
        Mockito.when(userRepositoryMock.exists("1")).thenReturn(false);
        //When
        Response res = userController.deleteUser("1");
        //Then
        assertThat(res.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

}
