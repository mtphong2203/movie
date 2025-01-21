import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AUTH_SERVICE } from '../../../constants/injection.constant';
import { IAuthService } from '../../../services/auth/auth-service.interface';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faBars, faClose, faPowerOff, faUser, IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule, FontAwesomeModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  public isAuthenticated: boolean = false;
  public isShow: boolean = false;
  public brandLogo: string = './assets/images/brand-logo.jpg';
  public isShowProfileMenu: boolean = false;

  public currentRoute: string = '';

  public userInformation: any;

  public faUser: IconDefinition = faUser;
  public faLogout: IconDefinition = faPowerOff;
  public faHamburger: IconDefinition = faBars;
  public faClose: IconDefinition = faClose;
  public menuItems: any[] = [
    { title: 'Home', route: '/', isRoute: '/' },
    { title: 'Management', route: '/manager', isRoute: 'manager' },
    { title: 'Schedule', route: '/schedule', isRoute: 'schedule' },
    { title: 'Ticket', route: '/ticket', isRoute: 'ticket' },
    { title: 'Promotion', route: '/promotion', isRoute: 'promotion' },
    { title: 'About', route: '/about', isRoute: 'about' },
    { title: 'Contact', route: '/contact', isRoute: 'contact' }
  ]

  constructor(
    @Inject(AUTH_SERVICE) private readonly authService: IAuthService,
    private readonly router: Router) {
    this.authService.isAuthenticated().subscribe((res) => {
      this.isAuthenticated = res;
    });
    this.authService.getUserInformation().subscribe((res: any) => {
      this.userInformation = res;
    });
    this.router.events.subscribe(() => {
      this.currentRoute = router.url;
    })
  }

  //#region Public Methods

  public logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  public toggleShow(): void {
    this.isShow = !this.isShow;
  }

  public closeHamburger(): void {
    this.isShow = false;
  }

  public isRoute(route: string): boolean {
    if (route === '/') {
      return this.currentRoute === '/';
    }
    return this.currentRoute.includes(route);
  }

  public onMouseOver(): void {
    this.isShowProfileMenu = true;
  }

  public onMouseLeave(): void {
    this.isShowProfileMenu = false;
  }

  public onFocus(): void {
    this.isShowProfileMenu = true;
  }

  //#endregion

  //#region Private Methods

  //#endregion
}
