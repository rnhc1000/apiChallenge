package br.dev.ferreiras.challenge.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.Instant;

@Entity
@Table(name="tb_contacts")
@SQLDelete(sql = "UPDATE tb_contacts SET deleted=true where id = ?")
@FilterDef(
        name = "deletedTb_contactsFilter",
        parameters = @ParamDef(name = "isDeleted", type = org.hibernate.type.descriptor.java.BooleanJavaType.class)
)
@Filter(
        name = "deletedTb_contactsFilter",
        condition = "deleted = :isDeleted"
)
public class Contact {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @Email
    @Column (unique = true)
    private String email;

    @NotBlank
    private String company;

    @CreationTimestamp
    private Instant createdAt;

    @CreationTimestamp
    private Instant updateAt;

    private final boolean deleted=Boolean.FALSE;

    @Column (nullable = false)
    @NotBlank
    @Size(min = 10, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public @NotBlank @Size(min = 10, max = 100) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 10, max = 100) String password) {
        this.password = password;
    }

    public Contact() {
    }

    public Contact(Long id, String name, String email, String company, Instant createdAt, Instant updateAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.company = company;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public Contact(String name, String email, String company, Instant createdAt, Instant updateAt) {
        this.name = name;
        this.email = email;
        this.company = company;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
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

    public @NotBlank String getCompany() {
        return company;
    }

    public void setCompany(@NotBlank String company) {
        this.company = company;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", deleted=" + deleted +
                ", password='" + password + '\'' +
                '}';
    }
}
