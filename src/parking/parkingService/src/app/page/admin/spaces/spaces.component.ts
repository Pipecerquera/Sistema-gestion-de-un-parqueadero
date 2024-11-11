import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-spaces',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './spaces.component.html',
  styleUrls: ['./spaces.component.css']
})
export class SpacesComponent implements OnInit {
  spaces: any[] = [];
  newSpace: { relativeName: string, isOccupied: boolean, isReserved: boolean, tipoVehiculo: string } = {
    relativeName: '',
    isOccupied: false,
    isReserved: false,
    tipoVehiculo: 'moto' // Valor por defecto, puede ser moto, carro o bus
  };
  loading: boolean = false;
  error: string | null = null;

  private apiUrl = 'http://localhost:9000/api/space'; // Cambia la URL si es necesario

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getSpaces();
  }

  // Obtener los espacios existentes
  getSpaces() {
    this.loading = true;
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    this.http.get(`${this.apiUrl}/all`, { headers }).subscribe(
      (response: any) => {
        this.spaces = response || [];  // Asegura que siempre sea un array
        this.loading = false;
      },
      (error) => {
        this.error = 'Error al obtener los espacios';
        this.loading = false;
      }
    );
  }

  // Agregar un nuevo espacio
  addSpace() {
    if (this.newSpace.relativeName.trim()) {  // Asegurarse de que no esté vacío ni contenga solo espacios
      console.log('Espacio a agregar:', this.newSpace); // Verifica que relativeName no esté vacío

      const token = localStorage.getItem('token');
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

      this.http.post(`${this.apiUrl}/add`, this.newSpace, { headers }).subscribe(
        (response) => {
          this.getSpaces(); // Actualizar la lista de espacios
          this.newSpace = { relativeName: '', isOccupied: false, isReserved: false, tipoVehiculo: 'moto' }; // Limpiar el formulario
          this.error = null;
        },
        (error) => {
          this.error = 'Error al agregar el espacio';
        }
      );
    } else {
      this.error = 'El nombre del espacio es obligatorio';
    }
  }

  deleteSpace(id: number) {
    // Usar SweetAlert2 para la confirmación
    Swal.fire({
      title: '¿Estás seguro?',
      text: "Este espacio se eliminará permanentemente",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        const token = localStorage.getItem('token');
        const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
        // Realizar la solicitud DELETE a la API
        this.http.delete(`${this.apiUrl}/delete/${id}`, { headers }).subscribe(
          (response) => {
            Swal.fire('Eliminado!', 'El espacio ha sido eliminado correctamente.', 'success');
            this.getSpaces(); // Actualizar la lista de espacios
          },
          (error) => {
            Swal.fire('Error!', 'Hubo un problema al eliminar el espacio.', 'error');
          }
        );
      }
    });
  }
}
