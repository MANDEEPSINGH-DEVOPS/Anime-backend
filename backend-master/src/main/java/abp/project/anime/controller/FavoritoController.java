package abp.project.anime.controller;

import abp.project.anime.model.Favorito;
import abp.project.anime.service.FavoritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FavoritoController {
    private FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @GetMapping("/user/{id}/favoritos")
    public ResponseEntity getFavoritoByIdUser(@PathVariable int id){
        return favoritoService.getFavoritoByIdUser(new Favorito(id, -1));
    }

    @PostMapping("/user/{idUser}/anime/{idAnime}/favoritos")
    public ResponseEntity updateFavorito(@PathVariable int idUser, @PathVariable int idAnime){
        return favoritoService.insertFavorito(new Favorito(idUser, idAnime));
    }

    @DeleteMapping("/user/{idUser}/anime/{idAnime}/favoritos")
    public ResponseEntity deleteFavorito(@PathVariable int idUser, @PathVariable int idAnime){
        return favoritoService.deleteFavorito(new Favorito(idUser, idAnime));
    }
}
