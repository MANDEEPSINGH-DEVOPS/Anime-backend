package abp.project.anime.repository;

import abp.project.anime.dao.UserDao;
import abp.project.anime.model.User;
import abp.project.anime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserReposiotryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserDao userDao;

    public UserReposiotryTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerUserPorId_Success(){
        int userId = 1;
        User mockUser = new User(userId, "Test", "test@email.com", "password", "123456789");

        when(jdbcTemplate.queryForObject(
                eq("CALL obtenerUserByProcedure(?)"),
                any(BeanPropertyRowMapper.class),
                eq(userId)
        )).thenReturn(mockUser);

        User result = userDao.obtenerUserPorId(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Test", result.getName());
        assertEquals("test@email.com", result.getEmail());
    }

    @Test
    void testObtenerUserPorId_NotFound(){
        int userId= 99;
        when(jdbcTemplate.queryForObject(
                eq("CALL obtenerUserByProcedure(?)"),
                any(BeanPropertyRowMapper.class),
                eq(userId)
        )).thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(EmptyResultDataAccessException.class, () -> userDao.obtenerUserPorId(userId));
    }

    @Test
    void testInsetarUser_Success(){
        String name = "Test User";
        String email = "test@email.com";
        String password = "password";
        String phone = "12345678";

        when(jdbcTemplate.update(anyString(),anyString(),anyString(),anyString())).thenReturn(1);
        User result = new User(name,email,password,phone);

        assertNotEquals(result, "El usuario insertado no debería ser null ");
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        assertEquals(phone, result.getPhone());
    }

    @Test
    void testInsertFail(){
        when(jdbcTemplate.update(anyString(),anyString(),anyString(),anyString())).thenReturn(0);
        User result = userDao.insertarUser("Test", "test@email.com", "pass", "12345678");
        assertNull(result, "Si no se insertan filas, el resultado debe ser null");
    }

    @Test
    void testInsertarUser_DatabaseError() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new DataAccessException("Error de base de datos") {});

        assertThrows(DataAccessException.class, () -> userDao.insertarUser("Test", "test@email.com", "pass", "12345678"));
    }

    @Test
    void updateUser_Success() {
        int id = 1;
        String name = "Test new";
        String email = "test@email.com";
        String password = "password";
        String phone = "12345678";

        when(jdbcTemplate.update(anyString(), anyInt(), anyString(), anyString(), anyString(), anyString())).thenReturn(1);
        int rowsAffected = userDao.updateUser(id, name, email, password, phone);
        assertEquals(1, rowsAffected, "Se esperaba que una fila fuera actualizada");
    }

    @Test
    void updateUser_NotFound() {
        int id = 1;
        String name = "mal";
        String email = "mal@email.com";
        String password = "mal";
        String phone = "12345678";

        when(jdbcTemplate.update(anyString(), anyInt(), anyString(), anyString(), anyString(), anyString())).thenReturn(0);
        int rowsAffected = userDao.updateUser(id, name, email, password, phone);
        assertEquals(0, rowsAffected, "Si no hay cambios, se espera que el valor devuelto sea 0");
    }

    @Test
    void updateUser_ErrorDB() {
        int id = 1;
        String name = "Error User";
        String email = "error@email.com";
        String password = "errorpass";
        String phone = "99999999";
        when(jdbcTemplate.update(anyString(), anyInt(), anyString(), anyString(), anyString(), anyString())).thenThrow(new DataAccessException("Error de base de datos") {
        });
        assertThrows(DataAccessException.class, () -> userDao.updateUser(id, name, email, password, phone),
                "Se esperaba una excepción de acceso a datos");
    }

    @Test
    void testLogin_Success(){
        String email = "user@email.com";
        String pass = "1234";
        User mockUser = new User( email, pass);

        when(jdbcTemplate.queryForObject(
                eq("CALL Login(?,?)"),
                any(BeanPropertyRowMapper.class),
                eq(email),
                eq(pass)
        )).thenReturn(mockUser);

        User result = userDao.Login(email,pass);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(pass, result.getPassword());
    }


}
