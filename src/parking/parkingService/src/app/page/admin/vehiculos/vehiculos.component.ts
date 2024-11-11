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
  personas: any[] = [];
  tiposVehiculo: string[] = ['Autobús', 'Motocicleta', 'Carro Particular'];
  vehiculoForm: any = { placa: '', tipoVehiculo: '', persona: { id: '' } };
  editando: boolean = false;
  apiUrl = 'http://localhost:9000/api/vehiculo';
  personasUrl = 'http://localhost:9000/api/auth/all';
  token: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('token');
    this.cargarVehiculos();
    this.cargarPersonas();
  }

  private getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${this.token || ''}`
    });
  }

  cargarVehiculos() {
    this.http.get<any[]>(`${this.apiUrl}/all`, { headers: this.getAuthHeaders() }).subscribe(
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

  cargarPersonas() {
    this.http.get<any[]>(this.personasUrl, { headers: this.getAuthHeaders() }).subscribe(
      (data) => {
        this.personas = data.map((persona) => ({
          id: persona.id,
          fullname: persona.fullname
        }));
      },
      (error) => {
        Swal.fire('Error', 'Error al cargar los nombres de personas', 'error');
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
    this.vehiculoForm = {
      id: vehiculo.id,
      tipoVehiculo: vehiculo.tipoVehiculo,
      placa: vehiculo.placa,
      persona: { id: vehiculo.personaId }
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
    this.vehiculoForm = { placa: '', tipoVehiculo: '', persona: { id: '' } };
    this.editando = false;
  }
}
