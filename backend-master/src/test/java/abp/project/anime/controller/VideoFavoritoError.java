package abp.project.anime.controller;

import abp.project.anime.dao.FavoritoRepository;
import abp.project.anime.dao.VideoRepository;
import abp.project.anime.model.Favorito;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VideoFavoritoError {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoRepository videoRepository;

    @MockBean
    private FavoritoRepository favoritoRepository;

    @Test
    public void getVideoByIdError() throws Exception {
        when(videoRepository.getAllVideosByIdAnime(1)).thenThrow(new DataAccessException("Error"){});

        mockMvc.perform(get("/anime/1/videos"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getFavoritoByIdUserError() throws Exception {
        when(favoritoRepository.obtenerFavoritoByIdUser(any(Favorito.class))).thenThrow(new DataAccessException("Error"){});

        mockMvc.perform(get("/user/1/favoritos"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void insertFavoritoError() throws Exception {
        when(favoritoRepository.insertarFavorito(any(Favorito.class))).thenThrow(new DataAccessException("Error"){});

        mockMvc.perform(MockMvcRequestBuilders.post("/user/1/anime/1/favoritos"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteFavoritoError() throws Exception {
        when(favoritoRepository.obtenerFavoritoByIds(any(Favorito.class))).thenThrow(new DataAccessException("Error"){});

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1/anime/1/favoritos"))
                .andExpect(status().isInternalServerError());

    }


}
