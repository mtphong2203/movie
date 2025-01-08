import { Component, Inject, OnInit } from '@angular/core';
import { USER_SERVICE } from '../../../constants/injection.constant';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IUserService } from '../../../services/user/user-service.interface';
import { faClose, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FontAwesomeModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  public userInformation: any;
  public selectedTab: string = 'userInfo';

  public form!: FormGroup;

  public response: string = '';
  public showModal: boolean = false;
  public changePassword: boolean = false;
  public isCorrect: boolean = true;
  public faClose: IconDefinition = faClose;

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
      password: new FormControl('', Validators.minLength(5)),
      confirmPassword: new FormControl('', Validators.minLength(5)),
    })
  }

  public patchValue(): void {
    const userRaw = localStorage.getItem('userDTO') || '';
    this.userInformation = JSON.parse(userRaw);
    this.form.patchValue(this.userInformation);
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    const data = this.form.value;
    const updatedUserDTO = { ...this.userInformation, ...data };

    // update information APIs
    this.userService.updateInformation(this.userInformation.id, data).subscribe((res) => {
      if (res) {
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

  // modal update information
  private onShowModal(): void {
    setTimeout(() => {
      this.showModal = true;
    }, 100);
  }

  // close modal if change success
  public closeModal(): void {
    this.showModal = false;
  }

  // open modal change password
  public onChangePassword(): void {
    this.changePassword = true;

  }

  // validate value in modal change password
  public confirmChangePassword(): void {

    const password = this.form.value.password;
    const confirmPassword = this.form.value.confirmPassword;

    if (password == confirmPassword && password != '') {
      // update password APIs
      const data = this.form.value;
      console.log(data);
      const updatedUserDTO = { ...this.userInformation, ...data };
      this.userService.update(this.userInformation.id, data).subscribe((res) => {
        if (res) {
          localStorage.setItem('userDTO', JSON.stringify(updatedUserDTO));
          this.form.reset();
          this.patchValue();
          this.onShowModal();
        }
      })
      this.isCorrect = true;
    } else if (password == '' || confirmPassword == '') {
      this.response = 'Minimum is 5 characters';
      setTimeout(() => {
        this.response = '';
      }, 5000);
      this.isCorrect = false;
    } else {
      this.response = 'Password does not match';
      setTimeout(() => {
        this.response = '';
      }, 5000);
      this.isCorrect = false;
    }
  }

  // close modal change password if update success
  public closeModalChangePassword(): void {
    if (this.isCorrect) {
      this.changePassword = false;
    }
  }

  public onCloseModal(): void {
    this.changePassword = false;
  }

}
