package com.DouDev.AnimeLibre.domains;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Anime {
    private String title;
    private String imageUrl;
    private String animeUri;
    private String type;
    private String description;
    private String status;
    private List<Episode> episodesList = new ArrayList<>();
}
