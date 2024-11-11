import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

interface Espacio {
  id: number;
  relativeName: string;
  isOccupied: boolean;
  isReserved: boolean;
  tipoVehiculo: String;
}

interface Vehiculo {
  id: number;
  placa: string;
  tipoVehiculo: string;
  propietario: string;
}

@Component({
  selector: 'app-parking',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './parking-details.component.html',
  styleUrls: ['./parking-details.component.css']
})
export class ParkingComponent implements OnInit {
  espacios: Espacio[] = [];
  vehiculos: Vehiculo[] = [];
  mostrarModal: boolean = false;
  espacioSeleccionado?: Espacio;
  vehiculoSeleccionado?: number;
  fechaReserva?: string;
  fechaSalida?: string;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.cargarEspacios();
    this.cargarVehiculos();
  }

  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  crearHeaders(): HttpHeaders {
    const token = this.obtenerToken();
    return new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }

  cargarEspacios(): void {
    this.http.get<Espacio[]>('http://localhost:9000/api/space/all', { headers: this.crearHeaders() })
      .subscribe(data => {
        this.espacios = data;
      });
  }

  cargarVehiculos(): void {
    const userId = localStorage.getItem("userId");

    this.http.get<any[]>(`http://localhost:9000/api/vehiculo/persona/${userId}`, { headers: this.crearHeaders() })
      .subscribe(data => {
        this.vehiculos = data.map(item => ({
          id: item[0],           // El primer valor del array: id
          placa: item[2],        // El segundo valor del array: placa
          tipoVehiculo: item[1], // El tercer valor del array: tipo de vehículo
          propietario: item[3]   // El cuarto valor del array: propietario
        }));
      });
  }

  espaciosPorTipo(tipo: string): Espacio[] {
    return this.espacios.filter(espacio => espacio.tipoVehiculo === tipo);
  }

  abrirModal(espacio: Espacio): void {
    if (!espacio.isOccupied) {
      this.espacioSeleccionado = espacio;
      this.mostrarModal = true;
    }
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.espacioSeleccionado = undefined;
    this.vehiculoSeleccionado = undefined;
    this.fechaReserva = undefined;
    this.fechaSalida = undefined;
  }

  reservarEspacio(): void {
    if (this.espacioSeleccionado && this.vehiculoSeleccionado && this.fechaReserva && this.fechaSalida) {
      const reserva = {
        space: { id: this.espacioSeleccionado.id },
        vehiculo: {
          id: this.vehiculoSeleccionado
        },
        fechaReserva: this.fechaReserva,
        fechaSalida: this.fechaSalida
      };
      this.http.post('http://localhost:9000/api/reserva/add', reserva, { headers: this.crearHeaders() })
        .subscribe(() => {
          this.actualizarIsReserved(this.espacioSeleccionado!.id, true);
          Swal.fire({
            title: 'Reserva realizada con éxito',
            icon: 'success',
            confirmButtonText: 'Aceptar'
          });
          this.cargarEspacios(); // Recargar espacios después de la reserva
          this.cerrarModal();
        });
    }
  }

  // Método para actualizar solo el campo isReserved
  actualizarIsReserved(id: number, isReserved: boolean): void {
    this.http.patch(`http://localhost:9000/api/space/update/reserved/${id}`, { isReserved }, { headers: this.crearHeaders() })
      .subscribe(
        () => {
          console.log('Campo isReserved actualizado con éxito');
          this.cargarEspacios(); // Recargar espacios después de actualizar isReserved
        },
        error => console.error('Error al actualizar el campo isReserved:', error)
      );
  }
}
