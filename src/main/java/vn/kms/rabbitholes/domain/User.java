/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "users")
@Entity
public class User implements Serializable {
    public enum Role {
        ADMIN,
        USER,
        ANNONYMOUS;
    }

    public static final User ANONYMOUS_USER;

    static {
        ANONYMOUS_USER = new User();
        ANONYMOUS_USER.setFirstName("Anonymous");
        ANONYMOUS_USER.setLastName("User");
        ANONYMOUS_USER.setRole(Role.ANNONYMOUS);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonIgnore
    public boolean isLoggedIn() {
        return (role != null) && (role != Role.ANNONYMOUS);
    }

    @JsonIgnore
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}
