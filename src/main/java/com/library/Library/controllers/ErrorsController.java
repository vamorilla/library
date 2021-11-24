
package com.library.Library.controllers;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController implements ErrorController{
    @RequestMapping(value="/error", method={RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showErrors(HttpServletResponse hsr){
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("code", hsr.getStatus());
        String msg = "";
        switch(hsr.getStatus()){
            case 403:
                msg = "Aceso denegado";
                break;
            case 404:
                msg = "No se encontró la página";
                break;
            case 500:
                msg = "Error de comunicación con servidor";
                break;
            default:
                msg = "Error general";
        }
        mav.addObject("message", msg);
        
        return mav;
    }
}
