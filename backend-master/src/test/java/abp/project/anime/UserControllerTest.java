package abp.project.anime;

import abp.project.anime.model.User;
import abp.project.anime.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    //Login
    @Test
    void testLoginSuccess() throws  Exception{
        User inputUser = new User("admin@email.com", "1234");
        User returnedUser = new User(1,"Admin","admin@email.com","1234","12345678");

        when(userService.login(any(User.class)))
                .thenReturn(ResponseEntity.ok(returnedUser));

        String jsonInput = """
                {
                    "email": "admin@email.com",
                    "password": "1234"
                }
                """;

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@email.com"))
                .andExpect(jsonPath("$.name").value("Admin"));
    }

    //getId
    @Test
    public void testObtenerUsuario() throws Exception{
        User user = new User(1,"Admin","admin@email.com","1234","12345678");

        when(userService.getUser(1)).thenReturn(ResponseEntity.ok(user));

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@email.com"))
                .andExpect(jsonPath("$.password").value("1234"));
    }

    //register
    @Test
    void testRegisterSuccess() throws  Exception{
        User inputUser = new User("mandeep","mandeep@email.com","1234","123456789");
        User returnedUser = new User(2,"mandeep","mandeep@email.com","1234","123456789");

        when(userService.insertUser(any(User.class)))
                .thenReturn(ResponseEntity.ok(returnedUser));

        String jsonInput = """
                {
                     "name": "mandeep",
                     "email": "mandeep@email.com",
                     "password": "1234",
                     "phone": "123456789"
                }
                """;

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mandeep"))
                .andExpect(jsonPath("$.email").value("mandeep@email.com"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.phone").value("123456789"));
    }

    //update
    @Test
    void testUserUpdateSuccess() throws  Exception{
        User inputUser = new User("mandeep","mandeep1@email.com","1234","123456789");
        User returnedUser = new User(2,"mandeep","mandeep1@email.com","1234","123456789");

        when(userService.updateUser(eq(2),any(User.class)))
                .thenReturn(ResponseEntity.ok(returnedUser));

        String jsonInput = """
                {
                     "name": "mandeep",
                     "email": "mandeep1@email.com",
                     "password": "1234",
                     "phone": "123456789"
                }
                """;

        mockMvc.perform(put("/user/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mandeep"))
                .andExpect(jsonPath("$.email").value("mandeep1@email.com"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.phone").value("123456789"));
    }

}
