package com.gmail.mordress.epamproject.entities;

import java.math.BigDecimal;

/**
 * Class-Entity, describes user.
 * @author Alexey Kardychko
 * @version 1.0
 */
public class User extends Entity {

    /** Unique field needs to authentication and authorization user in web-app. */
    private String login;

    private String firstName;

    private String lastName;

    /** This field stores user's password as SHA-256 hash*/
    private String password;

    private String email;

    private Role role;

    /** This field uses to store user's cash. User-administrator and user-bookmaker have not cash(value == null). */
    private BigDecimal cashAmount;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    @Override
    public String toString() {
        return new StringBuilder("User:{")
                .append("UserId = ").append(this.getId())
                .append(", login = ").append(login)
                .append(", firstName = ").append(firstName)
                .append(", lastName = ").append(lastName)
                .append(", email = ").append(email)
                .append(", role = ").append(role)
                .append(", cashAmount = ").append(cashAmount)
                .append("}")
                .toString();
    }
}
