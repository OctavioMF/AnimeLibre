package com.DouDev.AnimeLibre.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DouDev.AnimeLibre.services.ScrappingService;

@RestController
@RequestMapping("/animelibre")
public class AnimeController {
    
    @Autowired
    ScrappingService scrappingService;

    @GetMapping("/animeList")
    public ResponseEntity<?> getAnimeList(@RequestParam String animeName) {
        try {
            return ResponseEntity.ok(scrappingService.getAnimeList(animeName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/animeInfo")
    public ResponseEntity<?> getAnimeInfo(@RequestParam String animeName) {
        try {
            return ResponseEntity.ok(scrappingService.getAnimeInfo(animeName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/episodeLinks")
    public ResponseEntity<?> getEpisodeLinks(@RequestParam String episodeId) {
        try {
            return ResponseEntity.ok(scrappingService.getEpisodeLinks(episodeId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
