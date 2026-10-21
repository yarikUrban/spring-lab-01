package kz.iitu.springlab.catalog;

import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(String author) {
        List<Book> books = repository.findAll();
        if (author == null || author.isBlank()) {
            return books;
        }
        return books.stream()
                .filter(b -> b.author().toLowerCase().contains(author.toLowerCase()))
                .toList();
    }

    // Метод для варианта 11 (сортировка)
    public List<Book> findAllSorted(String author, String sort) {
        List<Book> books = findAll(author);
        if (sort == null) {
            return books;
        }
        Comparator<Book> comparator = switch (sort.toLowerCase()) {
            case "title" -> Comparator.comparing(Book::title, String.CASE_INSENSITIVE_ORDER);
            case "author" -> Comparator.comparing(Book::author, String.CASE_INSENSITIVE_ORDER);
            case "year" -> Comparator.comparingInt(Book::year);
            default -> throw new IllegalArgumentException("Invalid sort parameter: " + sort);
        };
        return books.stream().sorted(comparator).toList();
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    public Book create(Book book) {
        return repository.save(book);
    }

    public boolean update(long id, Book book) {
        if (repository.findById(id).isEmpty()) {
            return false;
        }
        Book updated = new Book(id, book.title(), book.author(), book.year());
        repository.save(updated);
        return true;
    }

    public boolean delete(long id) {
        return repository.deleteById(id);
    }
}