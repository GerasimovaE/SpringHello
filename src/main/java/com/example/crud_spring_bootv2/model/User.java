package com.example.crud_spring_bootv2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private byte age;

    private String email;

    private String password;

    private boolean enabled = true;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Role> likedUser;

    public List<Role> getLikedUser() {
        return likedUser;
    }

    public void setLikedUser(List<Role> likedUser) {
        this.likedUser = likedUser;
    }

    public void addLikedUser(Role role) {
        this.likedUser.add(role);
    }

    public User() {
    }

    public User(String name, byte age, String email, boolean enabled) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        List<Role> likedUser = new ArrayList<>();
//        likedUser.add(new Role("ROLE_admin"));
//
//        return likedUser;
        return likedUser;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
