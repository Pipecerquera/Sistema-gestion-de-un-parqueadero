import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [],
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css'] 
})
export class BoardComponent implements OnInit {

  ngOnInit(): void {
    if (!localStorage.getItem('firstLoad')) {
      localStorage.setItem('firstLoad', 'true');
      window.location.reload();
    }
  }
}
