package abp.project.anime.controller;

import abp.project.anime.model.Anime;
import abp.project.anime.service.AnimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnimeController {
    private AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/users/{id}/animes")
    public ResponseEntity get_all_animes_ordered_by_favorites(@PathVariable int id){
        return animeService.get_all_animes_ordered_by_favorites(id);
    }

    @GetMapping("/users/{iduser}/anime/{idanime}")
    public ResponseEntity is_favorite(@PathVariable int iduser, @PathVariable int idanime){
        return animeService.get_favorites(iduser, idanime);
    }
}
