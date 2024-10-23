package br.dev.ferreiras.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="tb_contacts")
public class Contact {

    @Id
    @Column(unique = true)
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @Email
    private String email;

    private String created_at;

    private String update_at;

    public Contact() {
    }

    public Contact(Long id, String name, String email, String created_at, String update_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @NotNull String getName() {
        return name;
    }

    public void setName(@NotBlank @NotNull String name) {
        this.name = name;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
