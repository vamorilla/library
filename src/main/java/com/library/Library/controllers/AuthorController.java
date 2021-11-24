
package com.library.Library.controllers;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Autor;
import com.library.Library.entities.Book;
import com.library.Library.service.AuthorServices;
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
@RequestMapping("/autor")
public class AuthorController {
    
    @Autowired
    private AuthorServices auServ;
    
   @GetMapping("")
   public ModelAndView showAuthors(HttpServletRequest request){
       ModelAndView mav = new ModelAndView("autor");
       List<Autor> listAuthors = auServ.AuthorList();
       
       Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
       if(flashMap != null){
           mav.addObject("exito", flashMap.get("exito"));
           mav.addObject("error", flashMap.get("error"));
       }
       mav.addObject("autores", listAuthors);//Autores es lo que llamo en mi html en el each
       return mav;
   }
   
   @GetMapping("/crear")
   public ModelAndView createAuthor(){
       ModelAndView mav = new ModelAndView("formularioAutor");
       mav.addObject("autor", new Book());
       mav.addObject("title", "Crear Autor");
       mav.addObject("action", "guardar");
       
       return mav;
   }
   
   @GetMapping("/modificar/{id}")
   public ModelAndView modifyAuthor(@PathVariable Integer id){
       ModelAndView mav = new ModelAndView("formularioAutor");
       mav.addObject("autor", auServ.findById(id));
       mav.addObject("title", "Modificar Autor");
       mav.addObject("action", "editar");
       
       return mav;
   }
   
   @GetMapping("/eliminar/{id}")
   public RedirectView delete(@PathVariable("id") Integer id, RedirectAttributes attr) throws ExceptionService{
       try {
           auServ.deleteAuthor(id);
           attr.addFlashAttribute("exito", "Se eliminó correctamente el autor.");
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       
       return new RedirectView("/autor");
   }
   
   @PostMapping("/guardar")
   public RedirectView createAuthor(@RequestParam String name, RedirectAttributes attr) throws ExceptionService{
       try {
           auServ.addAuthor(name);
           attr.addFlashAttribute("exito", "Se agregó correctamente el autor.");
           
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       
       return new RedirectView("/autor");
   }
   
   @PostMapping("/editar")
   public RedirectView editAuthor(@RequestParam Integer id, @RequestParam String name, RedirectAttributes attr) throws ExceptionService{
       try {
           auServ.modifyAuthor(id, name);
           attr.addFlashAttribute("exito", "Se modificó correctamente el autor.");
           
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       return new RedirectView("/autor");
   }
}
