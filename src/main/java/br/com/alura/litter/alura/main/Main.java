package br.com.alura.litter.alura.main;


import br.com.alura.litter.alura.model.*;
import br.com.alura.litter.alura.repository.AuthorRepository;
import br.com.alura.litter.alura.repository.BookRepository;
import br.com.alura.litter.alura.service.DataConvert;
import br.com.alura.litter.alura.service.RequestAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Main {

    private final Scanner leitura = new Scanner(System.in);
    private final RequestAPI consumoApi = new RequestAPI();
    private final DataConvert conversor = new DataConvert();
    private final String endereco = "https://gutendex.com/books/?search=";

    @Autowired
    private BookRepository livroRepository;
    @Autowired
    private AuthorRepository autorRepository;

    public void menu() {
        int opcao;
        do {
            System.out.println("""
                    *** Liter Alura Challenge ***
                    1 - Buscar livros por título
                    2 - Buscar livros por autores
                    3 - Listar livros
                    4 - Listar autores
                    5 - Listar autores vivos em determinado ano
                    9 - Sair
                    """);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1 -> searchBookInApi();
                case 2 -> searchAuthors();
                case 3 -> listBooks();
                case 4 -> listAuthors();
                case 5 -> searchForAuthorsLifed();
                case 9 -> System.out.println("Saindo da pesquisa!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 9);
    }

    private void searchBookInApi() {
        System.out.println("Qual o nome do livro para buscar?");
        String nomeLivro = leitura.nextLine();
        String json = consumoApi.obterDados(endereco + nomeLivro.replace(" ", "+"));

        ResultsDTO dadosResultado = conversor.obterDados(json, ResultsDTO.class);
        if (dadosResultado.livro().isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        dadosResultado.livro().forEach(dadosLivro -> {
            Book livro = new Book(dadosLivro);
            dadosLivro.authors().forEach(dadosAutor -> {
                Author autor = new Author(dadosAutor);
                autor.setBook(livro);
                livro.getAuthors().add(autor);
            });
            livroRepository.save(livro);
            System.out.println("Livro salvo: " + livro.getTitle());
        });
    }

    private void searchAuthors() {
        System.out.println("Digite o nome do autor desejado:");
        String nomeAutor = leitura.nextLine();
        List<Book> livrosEncontrados = livroRepository.findByAuthorNameContainingIgnoreCase(nomeAutor);

        if (livrosEncontrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o autor " + nomeAutor);
        } else {
            livrosEncontrados.forEach(livro -> {
                System.out.println("Título: " + livro.getTitle());
                System.out.println("Idioma: " + livro.getLanguage());
                System.out.println("Total de Downloads: " + livro.getDownloads());
                System.out.println("-----------");
            });
        }
    }

    private void listBooks() {
        List<Book> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado!");
        } else {
            livros.forEach(livro -> System.out.println("Título: " + livro.getTitle()));
        }
    }

    private void listAuthors() {
        List<Author> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado!");
        } else {
            autores.forEach(autor -> System.out.println("Autor: " + autor.getName() +
                    " | Nascimento: " + autor.getBirthYear() +
                    " | Falecimento: " + autor.getDeathYear()));
        }
    }

    private void searchForAuthorsLifed() {
        System.out.println("Digite o ano para busca:");
        int anoBuscado = leitura.nextInt();
        List<Author> autores = autorRepository.findAll();

        autores.stream()
                .filter(a -> a.getBirthYear() != null && a.getBirthYear() <= anoBuscado &&
                        (a.getDeathYear() == null || a.getDeathYear() >= anoBuscado))
                .forEach(autor -> System.out.println("Autor vivo: " + autor.getName() +  ",  Autor Data de Nascimento: " + autor.getBirthYear() + ", Autor Data de Falecimento: " +autor.getDeathYear()));
    }
}