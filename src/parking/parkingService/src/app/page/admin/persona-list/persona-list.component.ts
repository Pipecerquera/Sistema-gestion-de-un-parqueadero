import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-persona-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './persona-list.component.html',
  styleUrls: ['./persona-list.component.css']
})
export class PersonaListComponent implements OnInit {
  personas: any[] = [];
  isAddingUser = false;
  newUser = { fullname: '', email: '', password: '', role: 'User', locate: '', notifications: false };
  private apiUrl = 'http://localhost:9000/api/auth';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchPersonas();
  }

  // Configurar headers con token
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Cargar lista de personas
  fetchPersonas(): void {
    this.http.get<any[]>(`${this.apiUrl}/all`, { headers: this.getAuthHeaders() }).subscribe(
      (data) => {
        this.personas = data;
      },
      (error) => {
        console.error('Error al cargar personas:', error);
      }
    );
  }

  // Agregar usuario
  addUser(): void {
    this.http.post(`${this.apiUrl}/add`, this.newUser, { headers: this.getAuthHeaders() }).subscribe(
      (response) => {
        Swal.fire('Usuario creado', 'Usuario agregado exitosamente', 'success');
        this.isAddingUser = false;
        this.newUser = { fullname: '', email: '', password: '', role: 'User', locate: '', notifications: false };
        this.fetchPersonas();
      },
      (error) => {
        console.error('Error al agregar usuario:', error);
        Swal.fire('Error', 'No se pudo agregar el usuario', 'error');
      }
    );
  }

  // Eliminar usuario
  deleteUser(userId: number): void {
    this.http.delete(`${this.apiUrl}/delete/${userId}`, { headers: this.getAuthHeaders() }).subscribe(
      () => {
        Swal.fire('Eliminado', 'Usuario eliminado con éxito', 'success');
        this.fetchPersonas();
      },
      (error) => {
        console.error('Error al eliminar usuario:', error);
        Swal.fire('Error', 'No se pudo eliminar el usuario', 'error');
      }
    );
  }

  toggleAddUser(): void {
    this.isAddingUser = !this.isAddingUser;
  }
}
