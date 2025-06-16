package abp.project.anime.dao;

import abp.project.anime.model.Anime;
import abp.project.anime.model.Favorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnimeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AnimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Anime> get_all_animes_ordered_by_favorites(int iduser){
        String sql = "call get_all_animes_ordered_by_favorites(?)";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Anime.class), iduser);
    }

    public List<Anime> get_favorites(int iduser, int idanime){
        String sql = "call get_favorites(?, ?)";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Anime.class), iduser, idanime);
    }
}
