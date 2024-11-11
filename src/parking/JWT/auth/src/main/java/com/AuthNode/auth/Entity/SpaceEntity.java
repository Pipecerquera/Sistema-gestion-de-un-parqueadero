package com.AuthNode.auth.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "space")
public class SpaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "relative_name", nullable = false)
    private String relativeName;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied;

    @Column(name = "is_reserved", nullable = false)
    private boolean isReserved;

    @Column(name = "tipo_vehiculo", nullable = false)
    private String tipoVehiculo;  

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRelativeName() {
        return relativeName;
    }

    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
}
