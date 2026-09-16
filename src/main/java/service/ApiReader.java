package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiReader {

    String apiKey = System.getenv("API_KEY");
    HttpClient httpClient = HttpClient.newHttpClient();

    public String getAllDataFromApi(int  page){
        try{
            String url = "https://api.themoviedb.org/3/discover/movie"
                    + "?api_key=" + apiKey
                    + "&with_origin_country=DK"
                    + "&primary_release_date.gte=2021-09-16"
                    + "&primary_release_date.lte=2026-09-16"
                    + "&page=" + page;


            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> httpResponse =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        }

    public String getMovieDetails(Long movieId) {

        String url = "https://api.themoviedb.org/3/movie/"
                + movieId
                + "?api_key=" + apiKey
                + "&append_to_response=credits";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            return response.body();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
}}
