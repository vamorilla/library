
package com.library.Library.controllers;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Loan;
import com.library.Library.service.BookService;
import com.library.Library.service.CustomerService;
import com.library.Library.service.LoanService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/prestamo")
public class LoanController {
    
    @Autowired
    private LoanService lServ;
    @Autowired
    private BookService bServ;
    @Autowired
    private CustomerService custServ;
    
    @GetMapping("")
    public ModelAndView showLoans(HttpServletRequest request){
       ModelAndView mav = new ModelAndView("prestamo");
       List<Loan> loanList = lServ.loanList();
       
       Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
       if(flashMap != null){
           mav.addObject("exito", flashMap.get("exito"));
           mav.addObject("error", flashMap.get("error"));
       }
       mav.addObject("prestamos", loanList);
       return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView createLoan(){
       ModelAndView mav = new ModelAndView("formularioPrestamo");
       mav.addObject("prestamo", new Loan());
       mav.addObject("title", "Crear Prestamo");
       mav.addObject("action", "guardar");
       mav.addObject("libros", bServ.BookList());
       mav.addObject("clientes", custServ.customerList());
       
       return mav;
    }
    
    @GetMapping("/modificar/{id}")
    public ModelAndView modifyLoan(@PathVariable Integer id){
       ModelAndView mav = new ModelAndView("formularioPrestamo");
       mav.addObject("prestamo", lServ.findById(id));
       mav.addObject("title", "Modificar Prestamo");
       mav.addObject("action", "editar");
       mav.addObject("libros", bServ.BookList());
       mav.addObject("clientes", custServ.customerList());
       
       return mav;
    }
    
    @GetMapping("/eliminar/{id}")
   public RedirectView delete(@PathVariable("id") Integer id, RedirectAttributes attr) throws ExceptionService{
       try {
           lServ.delete(id);
           attr.addFlashAttribute("exito", "Se eliminó correctamente el prestamo.");
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       
       return new RedirectView("/prestamo");
   }
   
   @PostMapping("/guardar")
   public RedirectView createLoan(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date returnDate, @RequestParam Long book, @RequestParam Integer customer, RedirectAttributes attr) throws ExceptionService{
       try {
           lServ.addLoan(returnDate, book, customer);
           attr.addFlashAttribute("exito", "Se agregó correctamente el prestamo.");
           
       } catch (Exception ex) {
           attr.addFlashAttribute("error", ex.getMessage());
       }
       
       return new RedirectView("/prestamo");
   }
   
   @PostMapping("/editar")
   public RedirectView editLoan(@RequestParam Integer id, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date returnDate, @RequestParam Long book, @RequestParam Integer customer, RedirectAttributes attr) throws ExceptionService{
       try {
           lServ.modifyLoan(id, returnDate, book, customer);
           attr.addFlashAttribute("exito", "Se modificó correctamente el prestamo.");
           
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       return new RedirectView("/prestamo");
   }
}
