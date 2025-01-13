package br.com.alura.litter.alura.model;

public record AuthorDTO(
        String name,
        int birthYear,
        int deathYear
) {
}
