
package com.library.Library.service;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Book;
import com.library.Library.entities.Customer;
import com.library.Library.entities.Loan;
import com.library.Library.repository.BookRepository;
import com.library.Library.repository.CustomerRepository;
import com.library.Library.repository.LoanRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {
    
    @Autowired
    private LoanRepository lRepo;
    @Autowired
    private BookRepository bRepo;
    @Autowired
    private CustomerRepository custRepo;
    
    public void addLoan(Date returnDate, Long isBnBook, Integer idCustomer) throws ExceptionService{
        
        Loan loan = new Loan();
        Book book = bRepo.findById(isBnBook).get();
        loan.setLoanDate(new Date());
        if(returnDate.before(loan.getLoanDate())){
            throw new ExceptionService("La fecha de devolucion es incorrecta");
        }else{
            loan.setReturnDate(returnDate);
        }
        
        if(bRepo.findById(isBnBook).get().getRemainingCopies() < 1){
            throw new ExceptionService("No hay copias disponibles");
        }
        book.setRemainingCopies(book.getRemainingCopies()-1);
        book.setCopiesOnLoan(book.getCopiesOnLoan()+1);
        bRepo.save(book);
        loan.setBook(book);
        loan.setCustomer(custRepo.findById(idCustomer).get());
        lRepo.save(loan);
    }

    @Transactional
    public void modifyLoan(Integer id, Date returnDate, Long isBnBook, Integer idCustomer) throws ExceptionService{
        Optional<Loan> response = lRepo.findById(id);
        if(response.isPresent()){
            Loan loan = response.get();
            if(returnDate.before(loan.getLoanDate())){
               throw new ExceptionService("La fecha de devolucion es incorrecta");
            }else{
              loan.setReturnDate(returnDate);
            }
        
        if(bRepo.findById(isBnBook).get().getRemainingCopies() < 1){
            throw new ExceptionService("No hay copias disponibles");
        }
            
            loan.setBook(bRepo.findById(isBnBook).get());
            loan.setCustomer(custRepo.findById(idCustomer).get());
        
            lRepo.save(loan);
        }else{
            throw new ExceptionService("No se encontro el prestamo");
        }
    }
    
    @Transactional
    public void delete(Integer id) throws ExceptionService{
        Optional<Loan> response = lRepo.findById(id);
        if(response.isPresent()){
            Loan loan = response.get();
            Book book = loan.getBook();
            book.setCopiesOnLoan(book.getCopiesOnLoan()-1);
            book.setRemainingCopies(book.getRemainingCopies()+1);
            bRepo.save(book);
            lRepo.delete(loan);
        }else{
            throw new ExceptionService("No se encontro el prestamo");
        }
    }
    
    public List<Loan> loanList(){
        return lRepo.findAll();
    }
    
    public Optional<Loan> findById(Integer id) {
       return lRepo.findById(id);
    }
    
    public long remainingDays(Integer id) throws ExceptionService{
        Optional<Loan> response = lRepo.findById(id);
        
        if(response.isPresent()){
          Loan loan = response.get();
          int currentYear = loan.getReturnDate().getYear();
          int currentMonth = loan.getReturnDate().getMonth();
          int currentDay = loan.getReturnDate().getDay();
        
          LocalDate date1 = LocalDate.of(currentYear, currentMonth, currentDay);
        
          return ChronoUnit.DAYS.between(date1, LocalDate.now());
        }else{
            throw new ExceptionService("No se encontro el prestamo");
        }
    }
    
    @Transactional
    public void unsubscribeCustomer(Integer id ,Integer idCustomer) throws ExceptionService{
        Optional<Loan> response = lRepo.findById(id);
        if(response.isPresent() && remainingDays(id) < 0){
            Customer customer = custRepo.findById(idCustomer).get();
            customer.setIsActive(false);
        }else{
            throw new ExceptionService("No se encontro el prestamo o el cliente");
        }
    }
}
