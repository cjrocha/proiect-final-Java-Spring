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

/**
 * Handling the member login and register
 * pages
 */
@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    /**
     * Apeals the login page / form
     * @return - login form
     */
    @GetMapping("/login")
    public String showLoginForm(){
        return "views/loginForm";
    }

    /**
     * Apeals the register page / form
     * @param model - html data
     * @return - register form
     */
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("member", new Member());
        return "views/registerForm";
    }

    /**
     * Showing register page
     * and handling pages if user is already registered
     * @param member - member object data
     * @param model - html handler
     * @return - register or login page
     */
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