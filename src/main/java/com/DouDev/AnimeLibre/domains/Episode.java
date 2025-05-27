package com.DouDev.AnimeLibre.domains;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Episode {
    private String episodeUri;
    private List<String> links = new ArrayList<>();
}
