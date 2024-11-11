package com.AuthNode.auth.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "reserva")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private VehiculoEntity vehiculo;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private SpaceEntity space;

    @Column(name = "fecha_reserva", nullable = false)
    private Date fechaReserva;
    
    @Column(name = "fecha_salida", nullable = false)
    private Date fechaSalida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VehiculoEntity getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehiculoEntity vehiculo) {
        this.vehiculo = vehiculo;
    }

    public SpaceEntity getSpace() {
        return space;
    }

    public void setSpace(SpaceEntity space) {
        this.space = space;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    
    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}
