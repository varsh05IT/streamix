package com.varsh.streamix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import com.varsh.streamix.model.Genre;
import com.varsh.streamix.model.StreamType;
import com.varsh.streamix.model.VideoStream;
import com.varsh.streamix.service.VideoStreamService;

@Controller
@RequestMapping("/stream")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VideoStreamController {
    
    @Autowired
    private VideoStreamService videoStreamService;
    
//    @Autowired
//    private S3Service s3Service;
    @GetMapping("/landing")
    public String showLandingPage() {
        return "landing"; // This will map to src/main/resources/templates/landing.html
    }
    
//    @GetMapping
//    public String homePage(Model model) {
//        model.addAttribute("message", "Welcome to the Home Page!");
//        return "landing"; 
//    }
    
    @GetMapping("/search")
    public ResponseEntity<List<VideoStream>> searchVideo(@RequestParam String keyword) {
        List<VideoStream> results = videoStreamService.searchVideo(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoStream> getVideoDetails(@PathVariable Long id) {
        VideoStream video = videoStreamService.getVideoDetails(id);
        return ResponseEntity.ok(video);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VideoStream>> getAllVideos() {
        List<VideoStream> videos = videoStreamService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    @PostMapping("/")
    public ResponseEntity<String> addNewVideo(@RequestBody VideoStream videoStream) {
        videoStreamService.addNewVideo(videoStream);
        return ResponseEntity.status(HttpStatus.CREATED).body("Video added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVideoDetails(@PathVariable Long id, @RequestBody VideoStream videoStream) {
        videoStreamService.updateVideoDetails(id, videoStream);
        return ResponseEntity.ok("Video updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable Long id) {
        videoStreamService.deleteVideo(id);
        return ResponseEntity.ok("Video deleted successfully");
    }
    
//    @GetMapping("/landing")
    public String showTop10Videos(Model model) {
        model.addAttribute("top5Movies", videoStreamService.getTop5Movies());
        model.addAttribute("top5Series", videoStreamService.getTop5Series());
        return "landing"; 
    }
    

//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file,
//                                              @RequestParam("title") String title,
//                                              @RequestParam("description") String description,
//                                              @RequestParam("directorName") String directorName,
//                                              @RequestParam("duration") Long duration,
//                                              @RequestParam("createdAt") Long createdAt,
//                                              @RequestParam("IMDBRating") int IMDBRating,
//                                              @RequestParam("type") String type,
//                                              @RequestParam("genre") String genre) {
//        String videoLink = s3Service.uploadFile(file);
//        VideoStream videoStream = new VideoStream();
//        videoStream.setTitle(title);
//        videoStream.setDescription(description);
//        videoStream.setDirectorName(directorName);
//        videoStream.setDuration(duration);
//        videoStream.setCreatedAt(createdAt);
//        videoStream.setIMDBRating(IMDBRating);
//        videoStream.setVideoLink(videoLink);
//        videoStream.setType(StreamType.valueOf(type));
//        videoStream.setGenre(Genre.valueOf(genre));
//        videoStreamService.addNewVideo(videoStream);
//        return ResponseEntity.status(HttpStatus.CREATED).body("Video uploaded and added successfully");
//    }
}
