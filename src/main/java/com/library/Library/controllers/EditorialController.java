
package com.library.Library.controllers;

import com.library.Library.Exception.ExceptionService;
import com.library.Library.entities.Editorial;
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
@RequestMapping("/editorial")
public class EditorialController {
    
    @Autowired
    private EditorialService edServ;
    
    @GetMapping("")
    public ModelAndView showEditorials(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("editorial");
        List<Editorial> listEditorial = edServ.EdList();
        
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        
        if(flashMap != null){
           mav.addObject("exito", flashMap.get("exito"));
           mav.addObject("error", flashMap.get("error"));
        }
        
        mav.addObject("editoriales", listEditorial);
        return mav;
    }
    
    @GetMapping("/crear")
   public ModelAndView createEditorial(){
       ModelAndView mav = new ModelAndView("formularioEditorial");
       mav.addObject("editorial", new Editorial());
       mav.addObject("title", "Crear Editorial");
       mav.addObject("action", "guardar");
       
       return mav;
   }
   
   @GetMapping("/modificar/{id}")
   public ModelAndView modifyEditorial(@PathVariable Integer id){
       ModelAndView mav = new ModelAndView("formularioEditorial");
       mav.addObject("editorial", edServ.findById(id));
       mav.addObject("title", "Modificar Editorial");
       mav.addObject("action", "editar");
       
       return mav;
   }
   
   @GetMapping("/eliminar/{id}")
   public RedirectView delete(@PathVariable("id") Integer id, RedirectAttributes attr) throws ExceptionService{
       try {
           edServ.deleteEditorial(id);
           attr.addFlashAttribute("exito", "Se eliminó correctamente la editorial.");
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       
       return new RedirectView("/editorial");
   }
   
   @PostMapping("/guardar")
   public RedirectView createEditorial(@RequestParam String name, RedirectAttributes attr) throws ExceptionService{
       try {
           edServ.addEditorial(name);
           attr.addFlashAttribute("exito", "Se agregó correctamente la editorial.");
           
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       
       return new RedirectView("/editorial");
   }
   
   @PostMapping("/editar")
   public RedirectView editEditorial(@RequestParam Integer id, @RequestParam String name, RedirectAttributes attr) throws ExceptionService{
       try {
           edServ.modifyEditorial(id, name);
           attr.addFlashAttribute("exito", "Se modificó correctamente la editorial.");
           
       } catch (Exception e) {
           attr.addFlashAttribute("error", e.getMessage());
       }
       return new RedirectView("/editorial");
   }
}
