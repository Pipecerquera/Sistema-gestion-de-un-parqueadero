package com.AuthNode.auth.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "commentary", nullable = false, unique = false, length = 200)
    private String commentary;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private PersonaEntity persona;

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
    }
}
