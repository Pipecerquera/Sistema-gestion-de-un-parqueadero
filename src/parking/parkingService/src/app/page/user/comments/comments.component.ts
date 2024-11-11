import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2'; // Importa SweetAlert2 para mostrar mensajes

@Component({
  selector: 'app-comments',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  comments: any[] = [];
  newComment: string = '';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadComments();
  }

  // Función para validar si una cadena es base64
  isValidBase64(str: string): boolean {
    const base64Regex = /^data:image\/[a-zA-Z]+;base64,[A-Za-z0-9+/]+={0,2}$/;
    return base64Regex.test(str);
  }

  loadComments() {
    const token = localStorage.getItem('token');
    if (token) {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });

      this.http.get<any[]>('http://localhost:9000/api/comment/all', { headers }).subscribe(
        (data) => {
          this.comments = data.map(comment => {
            // Verificar si la imagen es base64 válida, si no, establecer como null
            if (!this.isValidBase64(comment.persona.photo)) {
              comment.persona.photo = null;
            }
            return comment;
          });
        },
        (error) => {
          Swal.fire('Error', 'Error al cargar los comentarios', 'error');
          console.error('Error al cargar los comentarios:', error);
        }
      );
    }
  }

  // Función para enviar un nuevo comentario
  submitComment() {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    if (token && this.newComment.trim()) {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      });

      const newCommentData = {
        commentary: this.newComment,
        persona: {
          id: userId
        },
      };

      this.http.post<any>('http://localhost:9000/api/comment/add', newCommentData, { headers }).subscribe(
        (response) => {
          Swal.fire('Éxito', 'Comentario enviado con éxito', 'success');
          this.loadComments();
          this.newComment = ''; // Limpiar el campo del comentario
        },
        (error) => {
          Swal.fire('Error', 'Error al enviar el comentario', 'error');
          console.error('Error al enviar el comentario:', error);
        }
      );
    } else {
      Swal.fire('Error', 'El comentario no puede estar vacío', 'error');
    }
  }
}
