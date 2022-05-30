package com.unpontdev.comparator.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Helds the members role object
 * implemented but not used yet in the the web app.
 * Will allow serving particular data to admin and/or member
 */
@Entity
public class Role {

    @Id
    private String name;

    @ManyToMany(mappedBy = "role")
    private List<Member> members;

    /**
     * constructor for member role
     * @param name
     */
    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    /**
     * Setters and getters for role data
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }


}
