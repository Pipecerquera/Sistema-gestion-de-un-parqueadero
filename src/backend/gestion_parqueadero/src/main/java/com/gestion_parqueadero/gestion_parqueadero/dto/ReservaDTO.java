package com.gestion_parqueadero.gestion_parqueadero.dto;

import java.time.LocalDateTime;

public class ReservaDTO {
    private Long id;
    private Long parqueaderoId;
    private Long vehiculoId;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParqueaderoId() {
        return parqueaderoId;
    }

    public void setParqueaderoId(Long parqueaderoId) {
        this.parqueaderoId = parqueaderoId;
    }

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}
