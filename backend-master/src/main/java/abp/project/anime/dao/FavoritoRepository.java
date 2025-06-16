package abp.project.anime.dao;

import abp.project.anime.model.Anime;
import abp.project.anime.model.Favorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoritoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FavoritoRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Anime> obtenerFavoritoByIdUser(Favorito favorito) {
        String sql = "call get_favorito_by_id_user(?);";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Anime.class), favorito.getIduser());
    }

    public Favorito obtenerFavoritoByIds(Favorito favorito) {
        String sql = "call get_favorito_by_ids(?, ?);";
        List<Favorito> res = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Favorito.class), favorito.getIduser(), favorito.getIdanime());
        return res.isEmpty() ? null : res.getFirst();
    }

    public int insertarFavorito(Favorito favorito) {
        String sql = "call insert_favorito(?, ?);";
        return jdbcTemplate.update(sql, favorito.getIduser(), favorito.getIdanime());
    }

    public int eliminarFavorito(Favorito favorito) {
        String sql = "call delete_favorito(?, ?);";
        return jdbcTemplate.update(sql, favorito.getIduser(), favorito.getIdanime());
    }

}
