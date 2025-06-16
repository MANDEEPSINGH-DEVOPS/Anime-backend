//package abp.project.anime.controller.anime;
//
//import abp.project.anime.dao.AnimeRepository;
//import abp.project.anime.model.Anime;
//import abp.project.anime.service.AnimeService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class AnimeDaoRepository {
//        @Autowired
//        private MockMvc mockMvc;
//
//        @Autowired
//        private AnimeService animeService;
//
//        @MockBean
//        private AnimeRepository animeRepository;
//
//        @Test
//        void test_get_all_animes_ordered_by_favorites() throws Exception {
//            List<Anime> animes = Arrays.asList(
//                    new Anime(1, "nombre", "desc", "type", 2020, "image1", "originalname", "+18", "demography", "accion", "imagen2", "imagen3", 1, true),
//                    new Anime(2, "nombre2", "desc2", "type2", 2020, "image1_2", "originalname2", "+18_2", "demography_2", "accion_2", "imagen2_2", "imagen3_2", 1, true)
//            );
//
//            when(animeRepository.get_all_animes_ordered_by_favorites(anyInt()))
//                    .thenReturn(animes);
//
//            ResponseEntity<?> response = animeService.get_all_animes_ordered_by_favorites(1);
//
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(2, ((List<Anime>) response.getBody()).size());
//        }
//    }
//}
