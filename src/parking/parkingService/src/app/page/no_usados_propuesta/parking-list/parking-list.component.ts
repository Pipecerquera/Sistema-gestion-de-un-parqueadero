import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import Swal from 'sweetalert2';

interface Parking {
  id?: number;
  name: string; 
  maxSpace: number; 
  latitud: string;
  longitud: string; 
}

@Component({
  selector: 'app-parking-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './parking-list.component.html',
  styleUrls: ['./parking-list.component.css']
})
export class ParkingListComponent implements OnInit {
  parkings: Parking[] = [];
  newParking: Omit<Parking, 'id'> = { name: '', maxSpace: 0, latitud: '', longitud: '' };
  isCreateModalOpen: boolean = false;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.fetchParkings();
  }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  fetchParkings() {
    this.http.get<Parking[]>('http://localhost:9000/api/parking/all', { headers: this.getAuthHeaders() })
      .subscribe(
        (data) => {
          this.parkings = data;
        },
        (error) => {
          console.error('Error fetching parkings:', error);
          Swal.fire('Error', 'Error al obtener los parqueaderos', 'error');
        }
      );
  }

  openCreateModal() {
    this.isCreateModalOpen = true; 
  }

  closeCreateModal() {
    this.isCreateModalOpen = false; 
    this.resetNewParking();
  }

  resetNewParking() {
    this.newParking = { name: '', maxSpace: 0, latitud: '', longitud: '' };
  }

  createParking() {
    this.http.post<Parking>('http://localhost:9000/api/parking/add', this.newParking, { headers: this.getAuthHeaders() })
      .subscribe(
        (data) => {
          this.parkings.push(data);
          Swal.fire('Éxito', 'Parqueadero creado con éxito', 'success');
          this.closeCreateModal();
        },
        (error) => {
          console.error('Error creating parking:', error);
          Swal.fire('Error', 'Error al crear el parqueadero', 'error');
        }
      );
  }
}
