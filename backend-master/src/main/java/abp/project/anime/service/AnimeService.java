package abp.project.anime.service;

import abp.project.anime.dao.AnimeRepository;
import abp.project.anime.model.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {
    @Autowired
    private AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    public ResponseEntity get_all_animes_ordered_by_favorites(int id){
        try{
            return ResponseEntity.ok(animeRepository.get_all_animes_ordered_by_favorites(id));
        }catch (DataAccessException e){
            String errorMessage = "Error al acceder a la base de datos: "+e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    public ResponseEntity get_favorites(int iduser, int idanime){
        try{
            return ResponseEntity.ok(animeRepository.get_favorites(iduser, idanime));
        }catch (DataAccessException e){
            String errorMessage = "Error al acceder a la base de datos: "+e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}
