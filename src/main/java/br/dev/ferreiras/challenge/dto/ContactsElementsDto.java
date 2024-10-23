package br.dev.ferreiras.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpHeaders;

import java.time.Instant;

public class ContactsElementsDto {
    private Long id;
    private String name;
    private String email;
    @NotBlank
    private String source = "KENECT_LABS";
    private Instant created_at;
    private Instant updated_at;


    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ContactsElementsDto(Long id, Instant updated_at, Instant created_at, String email, String name, String source) {
        this.id = id;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.email = email;
        this.name = name;
        this.source = source;
    }

    public ContactsElementsDto() {
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public void setUpdate_at(Instant updated_at) {
        this.updated_at = updated_at;
    }

}
