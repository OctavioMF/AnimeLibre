package com.DouDev.AnimeLibre.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DouDev.AnimeLibre.services.ScrappingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/animelibre")
public class AnimeController {
    
    @Autowired
    ScrappingService scrappingService;

    @Operation(summary = "Retorna una lista de animes basados en el nombre proporcionado",
                description = "Este endpoint permite buscar animes por nombre y devuelve una lista de animes que coinciden con la búsqueda.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de animes obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error al obtener la lista de animes")
    })
    @Parameter(name = "animeName", description = "Nombre del anime a buscar", required = true)
    @GetMapping("/animeList")
    public ResponseEntity<?> getAnimeList(@RequestParam String animeName) {
        try {
            return ResponseEntity.ok(scrappingService.getAnimeList(animeName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Retorna información detallada de un anime basado en su URI",
                description = "Este endpoint permite obtener información detallada de un anime específico utilizando su URI.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Información del anime obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error al obtener la información del anime")
    })
    @Parameter(name = "animeUri", description = "URI del anime del cual se desea obtener información", required = true)
    @GetMapping("/animeInfo")
    public ResponseEntity<?> getAnimeInfo(@RequestParam String animeUri) {
        try {
            return ResponseEntity.ok(scrappingService.getAnimeInfo(animeUri));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Retorna una lista de episodios de un anime basado en su URI",
                description = "Este endpoint permite obtener una lista de episodios de un anime específico utilizando su URI.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de episodios obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error al obtener la lista de episodios")
    })
    @Parameter(name = "animeUri", description = "URI del anime del cual se desea obtener la lista de episodios", required = true)
    @GetMapping("/episodeLinks")
    public ResponseEntity<?> getEpisodeLinks(@RequestParam String episodeUri) {
        try {
            return ResponseEntity.ok(scrappingService.getEpisodeLinks(episodeUri));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
