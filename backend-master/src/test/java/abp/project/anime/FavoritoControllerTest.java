package abp.project.anime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getFavoritoByIdUserOk() throws Exception {
        mockMvc.perform(get("/user/2/favoritos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].id").exists())
                .andExpect(jsonPath("$[*].name").exists())
                .andExpect(jsonPath("$[*].description").exists())
                .andExpect(jsonPath("$[*].type").exists())
                .andExpect(jsonPath("$[*].year").exists())
                .andExpect(jsonPath("$[*].image").exists())
                .andExpect(jsonPath("$[*].originalname").exists())
                .andExpect(jsonPath("$[*].rating").exists())
                .andExpect(jsonPath("$[*].demography").exists())
                .andExpect(jsonPath("$[*].genre").exists())
                .andExpect(jsonPath("$[*].image2").exists())
                .andExpect(jsonPath("$[*].image3").exists())
                .andExpect(jsonPath("$[*].active").exists())
                .andExpect(jsonPath("$[*].favorito").exists())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void getFavoritoByIdUserEmpty() throws Exception {
        mockMvc.perform(get("/user/100/favoritos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    public void getFavoritoByIdUserError() throws Exception {
        mockMvc.perform(get("/user/e/favoritos"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void insertIDeleteFavoritoOkIError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/1/anime/1/favoritos"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Se ha creado el favorito con éxito."))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/1/anime/1/favoritos"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Ya hay un favorito con idanime 1 y iduser 1"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1/anime/1/favoritos"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Se ha eliminado el favorito con éxito."))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1/anime/1/favoritos"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("No se ha encontrado ningún favorito con idanime 1 y iduser 1"))
                .andExpect(status().isNotFound());
    }

}
