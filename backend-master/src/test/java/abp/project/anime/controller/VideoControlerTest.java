package abp.project.anime.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VideoControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getVideoByIdOk() throws Exception {
        mockMvc.perform(get("/anime/27/videos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].idanime").value(hasItem(27)))
                .andExpect(jsonPath("$[*].episode").exists())
                .andExpect(jsonPath("$[*].url").exists())
                .andExpect(jsonPath("$[*].image").exists())
                .andExpect(jsonPath("$.size()").value(6));
    }

    @Test
    public void getVideoByIdEmpty() throws Exception {
        mockMvc.perform(get("/anime/100/videos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(0));
    }

}
