import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faYoutube, faFacebook, faTwitter, IconDefinition } from '@fortawesome/free-brands-svg-icons';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [RouterLink, FontAwesomeModule, FormsModule, CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {

  public faYoutube: IconDefinition = faYoutube;
  public faFacebook: IconDefinition = faFacebook;
  public faTwitter: IconDefinition = faTwitter;

  public email: string = '';

  public onSubmit(): void {
    console.log('Subscribe success!', this.email);
  }

}