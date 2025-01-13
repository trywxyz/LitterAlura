package br.com.alura.litter.alura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestAPI {

    public String getData(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = null;

        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e){
            throw new RuntimeException(e);
        } catch (NullPointerException | InterruptedException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
        String json = response.body();
        return json;
    }

}
