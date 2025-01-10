import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowRight, faCircle, IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-about',
  standalone: true,
  imports: [CommonModule, RouterLink, FontAwesomeModule],
  templateUrl: './about.component.html',
  styleUrl: './about.component.css'
})
export class AboutComponent {

  public selectedTab: string = 'intro';

  public logoAbout: string = './assets/images/about-logo.png';
  public intro01: string = './assets/images/about-intro1.jpg';
  public intro02: string = './assets/images/about-intro2.jpg';
  public intro03: string = './assets/images/about-intro3.jpg';
  public intro04: string = './assets/images/contact-1.jpg';
  public intro05: string = './assets/images/contact-2.jpg';
  public intro06: string = './assets/images/contact-3.jpg';
  public service01: string = './assets/images/service-1.jpg';
  public service02: string = './assets/images/service-2.jpg';
  public service03: string = './assets/images/service-3.jpg';
  public food01: string = './assets/images/food-1.jpg';
  public food02: string = './assets/images/food-2.jpg';
  public food03: string = './assets/images/food-3.jpg';
  public weekend01: string = './assets/images/weekend-1.jpg';
  public weekend02: string = './assets/images/weekend-2.jpg';

  public faRight: IconDefinition = faArrowRight;
  public faCircle: IconDefinition = faCircle;

  public selectTab(event: string): void {
    this.selectedTab = event;
  }

}
