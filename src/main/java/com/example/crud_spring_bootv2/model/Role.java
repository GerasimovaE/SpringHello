package com.example.crud_spring_bootv2.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = "id"), @UniqueConstraint(columnNames = "name")})
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name.substring(name.indexOf("_") + 1).toUpperCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name.substring(name.indexOf("_") + 1).toUpperCase();
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
