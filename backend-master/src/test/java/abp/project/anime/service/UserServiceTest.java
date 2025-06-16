package abp.project.anime.service;

import abp.project.anime.dao.UserDao;
import abp.project.anime.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }

    //Login
    @Test
    void testLoginSuccess() {
        User user = new User(1, "Admin", "admin@email.com", "1234", "12344567");
        when(userDao.Login("admin@email.com", "1234")).thenReturn(user);

        User input = new User("admin@email.com", "1234");
        ResponseEntity response = userService.login(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testLoginNotFound() {
        when(userDao.Login("mal@email.com", "4321")).thenReturn(null);
        User input = new User("mal@email.com", "4321");
        ResponseEntity response = userService.login(input);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Correo o contraseña incorrecto", response.getBody());
    }

    @Test
    void testLoginInternalError() {
        when(userDao.Login(anyString(), anyString())).thenThrow(new RuntimeException("DB error"));
        User test = new User(anyString(), anyString());
        ResponseEntity response = userService.login(test);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody());
    }

    //getUser
    @Test
    void testGet_User() {
        User user = new User(1, "Admin", "admin@email.com", "1234", "12345678");
        when(userDao.obtenerUserPorId(1)).thenReturn(user);

        ResponseEntity response = userService.getUser(1);

        verify(userDao).obtenerUserPorId(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGet_UserNOTFOUND() {
        when(userDao.obtenerUserPorId(1)).thenReturn(null);

        ResponseEntity response = userService.getUser(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se ha encontrado ningún user con id 1", response.getBody());
    }

    @Test
    void testGet_UserNotFound_IdZero() {
        User userWithZero = new User(0, "Zero", "zero@gmail.com", "000", "00000000");
        when(userDao.obtenerUserPorId(1)).thenReturn(userWithZero);

        ResponseEntity response = userService.getUser(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se ha encontrado ningún user con id 1", response.getBody());
    }

    @Test
    void testGet_UserInternalError() {
        when(userDao.obtenerUserPorId(anyInt())).thenThrow(new RuntimeException("Error al acceder a la base de datos: Error al acceder a la base de datos DB error"));
        ResponseEntity response = userService.getUser(anyInt());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al acceder a la base de datos", response.getBody());
    }

    //insertUser
    @Test
    void testInsertUser() {
        User user = new User(1, "Admin", "admin@email.com", "1234", "12345678");
        when(userDao.insertarUser("Admin", "admin@email.com", "1234", "12345678")).thenReturn(user);

        ResponseEntity response = userService.insertUser(user);

        verify(userDao).insertarUser("Admin", "admin@email.com", "1234", "12345678");
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testInsertUserNotFound() {
        when(userDao.insertarUser("mal", "mal@email.com", "000", "20000000")).thenReturn(null);
        User input = new User("mal", "mal@email.com", "000", "20000000");
        ResponseEntity response = userService.insertUser(input);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No se pudo crear el usuario. Verifica los datos proporcionados.", response.getBody());
    }

    @Test
    void testInsertUserDataAccessException() {
        when(userDao.insertarUser(anyString(), anyString(), anyString(), anyString())).thenThrow(new DataAccessException("Error de base de datos") {
        });
        User test = new User("mock", "mock@email.com", "pass", "12345678");

        ResponseEntity response = userService.insertUser(test);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error de base de datos: Error de base de datos", response.getBody());
    }

    @Test
    void testInsertUserException() {
        when(userDao.insertarUser(anyString(), anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Error de base de datos") {
        });
        User test = new User("mock", "mock@email.com", "pass", "12345678");

        ResponseEntity response = userService.insertUser(test);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error inesperado: Error de base de datos", response.getBody());
    }

    //updateUser
    @Test
    void testUpdateUser() {
        User user = new User(1, "Admin1", "admin@email.com", "1234", "12345678");
        when(userDao.updateUser(1, "Admin1", "admin@email.com", "1234", "12345678")).thenReturn(1);

        ResponseEntity response = userService.updateUser(1, user);

        verify(userDao, times(1)).updateUser(1, "Admin1", "admin@email.com", "1234", "12345678");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testUpdateNotFound() {
        User user = new User(0, "mal1", "mal@email.com", "1234", "12345678");
        when(userDao.updateUser(0, "mal1", "mal@email.com", "1234", "12345678")).thenReturn(0);

        ResponseEntity response = userService.updateUser(0, user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No se pudo actualizar el usuario. Verifica los datos proporcionados.", response.getBody());
    }

    @Test
    void testUpdateUserDataAccessException() {
        when(userDao.updateUser(anyInt(), anyString(), anyString(), anyString(), anyString())).thenThrow(new DataAccessException("Error de base de datos") {
        });
        User test = new User(0, "mock", "mock@email.com", "pass", "12345678");

        ResponseEntity response = userService.updateUser(0, test);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error de base de datos: Error de base de datos", response.getBody());
    }

    @Test
    void testUpdateUserException() {
        when(userDao.updateUser(anyInt(), anyString(), anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Error de base de datos") {
        });
        User test = new User(0, "mock", "mock@email.com", "pass", "12345678");

        ResponseEntity response = userService.updateUser(0, test);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error inesperado: Error de base de datos", response.getBody());
    }
}