import { Component, EventEmitter, Inject, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { ROLE_SERVICE } from '../../../../constants/injection.constant';
import { IRoleService } from '../../../../services/role/role-service.interface';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { faCancel, faSave, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RoleMasterDto } from '../../../../models/role/role-master-dto.model';

@Component({
  selector: 'app-role-details',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule],
  templateUrl: './role-details.component.html',
  styleUrl: './role-details.component.css'
})
export class RoleDetailsComponent implements OnChanges {

  @Input('isEdit') isEdit: boolean = false;
  @Input('dataEdit') dataEdit: RoleMasterDto | undefined;

  @Output() cancel: EventEmitter<void> = new EventEmitter<void>();

  public form!: FormGroup;
  public faCancel: IconDefinition = faCancel;
  public faSave: IconDefinition = faSave;

  constructor(@Inject(ROLE_SERVICE) private roleService: IRoleService) { }

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
