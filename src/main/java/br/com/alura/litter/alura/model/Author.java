package br.com.alura.litter.alura.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "authors")
@JsonIgnoreProperties(ignoreUnknown = true) // Mantido para ignorar campos não mapeados no JSON
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer birthYear;

    private Integer deathYear;

    @ManyToOne(fetch = FetchType.EAGER) // Uso de LAZY para evitar carregamento desnecessário
    private Book book;

    // Construtor padrão
    public Author() {}

    // Construtor parametrizado
    public Author(Book book, Integer deathYear, Integer birthYear, String name, Long id) {
        this.book = book;
        this.deathYear = deathYear;
        this.birthYear = birthYear;
        this.name = name;
        this.id = id;
    }

    // Construtor para conversão de DTO (corrigido)
    public Author(AuthorDTO dadosAutor) {
        this.name = dadosAutor.name();
        this.birthYear = dadosAutor.birthYear();
        this.deathYear = dadosAutor.deathYear();
    }

    @Override
    public String toString() {
        return "Author: Id=" + id + ", Name='" + name + '\'' +
                ", BirthYear=" + birthYear + ", DeathYear=" + deathYear +
                ", Book=" + (book != null ? book.getTitle() : "None");
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}