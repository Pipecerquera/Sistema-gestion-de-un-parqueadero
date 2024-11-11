import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router, RouterLink, RouterModule, RouterOutlet } from '@angular/router';

interface Parking {
  id: string;
  nombre: string;
  capacidadMaxima: number;
  ocupados: number;
  latitud: number;
  longitud: number;
}

@Component({
  selector: 'app-parking',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterModule, RouterOutlet],
  templateUrl: './parking.component.html',
  styleUrls: ['./parking.component.css']
})
export class ParkingComponent implements OnInit {
  parkings: Parking[] = [];
  searchTerm: string = '';
  isModalOpen: boolean = false;
  selectedParking: Parking | null = null;
  selectedVehiculoId: number | null = null;
  selectedEspacioId: number | null = null;
  fechaEntrada: Date | null = null;
  fechaSalida: Date | null = null;
  vehiculos: any[] = [];
  espacios: any[] = [];

  constructor(
    private router: Router,
    private sanitizer: DomSanitizer,
    private http: HttpClient,
    private cdr: ChangeDetectorRef // Inyectar ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadVehiculos();
    this.loadEspacios();
    this.fetchParkings();
  }

  fetchParkings() {
    this.http.get<any[]>('http://localhost:9000/api/parking/all', { headers: this.getAuthHeaders() })
      .subscribe(
        (data) => {
          console.log('Parkings fetched:', data); // Para depuración

          // Mapear los datos recibidos a la estructura esperada
          this.parkings = data.map(parking => ({
            id: parking.id,
            nombre: parking.name, // Mapeamos 'name' a 'nombre'
            capacidadMaxima: parking.maxSpace, // Mapeamos 'maxSpace' a 'capacidadMaxima'
            ocupados: parking.spaces ? parking.spaces.length : 0, // Asignamos 0 si no hay ocupados
            latitud: parseFloat(parking.latitud), // Aseguramos que sea un número
            longitud: parseFloat(parking.longitud) // Aseguramos que sea un número
          }));

          this.cdr.detectChanges(); // Forzar la detección de cambios
        },
        (error) => {
          console.error('Error fetching parkings:', error);
          Swal.fire('Error', 'Error al obtener los parqueaderos', 'error');
        }
      );
  }


  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  openReservaModal(parking: Parking): void {
    this.selectedParking = parking;
    this.isModalOpen = true;
  }

  closeReservaModal(): void {
    this.isModalOpen = false;
    this.resetReservaForm();
    this.selectedParking = null;
  }

  resetReservaForm(): void {
    this.selectedVehiculoId = null;
    this.selectedEspacioId = null;
    this.fechaEntrada = null;
    this.fechaSalida = null;
  }

  get filteredParkings() {
    return this.parkings.filter(parking =>
      parking && parking.nombre && parking.nombre.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  getMapUrl(latitud: number, longitud: number): SafeResourceUrl {
    const url = `https://www.google.com/maps/embed/v1/view?key=AIzaSyDWmh4H4O1AqdP5-nzLJft-EdFo9m6TDk8&center=${latitud},${longitud}&zoom=15`;
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  loadVehiculos() {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.http.get<any[]>('http://localhost:9000/api/vehiculo/all', { headers: this.getAuthHeaders() })
        .subscribe(
          data => this.vehiculos = data,
          error => Swal.fire('Error', 'Error al cargar los vehículos', 'error')
        );
    }
  }

  verMasDetalles(parking: Parking): void {
    if (parking && parking.id) {
      console.log(`Redirigiendo a /parking-details/${parking.id}`);
      this.router.navigate(['/parking-details', parking.id]);
    } else {
      console.error('ID de parking no válido:', parking);
      Swal.fire('Error', 'No se pudo redirigir. ID de parking no válido.', 'error');
    }
  }

  loadEspacios() {
    this.http.get<any[]>('http://localhost:9000/api/space/all', { headers: this.getAuthHeaders() })
      .subscribe(
        data => this.espacios = data,
        error => Swal.fire('Error', 'Error al cargar los espacios', 'error')
      );
  }

  realizarReserva() {
    const reservaData = {
      vehiculoId: this.selectedVehiculoId,
      fechaReserva: this.fechaEntrada,
      fechaSalida: this.fechaSalida,
      spaceId: this.selectedEspacioId
    };

    this.http.post<any>('http://localhost:9000/api/reserva', reservaData, { headers: this.getAuthHeaders() })
      .subscribe(
        response => {
          if (response.status === 'success') {
            Swal.fire('Éxito', response.message, 'success');
            this.closeReservaModal();
          } else {
            Swal.fire('Error', 'No se pudo realizar la reserva', 'error');
          }
        },
        error => Swal.fire('Error', 'Error al realizar la reserva', 'error')
      );
  }
}
