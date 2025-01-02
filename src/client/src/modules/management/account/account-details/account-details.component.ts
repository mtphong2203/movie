import { Component, Inject, SimpleChanges } from '@angular/core';
import { MasterDetailsComponent } from '../../master-details/master-details.component';
import { UserMasterDto } from '../../../../models/user/user-master-dto.model';
import { USER_SERVICE } from '../../../../constants/injection.constant';
import { IUserService } from '../../../../services/user/user-service.interface';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-account-details',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.css'
})
export class AccountDetailsComponent extends MasterDetailsComponent<UserMasterDto> {

  constructor(@Inject(USER_SERVICE) private userService: IUserService) {
    super();
  }

  ngOnChanges(changes: SimpleChanges): void {
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
      active: new FormControl(false),
    });
  }

  private patchValue(): void {
    if (this.isEdit && this.dataEdit != null) {
      this.form.patchValue(this.dataEdit);
    }
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    const data = this.form.value;
    if (this.isEdit && this.dataEdit != null) {
      this.userService.update(this.dataEdit.id, data).subscribe((result: UserMasterDto) => {
        if (result) {
          this.cancel.emit();
        }
      });
    } else {
      this.userService.create(data).subscribe((result: UserMasterDto) => {
        if (result) {
          this.cancel.emit();
        }
      });
    }
  }

  public onCancel(): void {
    this.cancel.emit();
  }

}
