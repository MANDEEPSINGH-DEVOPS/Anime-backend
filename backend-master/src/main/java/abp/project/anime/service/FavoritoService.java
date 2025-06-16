package abp.project.anime.service;

import abp.project.anime.dao.FavoritoRepository;
import abp.project.anime.model.Favorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    public ResponseEntity getFavoritoByIdUser(Favorito favorito){
        try{
            return ResponseEntity.ok(favoritoRepository.obtenerFavoritoByIdUser(favorito));
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al acceder a la base de datos: "+e.getMessage());
        }
    }

    public ResponseEntity insertFavorito(Favorito favorito){
        try{
            if(favoritoRepository.obtenerFavoritoByIds(favorito) == null){
                favoritoRepository.insertarFavorito(favorito);
                return ResponseEntity.status(HttpStatus.CREATED).body("Se ha creado el favorito con éxito.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ya hay un favorito con idanime "+ favorito.getIdanime() + " y iduser "+ favorito.getIduser());
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al acceder a la base de datos: "+e.getMessage());
        }
    }

    public ResponseEntity deleteFavorito(Favorito favorito){
        try{
            if(favoritoRepository.obtenerFavoritoByIds(favorito) != null){
                favoritoRepository.eliminarFavorito(favorito);
                return ResponseEntity.status(HttpStatus.CREATED).body("Se ha eliminado el favorito con éxito.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado ningún favorito con idanime "+ favorito.getIdanime() + " y iduser "+ favorito.getIduser());
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al acceder a la base de datos: "+e.getMessage());
        }
    }
}
