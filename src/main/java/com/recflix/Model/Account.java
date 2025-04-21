package com.recflix.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "Date_Of_Birth")
    private Date dateOfBirth;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Phone_Number", unique = true)
    private String phoneNumber;

    @ManyToMany
    @JoinTable(
        name = "account_favourite_movies",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<MovieDetail> favouriteMovies = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "User_Id", unique = true)
    private Users user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<MovieDetail> getFavouriteMovies() {
        return favouriteMovies;
    }

    public void setFavouriteMovies(Set<MovieDetail> favouriteMovies) {
        this.favouriteMovies = favouriteMovies;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
