package com.example.crud_spring_bootv2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

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

    public User(long id, String firstName, String lastName, byte age, String email, String password, List<Role> likedUser) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.likedUser = likedUser;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
////
//        return likedUser;
        return likedUser;
    }

    public boolean hasRole(int roleId) {
        if (null == likedUser || 0 == likedUser.size()) {
            return false;
        }
        Optional<Role> findRole = likedUser.stream().filter(role -> roleId == role.getId()).findFirst();
        return findRole.isPresent();
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

    public boolean isNew() {
        return null == (Long)id;
    }

    public String getRoleAllString(){

        String roles = "";

        for (Role role : likedUser) {
            roles = roles + " " + role.getName();
        }

        return roles.substring(1);

    }
}
