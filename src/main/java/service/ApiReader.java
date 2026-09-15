package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiReader {

    String apiKey = System.getenv("API_KEY");

    public String getAllDataFromApi(){
        try{
            String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;

            HttpClient httpClient = HttpClient.newHttpClient();
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
}
