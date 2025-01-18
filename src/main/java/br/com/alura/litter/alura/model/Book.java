package br.com.alura.litter.alura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Double downloads;

    @Enumerated(EnumType.STRING) // Para armazenar o enum como texto no banco
    private Languages language;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Uso de LAZY para performance
    @JsonIgnore // Evita problemas de serialização recursiva
    private List<Author> authors = new ArrayList<>();

    // Construtor padrão
    public Book() {}

    // Construtor para conversão de DTO (corrigido)
    public Book(BookDTO dadosLivro) {
        this.title = dadosLivro.title(); // Usa o método gerado pelo record
        this.downloads = Double.valueOf(dadosLivro.downloadCount()); // Conversão, se necessário
        // Supondo que 'languages' é uma lista e você precisa mapear para o enum 'Languages'
        this.language = dadosLivro.languages() != null && !dadosLivro.languages().isEmpty()
                ? Languages.valueOf(dadosLivro.languages().get(0).toUpperCase()) // Usa o primeiro idioma da lista
                : null;
    }

    // Construtor parametrizado
    public Book(String title, List<Author> authors, Double downloads, Languages language) {
        this.title = title;
        this.authors = authors;
        this.downloads = downloads;
        this.language = language;
    }

    @Override
    public String toString() {
        return "Book: id=" + id + ", title='" + title + '\'' +
                ", authors=" + (authors != null ? authors.size() : 0) +
                ", downloads=" + downloads + ", language=" + language;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }
}