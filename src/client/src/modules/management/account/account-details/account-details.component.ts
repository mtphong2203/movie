import { Component, Inject, SimpleChanges } from '@angular/core';
import { MasterDetailsComponent } from '../../master-details/master-details.component';
import { UserMasterDto } from '../../../../models/user/user-master-dto.model';
import { ROLE_SERVICE, USER_SERVICE } from '../../../../constants/injection.constant';
import { IUserService } from '../../../../services/user/user-service.interface';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IRoleService } from '../../../../services/role/role-service.interface';
import { RoleMasterDto } from '../../../../models/role/role-master-dto.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-account-details',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule, CommonModule],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.css'
})
export class AccountDetailsComponent extends MasterDetailsComponent<UserMasterDto> {

  public roles: RoleMasterDto[] = [];
  public response: string = '';

  constructor(@Inject(USER_SERVICE) private userService: IUserService, @Inject(ROLE_SERVICE) private roleService: IRoleService) {
    super();
    this.roleService.getAll().subscribe((result) => {
      this.roles = result;
    })
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
      roleName: new FormControl(''),
      active: new FormControl(false),
    });
  }

  private patchValue(): void {
    if (this.isEdit && this.dataEdit != null) {
      this.form.patchValue({
        ...this.dataEdit,
        roleName: Array.isArray(this.dataEdit.role) ? this.dataEdit.role[0] : this.dataEdit.role || ''
      });

    }
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      this.response = 'This attribute is required';
      setTimeout(() => {
        this.response = '';
      }, 5000);
    }
    const data = this.form.value;
    debugger;
    if (this.isEdit && this.dataEdit != null) {
      this.userService.updateInformation(this.dataEdit.id, data).subscribe((result: UserMasterDto) => {
        if (result) {
          this.cancel.emit();
        }
      });
    } else {
      console.log(this.form.value);
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
