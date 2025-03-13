package com.varsh.streamix.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.varsh.streamix.model.Genre;
import com.varsh.streamix.model.StreamType;
import com.varsh.streamix.model.VideoStream;
import com.varsh.streamix.repository.VideoStreamRepository;

@Service
public class VideoStreamService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private VideoStreamRepository videoStreamRepository;

    public List<VideoStream> searchVideo(String keyword) {
        return videoStreamRepository.searchByKeyword(keyword);
    }

    public List<VideoStream> getAllVideoStreams() {
        return videoStreamRepository.findAll();
    }

    public List<VideoStream> getAllTvShows() {
        return videoStreamRepository.findByType(StreamType.SERIES);
    }

    public List<VideoStream> getAllMovies() {
        return videoStreamRepository.findByType(StreamType.MOVIE);
    }

    private String bucketName = "streamixtvshowsmovies";
    private String region = "us-east-1";

    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }

    public String uploadThumbnail(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = "thumbnails/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            fileObj.delete();
            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
        } catch (Exception e) {
            return "File upload failed: " + e.getMessage();
        }
    }

    public VideoStream saveVideoStream(VideoStream videoStream) {
        return videoStreamRepository.save(videoStream);
    }

//    public Map<Genre, List<VideoStream>> getMoviesGroupedByGenre() {
//        List<VideoStream> allMovies = videoStreamRepository.findAll();
//        return allMovies.stream().collect(Collectors.groupingBy(VideoStream::getGenre));
//    }
    
//    public Map<Genre, List<VideoStream>> getMoviesGroupedByGenre() {
//        List<VideoStream> allMovies = videoStreamRepository.findAll();
//        List<VideoStream> movies = allMovies.stream()
//            .filter(video -> video.getType() == StreamType.MOVIE)
//            .collect(Collectors.toList());
//        return movies.stream().collect(Collectors.groupingBy(VideoStream::getGenre));
//    }

    public Map<Genre, List<VideoStream>> getMoviesGroupedByGenre() {
        List<VideoStream> allMovies = videoStreamRepository.findAll();
        List<VideoStream> movies = new ArrayList<>();
        for (VideoStream video : allMovies) {
            if (video.getType() == StreamType.MOVIE) {
                movies.add(video);
            }
        }
        Map<Genre, List<VideoStream>> moviesByGenre = new HashMap<>();
        for (VideoStream movie : movies) {
            Genre genre = movie.getGenre();
            if (!moviesByGenre.containsKey(genre)) {
                moviesByGenre.put(genre, new ArrayList<>());
            }
            moviesByGenre.get(genre).add(movie);
        }
        return moviesByGenre;
    }
    
    public boolean deleteVideo(Long id) {
        Optional<VideoStream> videoStreamOptional = videoStreamRepository.findById(id);
        if (videoStreamOptional.isPresent()) {
            VideoStream videoStream = videoStreamOptional.get();
            if (videoStream.getVideoLink() != null) {
                String videoKey = extractS3Key(videoStream.getVideoLink());
                if (videoKey != null && !videoKey.isEmpty()) {
                    amazonS3.deleteObject(bucketName, videoKey);
                }
            }
            if (videoStream.getThumbnailUrl() != null) {
                String thumbnailKey = extractS3Key(videoStream.getThumbnailUrl());
                if (thumbnailKey != null && !thumbnailKey.isEmpty()) {
                    amazonS3.deleteObject(bucketName, thumbnailKey);
                }
            }
            videoStreamRepository.delete(videoStream);
            return true;
        }
        return false;
    }

    private String extractS3Key(String fileUrl) {
        if (fileUrl == null || !fileUrl.contains("/")) {
            return "";
        }
//        System.out.println(fileUrl.substring(fileUrl.lastIndexOf("/") + 1));
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }

    public VideoStream getVideoById(Long id) {
        return videoStreamRepository.findById(id).orElse(null);
    }

    public void updateVideo(VideoStream stream) {
        videoStreamRepository.save(stream);
    }

}
