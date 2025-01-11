import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faEnvelope, faPhone, faMapMarkedAlt } from '@fortawesome/free-solid-svg-icons';
import { faFacebook, faYoutube, faTiktok, faLinkedin } from '@fortawesome/free-brands-svg-icons';
@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [FontAwesomeModule, ReactiveFormsModule, CommonModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  // icon
  public faEmail = faEnvelope;
  public faPhone = faPhone;
  public faMap = faMapMarkedAlt;
  // brands
  public faceBookIcon = faFacebook;
  public youtubeIcon = faYoutube;
  public tiktokIcon = faTiktok;
  public linkedinIcon = faLinkedin;

  public isModal: boolean = false;
  public response: string = '';
  public form!: FormGroup

  constructor() {
    this.form = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.maxLength(20)]),
      email: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      message: new FormControl('', [Validators.maxLength(255), Validators.required]),
    });
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      this.response = 'This attribute is required';
      setTimeout(() => {
        this.response = '';
      }, 3000);
      this.isModal = false;
      return;
    }
    this.form.reset();
    this.isModal = true;
  }

  public closeModal(): void {
    this.isModal = false;
  }


}