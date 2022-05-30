package com.unpontdev.comparator.controler;

import com.unpontdev.comparator.entities.Member;
import com.unpontdev.comparator.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles main page and account page
 */
@Controller
public class IndexController {

    @Autowired
    MemberRepository memberRepository;

    /**
     * If user is authenticated it serves the account page
     * else sends user to main page
     * @param model - html handlers
     * @param principal - authenticated user
     * @param member - the user object data
     * @return - the account page along with attributes
     */
    @GetMapping("/account")
    public String showAccount(Model model, Principal principal, Member member){
        if(principal == null){
            return "index";
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String azi = dtf.format(now);
        model.addAttribute("message", "Salut, bine ai venit la UnPont Product Comparator");
        model.addAttribute("date", azi);
        model.addAttribute("members", memberRepository.getOne(principal.getName()));
        return "mainPage";
    }

    /**
     * Maps the main page
     * @return main page
     */
    @GetMapping("/")
    public String showIndex(){
        return "index";
    }
}
