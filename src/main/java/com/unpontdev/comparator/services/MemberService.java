package com.unpontdev.comparator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.unpontdev.comparator.repositories.MemberRepository;
import com.unpontdev.comparator.entities.Member;
import com.unpontdev.comparator.entities.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates member data adds encrypted pass
 * and save's the data into DB
 */
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * Member object is being configured (added encrypted pass + setting role)
     * and saved to DB
     * @param member - member object
     * @return - member's object data
     */
    public Member createMember(Member member){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setPassword(encoder.encode(member.getPassword()));
        Role memberRole = new Role("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(memberRole);
        member.setRole(roles);
        memberRepository.save(member);
        return member;
    }
}