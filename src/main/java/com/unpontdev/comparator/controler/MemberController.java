package com.unpontdev.comparator.controler;

import com.unpontdev.comparator.entities.Member;
import com.unpontdev.comparator.repositories.MemberRepository;
import com.unpontdev.comparator.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/login")
    public String showLoginForm(){
        return "views/loginForm";
    }

    @GetMapping("/despre")
    public String showAboutPage(){
        return "despre";
    }

    @GetMapping("/termeni")
    public String showTermsPage(){
        return "termeni";
    }

    @GetMapping("/main-comparator")
    public String showComparatorMainPage(){
        return "main-comparator";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("member", new Member());
        return "views/registerForm";
    }
    @PostMapping("/register")
    public String registerMember(@Valid Member member, Model model){
        String email = member.getEmail();
        if (memberRepository.findByEmail(email) != null){
            model.addAttribute("exist",true);
            return "views/registerForm";
        }
        memberService.createMember(member);
        model.addAttribute("success", true);
        return "views/loginForm";
    }
}