package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, String> {

    public Member findByEmail(String email);

}