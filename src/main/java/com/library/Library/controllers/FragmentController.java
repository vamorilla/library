
package com.library.Library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentController {
    
    @GetMapping("/fragmento")
    public String getHome() {
        return "fragmento.html";
    }
}
