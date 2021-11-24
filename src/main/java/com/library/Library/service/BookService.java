package com.library.Library.service;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Book;
import com.library.Library.repository.AuthorRepository;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.EditorialRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepository bRepo;
    @Autowired
    private AuthorRepository auRepo;
    @Autowired
    private EditorialRepository edRepo;

    public void addBook(Long isBn, String title, Integer year, Integer copies, Integer copiesOnLoan, Integer idAuthor, Integer idEditorial) throws ExceptionService {
        validate(isBn, title, year, copies, copiesOnLoan);
        Book book = new Book();
        book.setIsBn(isBn);
        book.setTitle(title);
        book.setYear(year);
        book.setCopies(copies);
        book.setCopiesOnLoan(copiesOnLoan);
        book.setRemainingCopies(copies - copiesOnLoan);
        book.setHigh(true);
        book.setAuthor(auRepo.findById(idAuthor).get());
        book.setE(edRepo.findById(idEditorial).get());
        
        bRepo.save(book);
    }

    @Transactional
    public void modifyBook(Long isBn, String title, Integer year, Integer copies, Integer copiesOnLoan, Integer idAuthor, Integer idEditorial) throws ExceptionService {
        validate(isBn, title, year, copies, copiesOnLoan);
        Optional<Book> response = bRepo.findById(isBn);
        if (response.isPresent()) {
            Book book = response.get();
            book.setTitle(title);
            book.setYear(year);
            book.setCopies(copies);
            book.setCopiesOnLoan(copiesOnLoan);
            book.setRemainingCopies(copies - copiesOnLoan);
            book.setAuthor(auRepo.findById(idAuthor).get());
            book.setE(edRepo.findById(idEditorial).get());
            bRepo.save(book);
        }else{
            throw new ExceptionService("No se encontro el libro");
        }
    }
    
    @Transactional
    public void deleteBook(Long isBn) throws ExceptionService{
        Optional<Book> response = bRepo.findById(isBn);
        if(response.isPresent()){
            Book book = response.get();
            bRepo.delete(book);
        }else{
            throw new ExceptionService("No se encontro el libro");
        }
    }
    
    @Transactional
    public void toRegister(Long isBn) throws ExceptionService{
       Optional<Book> response = bRepo.findById(isBn);
        if (response.isPresent()) {
            Book book = response.get();

            book.setHigh(true);
            bRepo.save(book);
        }else{
            throw new ExceptionService("No se encontro el libro");
        } 
    }
    
    @Transactional
    public void unsubscribe(Long isBn) throws ExceptionService{
       Optional<Book> response = bRepo.findById(isBn);
        if (response.isPresent()) {
            Book book = response.get();

            book.setHigh(false);
            bRepo.save(book);
        }else{
            throw new ExceptionService("No se encontro el libro");
        } 
    }

    public void validate(Long isBn, String title, Integer year, Integer copies, Integer copiesOnLoan) throws ExceptionService {
        if(String.valueOf(isBn).isEmpty()){
             throw new ExceptionService("Ingresaste un isbn nulo o vacio");
        }
        if (title == null || title.isEmpty()) {
            throw new ExceptionService("Ingresaste un titulo nulo o vacio");
        }
        int currentYear = LocalDateTime.now().getYear();

        if (year > currentYear || year < 1800) {
            throw new ExceptionService("El año no puede ser mayor al actual  o menor al año 1800");
        }

        if (copies < 0) {
            throw new ExceptionService("El numero de ejemplares no puede ser  menor a 0");
        }

        if (copiesOnLoan < 0) {
            throw new ExceptionService("El numero de ejemplares prestados no puede ser  menor a 0");
        }
        if(copiesOnLoan > copies){
            throw new ExceptionService("Las copias prestadas no puede ser mayor a las copias disponibles");
        }

    }
    
    public List<Book> BookList(){
        
        return bRepo.findAll();
    }
    
    public Optional<Book> findById(Long isBn) {
       return bRepo.findById(isBn);
    }
}
