package com.example.tareadistribuidas.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

/**
 *
 * @author amanda
 */
public class User implements Serializable {

    int idUsuario;
    String firstName, lastName, birthdate, gender, phone, username, mail,
            password, image;
    boolean state;

    public User() {
    }

    public User(int idUsuario, String firstName, String lastName, String birthdate, String gender, String phone, String username, String mail, String password, String image, boolean state) {
        this.idUsuario = idUsuario;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.image = image;
        this.state = state;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "{" + "\"idUsuario\":" + idUsuario + ", \"firstName\":\""
                + firstName + "\", \"lastName\":\"" + lastName
                + "\", \"birthdate\":\"" + birthdate + "\", \"gender\":\"" + gender
                + "\", \"phone\":\"" + phone + "\", \"username\":\"" + username
                + "\", \"mail\":\"" + mail + "\", \"password\":\"" + password
                + "\", \"image\":\"" + image + "\", \"state\":\"" + state + "\"}";
    }

}
