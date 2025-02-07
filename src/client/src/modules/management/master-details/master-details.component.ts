import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { IconDefinition } from '@fortawesome/angular-fontawesome';
import { faCancel, faSave } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-master-details',
  standalone: true,
  imports: [],
  templateUrl: './master-details.component.html',
  styleUrl: './master-details.component.css'
})
export class MasterDetailsComponent<T> {

  @Input('isEdit') isEdit: boolean = false;
  @Input('dataEdit') dataEdit: T | undefined;

  @Output() cancel: EventEmitter<void> = new EventEmitter<void>();

  public form!: FormGroup;
  public faCancel: IconDefinition = faCancel;
  public faSave: IconDefinition = faSave;
}
