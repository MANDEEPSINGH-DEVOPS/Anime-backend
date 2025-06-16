package abp.project.anime.dao;

import abp.project.anime.model.Anime;
import abp.project.anime.model.Favorito;
import abp.project.anime.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VideoRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Video> getAllVideosByIdAnime(int id) {
        String sql = "call get_videos_id_anime(?);";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Video.class), id);
    }

}
