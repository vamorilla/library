
package com.library.Library.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date loanDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date returnDate;
    private Long remaningDays;
    @OneToOne
    private Book book;
    @OneToOne
    private Customer customer;

    public Loan() {
    }

    public Loan(Integer id, Date loanDate, Date returnDate, Long remaningDays, Book book, Customer customer) {
        this.id = id;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.remaningDays = remaningDays;
        this.book = book;
        this.customer = customer;
    }


    public Integer getId() {
        return id;
    }

    public Long getRemaningDays() {
        return remaningDays;
    }

    public void setRemaningDays(Long remaningDays) {
        this.remaningDays = remaningDays;
    }

    

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", loanDate=" + loanDate + ", returnDate=" + returnDate + ", book=" + book + ", customer=" + customer + '}';
    }
    
}
