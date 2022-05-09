package com.unpontdev.comparator.controler;

import com.unpontdev.comparator.entities.Member;
import com.unpontdev.comparator.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Date;

@Controller
public class IndexController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/account")
    public String showAccount(Model model, Principal principal, Member member){
        if(principal == null){
            return "index";
        }
        model.addAttribute("message", "Salut, bine ai venit la UnPont Product Comparator");
        model.addAttribute("date", new Date());
        model.addAttribute("members", memberRepository.getOne(principal.getName()));
        return "mainPage";
    }

    @GetMapping("/")
    public String showIndex(Model model, Principal principal, Member member){
        return "index";
    }
}
