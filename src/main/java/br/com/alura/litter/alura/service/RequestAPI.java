package br.com.alura.litter.alura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestAPI {

    public String getData(String url) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            // Envia a requisição e obtém a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica o código de status da resposta
            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro ao consumir API: status " + response.statusCode());
            }

            // Obtém o corpo da resposta
            String json = response.body();

            // Verifica se o corpo é nulo ou vazio
            if (json == null || json.trim().isEmpty()) {
                throw new RuntimeException("Resposta da API está vazia.");
            }

            System.out.println("Resposta da API: " + json);
            return json;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao enviar requisição: " + e.getMessage(), e);
        }
    }
}
