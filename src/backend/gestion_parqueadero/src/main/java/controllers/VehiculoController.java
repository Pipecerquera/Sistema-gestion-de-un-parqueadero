package controllers;

import models.Vehiculo;
import services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "http://localhost:4200") // Ajusta la URL del frontend
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return vehiculoService.obtenerTodosLosVehiculos();
    }

    @GetMapping("/tipo/{tipoVehiculo}")
    public List<Vehiculo> obtenerVehiculosPorTipo(@PathVariable String tipoVehiculo) {
        return vehiculoService.obtenerVehiculosPorTipo(tipoVehiculo);
    }

    @GetMapping("/{placa}")
    public Vehiculo obtenerVehiculoPorPlaca(@PathVariable String placa) {
        return vehiculoService.obtenerVehiculoPorPlaca(placa);
    }

    @PostMapping
    public Vehiculo registrarVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.registrarVehiculo(vehiculo);
    }

    @DeleteMapping("/{id}")
    public void eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminarVehiculo(id);
    }
}
