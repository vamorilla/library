
package com.library.Library.entities;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.OneToOne;



@Entity
public class Book{

   @Id
   @Column(unique = true)
   private Long isBn;
   @Column(name = "title", unique = true)
   private String title;
   private Integer year;
   private Integer copies;
   private Integer copiesOnLoan;
   private Integer remainingCopies;
   private Boolean high;
   @OneToOne
   private Autor author;
   @OneToOne
   private Editorial e;

    public Book() {
    }

    public Book(String title, Integer year, Integer copies, Integer copiesOnLoan, Integer remainingCopies, Boolean high, Autor author, Editorial e) {
        this.title = title;
        this.year = year;
        this.copies = copies;
        this.copiesOnLoan = copiesOnLoan;
        this.remainingCopies = remainingCopies;
        this.high = high;
        this.author = author;
        this.e = e;
    }

    public Long getIsBn() {
        return isBn;
    }

    public void setIsBn(Long isBn) {
        this.isBn = isBn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getCopiesOnLoan() {
        return copiesOnLoan;
    }

    public void setCopiesOnLoan(Integer copiesOnLoan) {
        this.copiesOnLoan = copiesOnLoan;
    }

    public Integer getRemainingCopies() {
        return remainingCopies;
    }

    public void setRemainingCopies(Integer remainingCopies) {
        this.remainingCopies = remainingCopies;
    }

    public Boolean getHigh() {
        return high;
    }

    public void setHigh(Boolean high) {
        this.high = high;
    }

    public Autor getAuthor() {
        return author;
    }

    public void setAuthor(Autor author) {
        this.author = author;
    }

    public Editorial getE() {
        return e;
    }

    public void setE(Editorial e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "Libro{" + "isBn=" + isBn + ", title=" + title + ", year=" + year + ", copies=" + copies + ", copiesOnLoan=" + copiesOnLoan + ", remainingCopies=" + remainingCopies + ", high=" + high + ", author=" + author + ", e=" + e + '}';
    }
   
   
}
