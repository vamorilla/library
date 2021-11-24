
package com.library.Library.service;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Autor;
import com.library.Library.repository.AuthorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorServices {
    
    @Autowired
    private AuthorRepository auRepo;
    
    public void addAuthor(String name) throws ExceptionService{
        
        Autor au = new Autor();
        
        au.setHigh(true);
        
        if(name == null || name.isEmpty()){
            throw new ExceptionService("El campo nombre no debe estar vacio");
        }
        
        au.setName(name);
        auRepo.save(au);
    }
    
    public List<Autor> AuthorList(){
        
        return auRepo.findAll();
    }
    
    @Transactional
    public void modifyAuthor(Integer id, String name) throws ExceptionService {
        if (name == null || name.isEmpty()) {
            throw new ExceptionService("El campo nombre no debe estar vacio");
        }
        Optional<Autor> response = auRepo.findById(id);
        if (response.isPresent()) {
            Autor author = response.get();

            author.setName(name);
            auRepo.save(author);
        }else{
            throw new ExceptionService("No se encontro el autor");
        }
    }
    @Transactional
    public void toRegister(Integer id) throws ExceptionService{
       Optional<Autor> response = auRepo.findById(id);
        if (response.isPresent()) {
            Autor author = response.get();

            author.setHigh(true);
            auRepo.save(author);
        }else{
            throw new ExceptionService("No se encontro el autor");
        } 
    }
    @Transactional
    public void unsubscribe(Integer id) throws ExceptionService{
       Optional<Autor> response = auRepo.findById(id);
        if (response.isPresent()) {
            Autor author = response.get();

            author.setHigh(false);
            auRepo.save(author);
        }else{
            throw new ExceptionService("No se encontro el autor");
        } 
    }
    @Transactional
    public void deleteAuthor(Integer id) throws ExceptionService{
        Optional<Autor> response = auRepo.findById(id);
        if(response.isPresent()){
            Autor author = response.get();
            auRepo.delete(author);
        }else{
            throw new ExceptionService("No se encontro el autor a eliminar");
        }
    }

    public Optional<Autor> findById(Integer id) {
       return auRepo.findById(id);
    }
}
