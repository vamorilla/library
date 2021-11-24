
package com.library.Library.controllers;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Customer;
import com.library.Library.service.CustomerService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/cliente")
public class CustomerController {
    
    @Autowired
    private CustomerService custServ;
    
    @GetMapping("")
    public ModelAndView showCustomers(HttpServletRequest request){
       ModelAndView mav = new ModelAndView("cliente");
       List<Customer> customerList = custServ.customerList();
       
       Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
       if(flashMap != null){
           mav.addObject("exito", flashMap.get("exito"));
           mav.addObject("error", flashMap.get("error"));
       }
       mav.addObject("clientes", customerList);
       return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView createCustomer(){
       ModelAndView mav = new ModelAndView("formularioCliente");
       mav.addObject("cliente", new Customer());
       mav.addObject("title", "Crear Cliente");
       mav.addObject("action", "guardar");
       
       return mav;
    }
    
    @GetMapping("/modificar/{id}")
    public ModelAndView modifyCustomer(@PathVariable Integer id){
       ModelAndView mav = new ModelAndView("formularioCliente");
       mav.addObject("cliente", custServ.findById(id));
       mav.addObject("title", "Modificar Cliente");
       mav.addObject("action", "editar");
       
       return mav;
    }
    
    @GetMapping("/alta/{id}")
    public RedirectView toRegister(@PathVariable Integer id, RedirectAttributes attr){
       try{
           custServ.toRegister(id);
           attr.addFlashAttribute("exito", "Se dio de alta correctamente el cliente.");
       }catch(Exception e){
           attr.addFlashAttribute("error", e.getMessage());
       }
        return new RedirectView("/cliente");
    }
   
    @GetMapping("/baja/{id}")
    public RedirectView unsubscribe(@PathVariable Integer id, RedirectAttributes attr){
       try{
           custServ.unsubscribe(id);
           attr.addFlashAttribute("exito", "Se dio de baja correctamente el cliente.");
       }catch(Exception e){
           attr.addFlashAttribute("error", e.getMessage());
       }
        return new RedirectView("/cliente");
    }
    
    @GetMapping("/eliminar/{id}")
    public RedirectView delete(@PathVariable("id") Integer id, RedirectAttributes attr) throws ExceptionService{
       try {
           custServ.deleteCustomer(id);
           attr.addFlashAttribute("exito", "Se eliminó correctamente el cliente.");
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       
       return new RedirectView("/cliente");
    }
    
    @PostMapping("/guardar")
    public RedirectView createCustomer(@RequestParam Long dni, @RequestParam String name, @RequestParam String lastName, @RequestParam String phoneNumber, RedirectAttributes attr) throws ExceptionService{
       try {
           custServ.addCustomer(dni, name, lastName, phoneNumber);
           attr.addFlashAttribute("exito", "Se agregó correctamente el cliente.");
           
       } catch (Exception ex) {
           attr.addFlashAttribute("error", ex.getMessage());
       }
       
       return new RedirectView("/cliente");
    }
    
    @PostMapping("/editar")
    public RedirectView editCustomer(@RequestParam Integer id, @RequestParam String name, @RequestParam String lastName, @RequestParam String phoneNumber, RedirectAttributes attr) throws ExceptionService{
       try {
           custServ.modifyCustomer(id, name, lastName, phoneNumber);
           attr.addFlashAttribute("exito", "Se modificó correctamente el cliente.");
           
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       return new RedirectView("/cliente");
    }
    
    
}
    
