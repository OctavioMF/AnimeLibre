package com.DouDev.AnimeLibre.services;

import java.util.List;

import com.DouDev.AnimeLibre.domains.Anime;

public interface ScrappingService {

    List<Anime> getAnimeList(String animeName) throws Exception;
    Anime getAnimeInfo(String animeUri) throws Exception;
    List<String> getEpisodeLinks(String episodeUri) throws Exception;
}
