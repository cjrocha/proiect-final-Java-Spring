package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Extending JPA repo, this interface
 * communicates with DB.
 */
public interface MemberRepository extends JpaRepository<Member, String> {
    /**
     * Contract method that finds members
     * from DB by email(set as Id)
     * @param email - member email
     * @return - the member data
     */
    public Member findByEmail(String email);


}