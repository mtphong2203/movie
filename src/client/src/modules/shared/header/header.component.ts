import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AUTH_SERVICE } from '../../../constants/injection.constant';
import { IAuthService } from '../../../services/auth/auth-service.interface';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faUser, IconDefinition } from '@fortawesome/free-solid-svg-icons';

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
  public isShow: boolean = false;

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

  public onShow(): void {
    setTimeout(() => {
      this.isShow = true;
    }, 200);
  }

  public onHide(): void {
    setTimeout(() => {
      this.isShow = false;
    }, 200);
  }

}
