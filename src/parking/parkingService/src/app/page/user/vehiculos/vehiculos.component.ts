import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-vehiculos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vehiculos.component.html',
  styleUrls: ['./vehiculos.component.css']
})
export class VehiculosComponent implements OnInit {
  vehiculos: any[] = [];
  vehiculoForm: any = { placa: '', tipoVehiculo: '', persona: { id: '' } };
  editando: boolean = false;
  apiUrl = 'http://localhost:9000/api/vehiculo';
  userId: string | null = null;
  token: string | null = null;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');
    this.token = localStorage.getItem('token');
    if (this.userId) {
      this.vehiculoForm.persona.id = this.userId;
    }
    this.cargarVehiculos();
  }

  private getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${this.token || ''}`
    });
  }

  cargarVehiculos() {
    this.http.get<any[]>(`${this.apiUrl}/persona/${this.userId}`, { headers: this.getAuthHeaders() }).subscribe(
      (data) => {
        this.vehiculos = data.map((vehiculo) => ({
          id: vehiculo[0],
          tipoVehiculo: vehiculo[1],
          placa: vehiculo[2],
          personaFullname: vehiculo[3]
        }));
      },
      (error) => {
        Swal.fire('Error', 'Error al cargar los vehículos', 'error');
        console.error(error);
      }
    );
  }

  guardarVehiculo() {
    if (this.editando) {
      this.actualizarVehiculo();
    } else {
      this.agregarVehiculo();
    }
  }

  agregarVehiculo() {
    this.http.post<any>(`${this.apiUrl}/add`, this.vehiculoForm, { headers: this.getAuthHeaders() }).subscribe(
      (data) => {
        Swal.fire('Éxito', 'Vehículo agregado con éxito', 'success');
        this.cargarVehiculos();
        this.resetFormulario();
      },
      (error) => {
        Swal.fire('Error', 'Error al agregar el vehículo', 'error');
        console.error(error);
      }
    );
  }

  editarVehiculo(vehiculo: any) {
    // Asigna el formulario con el formato esperado para edición
    this.vehiculoForm = {
      id: vehiculo.id,
      tipoVehiculo: vehiculo.tipoVehiculo,
      placa: vehiculo.placa,
      persona: { id: this.userId || '' }
    };
    this.editando = true;
  }

  actualizarVehiculo() {
    this.http.put<any>(`${this.apiUrl}/update/${this.vehiculoForm.id}`, this.vehiculoForm, { headers: this.getAuthHeaders() }).subscribe(
      (data) => {
        Swal.fire('Éxito', 'Vehículo actualizado con éxito', 'success');
        this.cargarVehiculos();
        this.resetFormulario();
      },
      (error) => {
        Swal.fire('Error', 'Error al actualizar el vehículo', 'error');
        console.error(error);
      }
    );
  }

  eliminarVehiculo(id: string) {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "No podrás revertir esto",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#911590',
      cancelButtonColor: '#7a1779',
      confirmButtonText: 'Sí, eliminarlo'
    }).then((result) => {
      if (result.isConfirmed) {
        this.http.delete<any>(`${this.apiUrl}/delete/${id}`, { headers: this.getAuthHeaders() }).subscribe(
          (data) => {
            Swal.fire('Eliminado', 'Vehículo eliminado con éxito', 'success');
            this.cargarVehiculos();
          },
          (error) => {
            Swal.fire('Error', 'Error al eliminar el vehículo', 'error');
            console.error(error);
          }
        );
      }
    });
  }

  resetFormulario() {
    this.vehiculoForm = { placa: '', tipoVehiculo: '', persona: { id: this.userId || '' } };
    this.editando = false;
  }
}
