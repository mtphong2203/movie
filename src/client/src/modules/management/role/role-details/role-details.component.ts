import { Component, Inject, OnChanges, SimpleChanges } from '@angular/core';
import { ROLE_SERVICE } from '../../../../constants/injection.constant';
import { IRoleService } from '../../../../services/role/role-service.interface';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RoleMasterDto } from '../../../../models/role/role-master-dto.model';
import { MasterDetailsComponent } from '../../master-details/master-details.component';

@Component({
  selector: 'app-role-details',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule],
  templateUrl: './role-details.component.html',
  styleUrl: './role-details.component.css'
})
export class RoleDetailsComponent extends MasterDetailsComponent<RoleMasterDto> implements OnChanges {

  constructor(@Inject(ROLE_SERVICE) private roleService: IRoleService) {
    super();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.createForm();
    this.patchValue();
  }

  private createForm(): void {
    this.form = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      description: new FormControl('', Validators.maxLength(500)),
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
      this.roleService.update(this.dataEdit.id, data).subscribe((result) => {
        if (result) {
          this.cancel.emit();
        }
      });
    } else {
      this.roleService.create(data).subscribe((result) => {
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
