
package com.library.Library.service;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Editorial;
import com.library.Library.repository.EditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {
    @Autowired
    private EditorialRepository edRepo;
    
    public void addEditorial(String name) throws ExceptionService{
        if(name == null || name.isEmpty()){
            throw new ExceptionService("El campo nombre no debe estar vacio");
        }
        
        Editorial editorial = new Editorial();
        editorial.setHigh(true);
        editorial.setName(name);
        edRepo.save(editorial);
    }
    
     public List<Editorial> EdList(){
        
        return edRepo.findAll();
    }
     
    @Transactional
    public void modifyEditorial(Integer id, String name) throws ExceptionService {
        if (name == null || name.isEmpty()) {
            throw new ExceptionService("El campo nombre no debe estar vacio");
        }
        Optional<Editorial> response = edRepo.findById(id);
        if (response.isPresent()) {
            Editorial editorial = response.get();

            editorial.setName(name);
            edRepo.save(editorial);
        }else{
            throw new ExceptionService("No se encontro la editorial");
        }
    }
    
    @Transactional
    public void toRegister(Integer id) throws ExceptionService{
       Optional<Editorial> response = edRepo.findById(id);
        if (response.isPresent()) {
            Editorial editorial = response.get();

            editorial.setHigh(true);
            edRepo.save(editorial);
        }else{
            throw new ExceptionService("No se encontro la editorial");
        } 
    }
    
    @Transactional
    public void unsubscribe(Integer id) throws ExceptionService{
       Optional<Editorial> response = edRepo.findById(id);
        if (response.isPresent()) {
            Editorial editorial = response.get();

            editorial.setHigh(false);
            edRepo.save(editorial);
        }else{
            throw new ExceptionService("No se encontro la editorial");
        } 
    }
    
    @Transactional
    public void deleteEditorial(Integer id) throws ExceptionService{
        Optional<Editorial> response = edRepo.findById(id);
        if(response.isPresent()){
            Editorial editorial = response.get();
            edRepo.delete(editorial);
        }else{
            throw new ExceptionService("No se encontro la editorial a eliminar");
        }
    }
    
    public Optional<Editorial> findById(Integer id) {
       return edRepo.findById(id);
    }
}
