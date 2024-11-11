package com.AuthNode.auth.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculo")
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_vehiculo", nullable = false, length = 50)
    private String tipoVehiculo;

    @Column(name = "placa", nullable = false, unique = true, length = 20)
    private String placa;

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

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
    }
}
