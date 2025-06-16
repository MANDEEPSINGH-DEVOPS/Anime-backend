package abp.project.anime.controller;

import abp.project.anime.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideoController {
    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/anime/{id}/videos")
    public ResponseEntity getVideo(@PathVariable int id){
        return videoService.getAllVideosByIdAnime(id);
    }


}
