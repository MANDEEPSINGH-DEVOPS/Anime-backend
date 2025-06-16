package abp.project.anime.service;

import abp.project.anime.dao.VideoRepository;
import abp.project.anime.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public ResponseEntity getAllVideosByIdAnime(int id){
        try{
            return ResponseEntity.ok(videoRepository.getAllVideosByIdAnime(id));
        }catch (DataAccessException e){
            String errorMessage = "Error al acceder a la base de datos: "+e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
