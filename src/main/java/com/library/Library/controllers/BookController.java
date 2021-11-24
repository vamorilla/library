
package com.library.Library.controllers;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Book;
import com.library.Library.service.AuthorServices;
import com.library.Library.service.BookService;
import com.library.Library.service.EditorialService;
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
@RequestMapping("/libro")
public class BookController {
    
    @Autowired
    private BookService bServ;
    
    @Autowired
    private EditorialService edServ;
    
    @Autowired
    private AuthorServices aServ;
    
    @GetMapping("")
    public ModelAndView showBooks(HttpServletRequest request){
       ModelAndView mav = new ModelAndView("libro");
       List<Book> bookList = bServ.BookList();
       
       Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
       if(flashMap != null){
           mav.addObject("exito", flashMap.get("exito"));
           mav.addObject("error", flashMap.get("error"));
       }
       mav.addObject("libros", bookList);
       return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView createBook(){
       ModelAndView mav = new ModelAndView("formularioLibro");
       mav.addObject("libro", new Book());
       mav.addObject("title", "Crear Libro");
       mav.addObject("action", "guardar");
       mav.addObject("autores", aServ.AuthorList());
       mav.addObject("editoriales", edServ.EdList());
       
       return mav;
    }
    
    @GetMapping("/modificar/{isBn}")
   public ModelAndView modifyBook(@PathVariable Long isBn){
       ModelAndView mav = new ModelAndView("formularioLibro");
       mav.addObject("libro", bServ.findById(isBn));
       mav.addObject("title", "Modificar Libro");
       mav.addObject("action", "editar");
       mav.addObject("autores", aServ.AuthorList());
       mav.addObject("editoriales", edServ.EdList());
       
       return mav;
   }
   
   @GetMapping("/alta/{isBn}")
   public RedirectView toRegister(@PathVariable Long isBn, RedirectAttributes attr){
       try{
           bServ.toRegister(isBn);
           attr.addFlashAttribute("exito", "Se dio de alta correctamente el libro.");
       }catch(Exception e){
           attr.addFlashAttribute("error", e.getMessage());
       }
        return new RedirectView("/libro");
   }
   
   @GetMapping("/baja/{isBn}")
   public RedirectView unsubscribe(@PathVariable Long isBn, RedirectAttributes attr){
       try{
           bServ.unsubscribe(isBn);
           attr.addFlashAttribute("exito", "Se dio de baja correctamente el libro.");
       }catch(Exception e){
           attr.addFlashAttribute("error", e.getMessage());
       }
        return new RedirectView("/libro");
   }
   
   @GetMapping("/eliminar/{isBn}")
   public RedirectView delete(@PathVariable("isBn") Long isBn, RedirectAttributes attr) throws ExceptionService{
       try {
           bServ.deleteBook(isBn);
           attr.addFlashAttribute("exito", "Se eliminó correctamente el libro.");
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       
       return new RedirectView("/libro");
   }
   
   @PostMapping("/guardar")
   public RedirectView createBook(@RequestParam Long isBn, @RequestParam String title, @RequestParam Integer year, @RequestParam Integer copies, @RequestParam Integer copiesOnLoan, @RequestParam Integer author, @RequestParam Integer e, RedirectAttributes attr) throws ExceptionService{
       try {
           bServ.addBook(isBn, title, year, copies, copiesOnLoan, author, e);
           attr.addFlashAttribute("exito", "Se agregó correctamente el libro.");
           
       } catch (Exception ex) {
           attr.addFlashAttribute("error", ex.getMessage());
       }
       
       return new RedirectView("/libro");
   }
   
   @PostMapping("/editar")
   public RedirectView editBook(@RequestParam Long isBn, @RequestParam String title, @RequestParam Integer year, @RequestParam Integer copies, @RequestParam Integer copiesOnLoan, @RequestParam Integer author, @RequestParam Integer e, RedirectAttributes attr) throws ExceptionService{
       try {
           bServ.modifyBook(isBn, title, year, copies, copiesOnLoan, author, e);
           attr.addFlashAttribute("exito", "Se modificó correctamente el libro.");
           
       } catch (Exception ex) {
           attr.addFlashAttribute("error", ex.getMessage());
       }
       return new RedirectView("/libro");
   }
   
}
