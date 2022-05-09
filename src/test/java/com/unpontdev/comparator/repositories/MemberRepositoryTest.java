package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
class MemberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository repo;

    @Test
    public void testCreateMember() {
        Member member = new Member();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setEmail("chi_andrei@yahoo.com");
        member.setPassword(encoder.encode("mtacachi1975"));
        member.setFirstName("Andy");
        member.setLastName("Chirila");

        Member savedMember = repo.save(member);
        Member existMember = entityManager.find(Member.class, savedMember.getEmail());

        assertThat(member.getEmail()).isEqualTo(existMember.getEmail());

    }
}