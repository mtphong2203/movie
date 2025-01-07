import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AUTH_SERVICE } from '../../../constants/injection.constant';
import { IAuthService } from '../../../services/auth/auth-service.interface';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faPowerOff, faUser, IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule, FontAwesomeModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  public isAuthenticated: boolean = false;

  public userInformation: any;

  public faUser: IconDefinition = faUser;
  public faLogout: IconDefinition = faPowerOff;

  constructor(@Inject(AUTH_SERVICE) private authService: IAuthService, private router: Router) {
    this.authService.isAuthenticated().subscribe((res) => {
      this.isAuthenticated = res;
    });
    this.authService.getUserInformation().subscribe((res: any) => {
      this.userInformation = res;
    })
  }

  public logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

}
