import { Component, Inject, OnInit } from '@angular/core';
import { USER_SERVICE } from '../../../constants/injection.constant';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IUserService } from '../../../services/user/user-service.interface';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  public userInformation: any;
  public selectedTab: string = 'userInfo';

  public form!: FormGroup;

  public response: string = '';
  public showModal: boolean = false;

  constructor(@Inject(USER_SERVICE) private readonly userService: IUserService) {
  }
  ngOnInit(): void {
    this.createForm();
    this.patchValue();
  }

  private createForm(): void {
    this.form = new FormGroup({
      firstName: new FormControl('', Validators.maxLength(50)),
      lastName: new FormControl('', Validators.maxLength(50)),
      username: new FormControl('', [Validators.maxLength(50), Validators.required]),
      email: new FormControl('', [Validators.maxLength(50), Validators.required]),
      phoneNumber: new FormControl('', [Validators.maxLength(50), Validators.required]),
      address: new FormControl('', Validators.maxLength(500)),
      gender: new FormControl(null),
      dateOfBirth: new FormControl(null),
      password: new FormControl('', [Validators.maxLength(50), Validators.required]),
      confirmPassword: new FormControl('', [Validators.maxLength(50), Validators.required]),
    })
  }

  public patchValue(): void {
    const userRaw = localStorage.getItem('userDTO') || '';
    this.userInformation = JSON.parse(userRaw);
    this.form.patchValue(this.userInformation);
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      this.response = 'This attribute is required';
      setTimeout(() => {
        this.response = '';
      }, 3000);
      return;
    }
    const data = this.form.value;
    const updatedUserDTO = { ...this.userInformation, ...data };

    this.userService.update(this.userInformation.id, data).subscribe((res) => {
      if (res) {
        delete updatedUserDTO.password;
        delete updatedUserDTO.confirmPassword;
        localStorage.setItem('userDTO', JSON.stringify(updatedUserDTO));
        this.form.reset();
        this.patchValue();
        this.onShowModal();
      }
    })
  }

  public selectTab(tab: string) {
    this.selectedTab = tab;
  }

  private onShowModal(): void {
    setTimeout(() => {
      this.showModal = true;
    }, 100);
  }

  public close(): void {
    this.showModal = false;
  }

}
