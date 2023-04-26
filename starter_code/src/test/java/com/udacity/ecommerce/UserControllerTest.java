package com.udacity.ecommerce;

import com.udacity.ecommerce.controllers.UserController;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.ecommerce.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private final UserRepository users = mock(UserRepository.class);

    @Mock
    private final CartRepository cartRepository = mock(CartRepository.class);

    private final BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUserHappyPath() throws Exception{
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername(Constant.USER_NAME);
        userRequest.setPassword(Constant.PASSWORD);
        userRequest.setConfirmPassword(Constant.PASSWORD);

        ResponseEntity<User> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals(Constant.USER_NAME, user.getUsername());
    }

    @Test
    public void findByIdFoundTest(){
        User user = createUser();

        when(users.findById(Constant.USER_ID)).thenReturn(Optional.of(user));
        ResponseEntity<User> response = userController.findById(Constant.USER_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void findByIdNotFoundTest(){
        ResponseEntity<User> response = userController.findById(Constant.USER_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findByUserNameFoundTest(){
        User user = createUser();

        when(users.findByUsername(Constant.USER_NAME)).thenReturn(user);
        ResponseEntity<User> response = userController.findByUserName(Constant.USER_NAME);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        assertEquals(Constant.USER_ID.longValue(), user.getId());
        assertEquals(Constant.USER_NAME, user.getUsername());
        assertEquals(Constant.PASSWORD, user.getPassword());
    }

    @Test
    public void findByUserNameNotFoundTest(){
        ResponseEntity<User> response = userController.findByUserName(Constant.USER_NAME);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private User createUser(){
        User user = new User();

        user.setId(Constant.USER_ID);
        user.setUsername(Constant.USER_NAME);
        user.setPassword(Constant.PASSWORD);

        return user;
    }

}