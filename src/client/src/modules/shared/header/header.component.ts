import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AUTH_SERVICE } from '../../../constants/injection.constant';
import { IAuthService } from '../../../services/auth/auth-service.interface';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  public isAuthenticated: boolean = false;

  public userInformation: any;

  constructor(@Inject(AUTH_SERVICE) private authService: IAuthService) {
    this.authService.isAuthenticated().subscribe((res) => {
      this.isAuthenticated = res;
    });
    this.authService.getUserInformation().subscribe((res: any) => {
      this.userInformation = res;
    })
  }

  public logout(): void {
    this.authService.logout();
  }

}
