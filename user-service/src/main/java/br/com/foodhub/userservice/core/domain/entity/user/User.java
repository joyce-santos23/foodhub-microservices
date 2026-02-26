package br.com.foodhub.userservice.core.domain.entity.user;

import br.com.foodhub.userservice.core.domain.entity.address.UserAddress;
import br.com.foodhub.userservice.core.domain.exceptions.generic.RequiredFieldException;
import br.com.foodhub.userservice.core.domain.exceptions.generic.ResourceConflictException;
import br.com.foodhub.userservice.core.domain.exceptions.user.AddressNotBelongsToUserException;
import br.com.foodhub.userservice.core.domain.exceptions.user.InvalidCpfException;
import br.com.foodhub.userservice.core.domain.exceptions.user.InvalidEmailException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class User {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private String password;
    private UserType userType;

    private List<UserAddress> addresses = new ArrayList<>();

    public User(
            String name,
            String email,
            String phone,
            String password,
            UserType userType
    ) {
        this.name = require(name, "Name");
        this.email = normalizeEmail(require(email, "Email"));
        this.phone = require(phone, "Phone");
        this.password = require(password, "Password");
        this.userType = require(userType, "User Type");
    }

    private User(
            String id,
            String name,
            String email,
            String phone,
            String cpf,
            String password,
            UserType userType
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.password = password;
        this.userType = userType;
        this.addresses = new ArrayList<>();
    }

    public static User reconstitute(
            String id,
            String name,
            String email,
            String phone,
            String cpf,
            String password,
            UserType userType
    ) {
        return new User(id, name, email, phone, cpf, password, userType);
    }

    /* =========================
       Comportamentos de domínio
       ========================= */

    public void defineCpf(String cpf) {
        if (this.cpf != null) {
            throw new ResourceConflictException("Cpf já foi definido para este usuário.");
        }
        this.cpf = normalizeCpf(require(cpf, "CPF"));
    }

    public void changeName(String name) {
        this.name = require(name, "Nome");
    }

    public void changeEmail(String email) {
        this.email = normalizeEmail(require(email, "Email"));
    }

    public void changePhone(String phone) {
        this.phone = require(phone, "Telefone");
    }

    public void changePassword(String password) {
        this.password = require(password, "Password");
    }

    /* =========================
       Endereços
       ========================= */

    public void addAddress(UserAddress address) {

        require(address, "Endereço");

        if (this.addresses.contains(address)) {
            throw new ResourceConflictException("Endereço já cadastrado");
        }

        if (address.isPrimary()) {
            unsetCurrentPrimary();
        }

        this.addresses.add(address);
    }

    public void removeAddress(String addressId) {

        boolean removed = addresses.removeIf(
                address -> address.getId().equals(addressId)
        );

        if (!removed) {
            throw new AddressNotBelongsToUserException();
        }
    }

    public void definePrimaryAddress(String addressId) {

        boolean found = false;

        for (UserAddress address : addresses) {

            if (addressId.equals(address.getId())) {
                address.markAsPrimary();
                found = true;
            } else {
                address.unmarkAsPrimary();
            }
        }

        if (!found) {
            throw new AddressNotBelongsToUserException();
        }
    }

    private void unsetCurrentPrimary() {
        for (UserAddress address : addresses) {
            address.unmarkAsPrimary();
        }
    }

    /* =========================
       Utilidades
       ========================= */

    private String require(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new RequiredFieldException(field);
        }
        return value;
    }

    private <T> T require(T value, String field) {
        if (value == null) {
            throw new RequiredFieldException(field);
        }
        return value;
    }

    private String normalizeCpf(String cpf) {
        String normalized = cpf.replaceAll("\\D", "");

        if (normalized.length() != 11) {
            throw new InvalidCpfException("Cpf inválido");
        }

        return normalized;
    }

    private String normalizeEmail(String email) {
        String normalized = email.trim().toLowerCase();

        if (!normalized.matches(".+@.+\\..+")) {
            throw new InvalidEmailException("Email inválido");
        }

        return normalized;
    }
}