import { Component, Inject, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faRegistered, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { AUTH_SERVICE } from '../../../constants/injection.constant';
import { IAuthService } from '../../../services/auth/auth-service.interface';
import { RegisterResponse } from '../../../models/auth/register-response.model';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, FontAwesomeModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {

  public form!: FormGroup;

  public faRegister: IconDefinition = faRegistered;

  constructor(@Inject(AUTH_SERVICE) private authService: IAuthService, private router: Router) { }

  ngOnInit(): void {
    this.createForm();
  }

  private createForm(): void {
    this.form = new FormGroup({
      firstName: new FormControl('', Validators.maxLength(50)),
      lastName: new FormControl('', Validators.maxLength(50)),
      username: new FormControl('', Validators.required),
      gender: new FormControl(null, Validators.required),
      phoneNumber: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      email: new FormControl('', [Validators.required, Validators.maxLength(25)]),
      address: new FormControl('', [Validators.maxLength(70)]),
      dateOfBirth: new FormControl(null),
      password: new FormControl('', Validators.required),
      confirmPassword: new FormControl('', Validators.required),
    });
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    const data = this.form.value;
    this.authService.register(data).subscribe((result: RegisterResponse) => {
      if (result) {
        this.router.navigate(['/auth/login']);
      }
    });
  }


}
