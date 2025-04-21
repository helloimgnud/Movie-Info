package com.recflix.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recflix.service.*;
import com.recflix.dto.*;

@RestController
public class GetInfoController {

    private static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";
    private static final String WIKIPEDIA_SUMMARY_URL = "https://en.wikipedia.org/api/rest_v1/page/summary/";

    private final DirectorService directorService;
    private final ActorService actorService;

    @Autowired
    public GetInfoController(DirectorService directorService, ActorService actorService) {
        this.directorService = directorService;
        this.actorService = actorService;
    }

    @GetMapping("/getInfo/{type}")
    public ResponseEntity<PersonInfo> getPersonInfo(@PathVariable("type") String type, @RequestParam Long id) {
        String name;

        if (type.equals("director")) {
            name = directorService.getDirectorById(id).getDirectorName();
        } else if (type.equals("actor")) {
            name = actorService.getActorById(id).getActorName();
        } else {
            return ResponseEntity.notFound().build();
        }

        try {
            String searchUrl = UriComponentsBuilder.fromHttpUrl(WIKIPEDIA_SEARCH_URL)
                    .queryParam("action", "query")
                    .queryParam("format", "json")
                    .queryParam("list", "search")
                    .queryParam("srsearch", name)
                    .build()
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> searchResponse = restTemplate.exchange(searchUrl, HttpMethod.GET, null, String.class);
            JsonNode searchRootNode = new ObjectMapper().readTree(searchResponse.getBody());
            JsonNode searchResults = searchRootNode.path("query").path("search");

            if (!searchResults.isArray() || !searchResults.elements().hasNext()) {
                return ResponseEntity.notFound().build();
            }

            String firstResultTitle = searchResults.elements().next().path("title").asText();

            String summaryUrl = UriComponentsBuilder.fromHttpUrl(WIKIPEDIA_SUMMARY_URL + firstResultTitle)
                    .build()
                    .toUriString();

            ResponseEntity<String> summaryResponse = restTemplate.exchange(summaryUrl, HttpMethod.GET, null, String.class);
            JsonNode summaryRootNode = new ObjectMapper().readTree(summaryResponse.getBody());

            String extract = summaryRootNode.path("extract").asText();
            String imageUrl = summaryRootNode.path("thumbnail").path("source").asText(null);

            PersonInfo personInfo = new PersonInfo(extract, imageUrl);
            return ResponseEntity.ok(personInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    public static class PersonInfo {
        private String biography;
        private String imageUrl;

        public PersonInfo(String biography, String imageUrl) {
            this.biography = biography;
            this.imageUrl = imageUrl;
        }

        public String getBiography() {
            return biography;
        }

        public void setBiography(String biography) {
            this.biography = biography;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
