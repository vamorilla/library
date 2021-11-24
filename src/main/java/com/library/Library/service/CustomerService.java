package com.library.Library.service;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Customer;
import com.library.Library.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository custRepo;

    public void addCustomer(Long dni, String name, String lastName, String phoneNumber) throws ExceptionService {
        validate(dni, name, lastName, phoneNumber);

        Customer customer = new Customer();
        customer.setDni(dni);
        customer.setName(name);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phoneNumber);
        customer.setIsActive(true);
        custRepo.save(customer);
    }
    
    @Transactional
    public void toRegister(Integer id) throws ExceptionService{
       Optional<Customer> response = custRepo.findById(id);
        if (response.isPresent()) {
            Customer customer = response.get();

            customer.setIsActive(true);
            custRepo.save(customer);
        }else{
            throw new ExceptionService("No se encontró el Cliente");
        } 
    }
    
    @Transactional
    public void unsubscribe(Integer id) throws ExceptionService{
       Optional<Customer> response = custRepo.findById(id);
        if (response.isPresent()) {
            Customer customer = response.get();

            customer.setIsActive(false);
            custRepo.save(customer);
        }else{
            throw new ExceptionService("No se encontró el Cliente");
        } 
    }
    
    @Transactional
    public void modifyCustomer(Integer id, String name, String lastName, String phoneNumber) throws ExceptionService {
         if (name == null || name.isEmpty()) {
            throw new ExceptionService("El campo nombre no debe estar vacio");
        }

        if (lastName == null || lastName.isEmpty()) {
            throw new ExceptionService("El campo apellido no debe estar vacio");
        }

        if (phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.length() < 10) {
            throw new ExceptionService("El campo telefono no debe estar vacio o no debe contener menos de 10 digitos");
        }
        Optional<Customer> response = custRepo.findById(id);
        if (response.isPresent()) {
            Customer customer = response.get();

            customer.setName(name);
            customer.setLastName(lastName);
            customer.setPhoneNumber(phoneNumber);

            custRepo.save(customer);
        }else{
            throw new ExceptionService("No se encontro el cliente");
        }

    }
    
    private void validate(Long dni, String name, String lastName, String phoneNumber) throws ExceptionService {
        if (name == null || name.isEmpty()) {
            throw new ExceptionService("El campo nombre no debe estar vacio");
        }

        if (lastName == null || lastName.isEmpty()) {
            throw new ExceptionService("El campo apellido no debe estar vacio");
        }

        if (phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.length() < 10) {
            throw new ExceptionService("El campo telefono no debe estar vacio o no debe contener menos de 10 digitos");
        }

        String dniString = Long.toString(dni);
        if (dni == null || dniString.length() < 8) {
            throw new ExceptionService("El campo dni no debe estar vacio o no debe contener menos de 8 digitos");
        }
    }
    
    @Transactional
    public void deleteCustomer(Integer id) throws ExceptionService{
        Optional<Customer> response = custRepo.findById(id);
        if(response.isPresent()){
            Customer customer = response.get();
            custRepo.delete(customer);
        }else{
            throw new ExceptionService("No se encontro el cliente a eliminar");
        }
    }
    
    public List<Customer> customerList(){
        return custRepo.findAll();
    }
    
    public Optional<Customer> findById(Integer id) {
       return custRepo.findById(id);
    }

}
