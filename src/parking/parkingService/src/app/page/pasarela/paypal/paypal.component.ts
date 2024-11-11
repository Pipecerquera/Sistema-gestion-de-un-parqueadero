import { Component, AfterViewInit } from '@angular/core';

declare global {
  interface Window {
    paypal: any;  // Definir el tipo de paypal en window como 'any' para evitar el error
  }
}

@Component({
  selector: 'app-paypal',
  standalone: true,
  templateUrl: './paypal.component.html',
  styleUrls: ['./paypal.component.css']
})
export class PaypalComponent implements AfterViewInit {

  constructor() {}

  ngAfterViewInit(): void {
    this.renderPaypalButton();
  }

  renderPaypalButton() {
    // Inicializar el botón de PayPal después de que el componente haya cargado
    if (window.paypal) {
      window.paypal.Buttons({
        createOrder: (data: any, actions: any) => {
          return actions.order.create({
            purchase_units: [{
              amount: {
                value: '50.00' // El valor de la transacción
              }
            }]
          });
        },
        onApprove: (data: any, actions: any) => {
          return actions.order.capture().then((details: any) => {
            alert('Pago completado por ' + details.payer.name.given_name);
          });
        },
        onError: (err: any) => {
          console.error("Error en el proceso de pago", err);
          alert("Hubo un error en el pago.");
        }
      }).render('#paypal-button-container');
    }
  }
}
