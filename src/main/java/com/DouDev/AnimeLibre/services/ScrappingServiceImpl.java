package com.DouDev.AnimeLibre.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.DouDev.AnimeLibre.domains.Anime;
import com.DouDev.AnimeLibre.domains.Episode;

@Service
public class ScrappingServiceImpl implements ScrappingService{ 

    private final String BASE_URL = "https://m.animeflv.net";

    @Cacheable(value = "animeList", key = "#animeName")
    @Override
    public List<Anime> getAnimeList(String animeName) throws IOException {
        List<Anime> animeList = new ArrayList<>();
        Elements animeRaw = null;
        
        try{
            Document doc = Jsoup.connect(BASE_URL + "/browse?q="+animeName).get();
            animeRaw = doc.select("li.Anime");

            for (Element animeElement : animeRaw) {
                Anime anime = new Anime();

                anime.setTitle(animeElement.select("h2").text());
                anime.setImageUrl(BASE_URL + animeElement.select("img").attr("src"));
                anime.setAnimeUri(animeElement.select("a").attr("href"));
                anime.setType(animeElement.select("span.Type").text());

                animeList.add(anime);
            }

        } catch (Exception e) {
            Logger.getLogger(ScrappingServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return animeList;
    }

    @Cacheable(value = "animeInfo", key = "#animeUri")
    @Override
    public Anime getAnimeInfo(String animeUri) throws IOException {
        Anime anime = new Anime();

        try {
            Document doc = Jsoup.connect(BASE_URL + animeUri).get();

            anime.setTitle(doc.select("h1").text());
            anime.setStatus(doc.select("strong.Amn-Off, strong.Amn-On").text());
            anime.setDescription(doc.select("p > strong:contains(Sinopsis:)").next().text());
            anime.setImageUrl(BASE_URL + doc.select("img").attr("src"));
            anime.setType(doc.select("span.Type").text());
            
            Elements episodes = doc.select("li.Episode");
            for (Element episodeElement : episodes) {
                Episode episode = new Episode();

                String episodeId = episodeElement.select("a").attr("href");

                episode.setEpisodeUri(episodeId);

                anime.getEpisodesList().add(episode);
            }
            
        } catch (Exception e) {
            Logger.getLogger(ScrappingServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return anime;
    }

    @Cacheable(value = "episodeLinks", key = "#episodeUri")
    @Override
    public List<String> getEpisodeLinks(String episodeUri) throws IOException {
        List<String> links = new ArrayList<>();
        String url = BASE_URL + episodeUri;
        
        try {
            Document doc = Jsoup.connect(url).get();

            String html = doc.html();

            String pattern = "https:\\\\?/\\\\?/ok\\.ru\\\\?/videoembed\\\\?/.*?\""
                           + "|https:\\\\?/\\\\?/www\\.yourupload\\.com\\\\?/embed\\\\?/.*?\""
                           + "|https:\\\\?/\\\\?/streamwish\\.to\\\\?/e\\\\?/.*?\"";

            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(html);

            while (matcher.find()) {
                String raw = matcher.group();
                String cleaned = raw.replace("\\", "").replace("\"", "");
                links.add(cleaned);
            }


        } catch (Exception e) {
            Logger.getLogger(ScrappingServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return links;
    }
}
