package br.dev.ferreiras.challenge.entity;


import br.dev.ferreiras.challenge.dto.LoginRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name="tb_users")
public class User {
    public User() {
    }

    public User(Long id, String username, String password, Instant createdAt, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Email
    private String username;

    @Column (nullable = false)
    @NotBlank
    @Size(min = 10, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @CreationTimestamp
    private Instant createdAt;

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable (
            name = "tb_users_roles",
            joinColumns = @JoinColumn (name = "id"),
            inverseJoinColumns = @JoinColumn (name = "role_id")
    )
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @NotBlank @Email String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @NotBlank @Email String username) {
        this.username = username;
    }

    public @NotBlank @Size(min = 10, max = 100) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 10, max = 100) String password) {
        this.password = password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isLoginCorrect(LoginRequestDto loginRequestDto, PasswordEncoder passwordEncoder) {

        return passwordEncoder.matches(loginRequestDto.password(), this.password);
    }
}
