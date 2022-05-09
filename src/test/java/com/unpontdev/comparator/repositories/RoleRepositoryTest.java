package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository repo;


    @Test
    public void testCreateRoleMember() {
        Role role = new Role("Subscriber");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Role savedRole = repo.save(role);
        Role existRole = entityManager.find(Role.class, savedRole.getName());

        assertThat(role.getName()).isEqualTo(existRole.getName());
    }

}