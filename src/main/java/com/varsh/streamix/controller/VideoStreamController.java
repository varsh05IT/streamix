package com.varsh.streamix.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.varsh.streamix.model.Episode;
import com.varsh.streamix.model.Genre;
import com.varsh.streamix.model.Role;
import com.varsh.streamix.model.Season;
import com.varsh.streamix.model.StreamType;
import com.varsh.streamix.model.UserDetails;
import com.varsh.streamix.model.VideoStream;
import com.varsh.streamix.repository.EpisodeRepository;
import com.varsh.streamix.repository.SeasonRepository;
import com.varsh.streamix.repository.VideoStreamRepository;
import com.varsh.streamix.service.EpisodeService;
import com.varsh.streamix.service.VideoStreamService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/stream")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VideoStreamController {

    @Autowired
    private VideoStreamService videoStreamService;

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    EpisodeRepository episodeRepository;

    @Autowired
    VideoStreamRepository videoStreamRepository;
    
    @GetMapping("/")
    public String indexPage() {
        return "index";
    }
    
    @GetMapping("/unauthorized")
    public String unauthorised() {
        return "unauthorized";
    }

    @GetMapping("/avengers-url")
    @ResponseBody
    public String getAvengersUrl() {
        Long id = 44L;
        VideoStream avengers = videoStreamRepository.findById(id).orElse(null);
        return (avengers != null) ? avengers.getVideoLink() : "";
    }
    
    @GetMapping("/home")
    public String home(Model model) {
        List<VideoStream> allMovies = videoStreamRepository.findByType(StreamType.MOVIE);
        List<VideoStream> trendingMovies = new ArrayList<VideoStream>();
        for (int i = 0; i < allMovies.size() && i < 5; i++) {
            trendingMovies.add(allMovies.get(i));
        }
        
        model.addAttribute("videoStreams", trendingMovies);
        return "home";
    }

    @GetMapping("/movies")
    public String moviesPage(Model model) {
        Map<Genre, List<VideoStream>> moviesByGenre = videoStreamService.getMoviesGroupedByGenre();
        model.addAttribute("moviesByGenre", moviesByGenre);
        return "movies";
    }

    @GetMapping("/watch/{id}")
    public String watchMovie(@PathVariable Long id, Model model) {
        VideoStream video = videoStreamService.getVideoById(id);
        if (video != null) {
            model.addAttribute("video", video);
            return "watch";
        }
        return "redirect:/stream/home";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile file, @RequestParam MultipartFile thumbnail,
            @RequestParam String title, @RequestParam String description, @RequestParam String directorName,
            @RequestParam Long duration, @RequestParam int IMDBRating, @RequestParam StreamType type,
            @RequestParam Genre genre, HttpSession session, Model model) {

//        UserDetails user = (UserDetails) session.getAttribute("user");
//        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
//            model.addAttribute("message", "Only admins can upload videos.");
//            return "home";
//        }
        if(session.getAttribute("role") == Role.ADMIN) {
            return "redirect:/stream/unauthorized";
        }
        
        String videoUrl = videoStreamService.uploadFile(file);
        if (videoUrl.startsWith("File upload failed")) {
            model.addAttribute("message", videoUrl);
            return "redirect:/admin/manageStreams";
        }

        String thumbnailUrl = videoStreamService.uploadThumbnail(thumbnail);
        if (thumbnailUrl.startsWith("File upload failed")) {
            model.addAttribute("message", thumbnailUrl);
            return "redirect:/admin/manageStreams";
        }
        VideoStream videoStream = new VideoStream();
        videoStream.setTitle(title);
        videoStream.setDescription(description);
        videoStream.setDirectorName(directorName);
        videoStream.setDuration(duration);
        videoStream.setIMDBRating(IMDBRating);
        videoStream.setVideoLink(videoUrl);
        videoStream.setThumbnailUrl(thumbnailUrl);
        videoStream.setType(type);
        videoStream.setGenre(genre);
        videoStreamService.saveVideoStream(videoStream);

        model.addAttribute("message", "File uploaded successfully: " + videoUrl);
        return "redirect:/admin/manageStreams";
    }

    @PostMapping("/upload-series")
    public String uploadSeries(@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("directorName") String directorName,
            @RequestParam("IMDBRating") int IMDBRating,
            @RequestParam("genre") Genre genre,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam("seasonNumber") int seasonNumber,
            @RequestParam("seasonDescription") String seasonDescription,
            @RequestParam("seasonThumbnail") MultipartFile seasonThumbnail,
            @RequestParam("episodeTitles") List<String> episodeTitles,
            @RequestParam("episodeNumbers") List<Integer> episodeNumbers,
            @RequestParam("episodeDescriptions") List<String> episodeDescriptions,
            @RequestParam("episodeDurations") List<Long> episodeDurations,
            @RequestParam("episodeIMDBRatings") List<Integer> episodeIMDBRatings,
            @RequestParam("episodeFiles") MultipartFile[] episodeFiles) {
        try {
            String thumbnailUrl = videoStreamService.uploadFile(thumbnail);
            VideoStream videoStream = new VideoStream();
            videoStream.setTitle(title);
            videoStream.setDescription(description);
            videoStream.setDirectorName(directorName);
            videoStream.setIMDBRating(IMDBRating);
            videoStream.setGenre(genre);
            videoStream.setType(StreamType.SERIES);
            videoStream.setThumbnailUrl(thumbnailUrl);

            String seasonThumbnailUrl = videoStreamService.uploadFile(seasonThumbnail);
            Season season = new Season();
            season.setSeasonNumber(seasonNumber);
            season.setDescription(seasonDescription);
            season.setThumbnailUrl(seasonThumbnailUrl);
            season.setVideoStream(videoStream);
            List<Episode> episodes = new ArrayList<>();
            
            for (int i = 0; i < episodeFiles.length; i++) {
                MultipartFile episodeFile = episodeFiles[i];
                String episodeVideoUrl = videoStreamService.uploadFile(episodeFile);
                Episode episode = new Episode();
                episode.setTitle(episodeTitles.get(i));
                episode.setEpisodeNumber(episodeNumbers.get(i));
                episode.setDescription(episodeDescriptions.get(i));
                episode.setDuration(episodeDurations.get(i));
                episode.setIMDBRating(episodeIMDBRatings.get(i));
                episode.setVideoLink(episodeVideoUrl);
                episode.setSeason(season);
                episodes.add(episode);
            }

            season.setEpisodeList(episodes);
            videoStream.setSeasonList(Collections.singletonList(season));
            videoStreamRepository.save(videoStream);
            return "redirect:/admin/manageStreams"; 
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/manageStreams?error=true"; 

        }

    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteVideo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = videoStreamService.deleteVideo(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Video deleted successfully.");
        } else {

            redirectAttributes.addFlashAttribute("message", "Video not found.");
        }
        return "redirect:/stream/home";
    }

    @GetMapping("/series/{id}/seasons")
    public String getSeriesSeasons(@PathVariable Long id, Model model) {
        VideoStream series = videoStreamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Series not found"));
        model.addAttribute("series", series);
        return "seasons";
    }


    @GetMapping("/watch/episode/{episodeId}")
    public String watchEpisode(@PathVariable Long episodeId, Model model) {
        Episode episode = episodeRepository.findById(episodeId)
            .orElseThrow(() -> new RuntimeException("Episode not found"));
        model.addAttribute("video", episode);
        return "watch";
    }
    
    
    
    
    
    
    
    
    
    
    @GetMapping("/tvshows")
    public String getTVShows(Model model) {
        List<VideoStream> series = videoStreamRepository.findByType(StreamType.SERIES);
        Map<Genre, List<VideoStream>> seriesByGenre = series.stream()
            .collect(Collectors.groupingBy(VideoStream::getGenre));
        model.addAttribute("seriesByGenre", seriesByGenre);
        return "tvshows";
    }

}
