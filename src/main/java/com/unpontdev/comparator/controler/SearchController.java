package com.unpontdev.comparator.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    //@GetMapping("/search")
    //public String showSearch(){
    //    return "search";
    //}

    @RequestMapping("/main-comparator")
    public String searchForm(Model model,
                             @RequestParam("keyword") String keyword,
                             @RequestParam("altex") Boolean altex,
                             @RequestParam("flanco") Boolean flanco,
                             @RequestParam("emag") Boolean emag,
                             @RequestParam("vivre") Boolean vivre){
        model.addAttribute("keyword", keyword);
        model.addAttribute("altex", altex);
        model.addAttribute("flanco", flanco);
        model.addAttribute("emag", emag);
        model.addAttribute("vivre", vivre);
        return "/main-comparator";
    }


}
