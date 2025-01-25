import { CommonModule } from '@angular/common';
import { Component, HostListener, Inject, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AUTH_SERVICE } from '../../../constants/injection.constant';
import { IAuthService } from '../../../services/auth/auth-service.interface';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faBars, faChevronDown, faClose, faPowerOff, faUser, IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule, FontAwesomeModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  public isAuthenticated: boolean = false;
  public isShow: boolean = false;
  public brandLogo: string = './assets/images/brand-logo.jpg';
  public isShowProfileMenu: boolean = false;
  public isDropdownVisible: boolean = false;

  public currentRoute: string = '';

  public userInformation: any;

  public faUser: IconDefinition = faUser;
  public faLogout: IconDefinition = faPowerOff;
  public faHamburger: IconDefinition = faBars;
  public faClose: IconDefinition = faClose;
  public faDown: IconDefinition = faChevronDown;

  public menuItems: any[] = [
    { title: 'Home', route: '/', isRoute: '/' },
    { title: 'Management', route: '/manager', isRoute: 'manager' },
    { title: 'Schedule', route: '/schedule', isRoute: 'schedule' },
    { title: 'Ticket', route: '/ticket', isRoute: 'ticket' },
    { title: 'Promotion', route: '/promotion', isRoute: 'promotion' },
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
  ngOnInit(): void {
    this.handleResize(window.innerWidth);
  }


  @HostListener('window:resize', ['$event'])
  onResize(event: any): void {
    this.handleResize(event.target.innerWidth);
  }

  private handleResize(screenWidth: number): void {
    if (screenWidth > 768) {
      this.isShow = true;
    } else {
      this.isShow = false;
    }
  }

  //#region Public Methods

  public logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  public toggleShow(state: boolean): void {
    this.isShow = state;
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

  public toggleDropdown(state: boolean) {
    this.isDropdownVisible = state;
  }

  //#endregion

  //#region Private Methods

  //#endregion
}
