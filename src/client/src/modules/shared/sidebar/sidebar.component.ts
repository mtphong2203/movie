import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFilm, faShield, faTheaterMasks, faUser, IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule, FontAwesomeModule, CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {

  public userIcon: IconDefinition = faUser;
  public roleIcon: IconDefinition = faShield;
  public cinemaIcon: IconDefinition = faTheaterMasks;
  public movieIcon: IconDefinition = faFilm;

  public currentRoute: string = '';


  constructor(private router: Router) {
    // Theo dõi sự thay đổi URL
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentRoute = this.router.url; // Cập nhật route hiện tại
      }
    });
  }

  public isActive(route: string): boolean {
    return this.currentRoute.includes(route); // Kiểm tra route
  }


}
