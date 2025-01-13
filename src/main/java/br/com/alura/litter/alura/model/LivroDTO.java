package br.com.alura.litter.alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record LivroDTO(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<Author> authors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Double downloads
) {
}
