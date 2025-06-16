package abp.project.anime.controller.anime;

import abp.project.anime.model.Anime;
import abp.project.anime.service.AnimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnimeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AnimeService animeService;  // Mockeamos el servicio

	@Test
	void test_all_animes_ordered_by_favorites() throws Exception {
		List<Anime> animes = Arrays.asList(
				new Anime(1, "nombre", "desc", "type", 2020, "image1", "originalname", "+18", "demography", "accion", "imagen2", "imagen3", 1, true),
				new Anime(2, "nombre2", "desc2", "type2", 2020, "image1_2", "originalname2", "+18_2", "demography_2", "accion_2", "imagen2_2", "imagen3_2", 1, true)
		);

		when(animeService.get_all_animes_ordered_by_favorites(anyInt()))
				.thenReturn(ResponseEntity.ok(animes));

		mockMvc.perform(get("/users/1/animes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value("nombre"))
				.andExpect(jsonPath("$[0].year").value(2020));
	}

	@Test
	void test_is_favorite() throws Exception{
		Anime anime = new Anime(1, "nombre", "desc", "type", 2020, "image1", "originalname", "+18", "demography", "accion", "imagen2", "imagen3", 1, true);
		when(animeService.get_favorites(anyInt(), anyInt())).thenReturn(ResponseEntity.ok(anime));
		mockMvc.perform(get("/users/1/anime/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.favorito").value(true));
	}
}