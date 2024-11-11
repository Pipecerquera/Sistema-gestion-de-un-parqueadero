import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthRoutingModule } from './auth-routing.module';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; 

@NgModule({
  imports: [
    CommonModule,
    FormsModule, // Asegúrate de que FormsModule esté aquí
    AuthRoutingModule, // Incluye el módulo de rutas si es necesario
    RouterModule.forChild([
      {
        path: 'login',
        loadComponent: () => import('./login/login.component').then(m => m.LoginComponent)
      }
    ]),
    BrowserAnimationsModule, // Agrega este módulo
  ],
})
export class AuthModule { }
