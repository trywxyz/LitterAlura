package br.com.alura.litter.alura.main;


import br.com.alura.litter.alura.model.Info;
import br.com.alura.litter.alura.model.Livro;
import br.com.alura.litter.alura.model.LivroDTO;
import br.com.alura.litter.alura.repository.LivroRepository;
import br.com.alura.litter.alura.service.DataConvert;
import br.com.alura.litter.alura.service.RequestAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private Scanner read = new Scanner(System.in);
    private RequestAPI requestApi = new RequestAPI();
    private DataConvert dataConvert = new DataConvert();
    private List<LivroDTO> livros = new ArrayList<>();
    private LivroRepository repository;
    private final String URL = "https://gutendex.com/books?search=";

    public Main(LivroRepository repository) {
        this.repository = repository;
    }


    public void menu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    1. Buscar Livro por título
                    2. Listar livros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos em determinado ano
                    5. Listar livros por idiomas
                    0. Sair
                    
                    """;


            System.out.println(menu);
            opcao = read.nextInt();
            read.nextLine();


            switch (opcao) {
                case 1:
                    System.out.println("Digite o título do livro:");
                    searcBookByTitle(read.nextLine());
                    break;
                case 2:
                    listBooks();
                    break;
                case 3:
                    listAuthors();
                    break;
                case 4:
                    listAuthorsLifed();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Não encontrei essa opção! Tente novamente");

            }

        }

    }


    private Info getBooksFromAPI(String title) {
        var json = requestApi.getData(URL + title.replace(" ", "+"));
//        System.out.println("Json Recebido: " + json);// " "
        return dataConvert.getData(json, Info.class);

    }


    private Optional<Livro> getBookData(Info info, String title) {
        return info.results().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .map(b -> new Livro(
                        b.getTitle(),
                        b.getAuthor(),
                        b.getDownloads()
                )).findFirst();
    }

    private Optional<Livro> searcBookByTitle(String title) {
        Info infobook = getBooksFromAPI(title);
        Optional<Livro> book = getBookData(infobook, title);

        if (book.isPresent()) {
            var result = repository.save(book.get());
            System.out.println("Seu Livro foi salvo com sucesso!");
            System.out.println("Seu Livro: " + result.getTitle());
        } else {
            System.out.println("Livro não encontrado");
        }
        return book;
    }

    private void listBooks() {
        var books = repository.findAll();

        if (books.isEmpty()) {
            System.out.println("Lista de livros vazia!");
        } else {

            books.forEach(System.out::println);
        }

    }

    private void listAuthors() {
        var books = repository.findAll();

        if (books.isEmpty()) {
            System.out.println("Lista sem autores!");
        } else {
            books.stream()
                    .map(Livro::getAuthor)
                    .distinct()
                    .forEach(System.out::println);
        }
    }

    private void listAuthorsLifed() {
        System.out.println("Digite o ano:");
        int year = read.nextInt();
        read.nextLine();


        var books = repository.findAll();
        if (books.isEmpty()) {
            System.out.println("Lista sem autores vivos!");
        } else {
            books = repository.findAll();
            books.stream()
                    .filter(b -> b.getAuthor().contains("(" + year))
                    .map(Livro::getAuthor)
                    .distinct()
                    .forEach(System.out::println);
        }
    }


    private void listBooksByLanguage() {
        var books = repository.findAll();
        books.stream()
                .map(Livro::getLanguage)
                .distinct()
                .forEach(System.out::println);
    }
}

// TESTE TESTE
//        System.out.println("Qual a linguagem que você deseja?");
//        var linguagem = read.nextLine();
//
//        var books = repository.findByLanguage(linguagem);
//
//        if(books.isEmpty()){
//            System.out.println("Lista vazia!");
//        }else{
//            books.forEach(System.out::println);
//        }

