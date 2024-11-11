import { Component } from '@angular/core';
import { SidenavComponent } from "../sidenav/sidenav.component";
import { BodyComponent } from "../body/body.component";

interface SideNavToggle {
  screenWidth: number;
  collapsed: boolean;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [SidenavComponent, BodyComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})

export class DashboardComponent {

  isSideNavCollapsed = false;
  screenWidth = 0;

  onToggleSideNav(data: SideNavToggle): void {
    this.screenWidth = data.screenWidth;
    this.isSideNavCollapsed = data.collapsed;
  }

}
