package br.dev.ferreiras.challenge.entity;

import jakarta.persistence.*;

@Entity
@Table(name="tb_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column (name = "role")
    private String role;

    public enum Roles {
        ROLE_ADMIN(1L),
        ROLE_USER(2L);

        final long roleId;
        Roles(long roleId) {
            this.roleId = roleId;
        }
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


