import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faCancel, faRotateLeft, faSave, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { MOVIE_SERVICE } from '../../../../constants/injection.constant';
import { IMovie } from '../../../../services/movie/movie.interface';
import { MovieMasterDTO } from '../../../../models/movie/movie-master-dto.model';

type MovieMasterDTOOrNull = MovieMasterDTO | null | undefined;
@Component({
  selector: 'app-movie-details',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule, CommonModule],
  templateUrl: './movie-details.component.html',
  styleUrl: './movie-details.component.css'
})
export class MovieDetailsComponent implements OnInit{
  //PROPERTIES
  // properties for formGroup - ReactiveForm
  public formEditCreate!: FormGroup;

  // propertiy to definite type of form is edit form or create form for display name of form
  public isEdit: any = false;

  // icon properties
  public faCancel: IconDefinition = faCancel;
  public faRotateLeft: IconDefinition = faRotateLeft;
  public faSave: IconDefinition = faSave;

  // input
  private selectItem!: MovieMasterDTOOrNull;
  @Input('selected-item') set selectedItem(value: MovieMasterDTOOrNull) {
    if (value != null) {
      this.selectItem = value;
      this.isEdit = true;
      this.updatedForm();
    } else {
      if (this.formEditCreate) {
        this.formEditCreate.reset();
      }
      this.isEdit = false;
    }
  }

  get selectedItem(): MovieMasterDTOOrNull {
    return this.selectItem;
  }

  // Output
  @Output() cancel: EventEmitter<any> = new EventEmitter<any>();

  // CONSTRUCTOR
  constructor(@Inject(MOVIE_SERVICE) private movieService: IMovie) {}

  //METHODS
  ngOnInit(): void {
    this.createForm();
    this.updatedForm();
  }

  updatedForm() {
    if (this.formEditCreate && this.selectedItem) {
      this.formEditCreate.patchValue(this.selectedItem)
    }
  }

  // updatedForm() {
  //   if (this.formEditCreate && this.selectedItem) {
  //     const patchedItem = { ...this.selectedItem };
  //     patchedItem.fromDate = this.formatDateForInput(this.selectedItem.fromDate);
  //     patchedItem.toDate = this.formatDateForInput(this.selectedItem.toDate);
  //     this.formEditCreate.patchValue(patchedItem);
  //   }
  // }

  private createForm(): void {
    this.formEditCreate = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.min(2), Validators.max(50)]),
      version: new FormControl('', [Validators.required, Validators.min(1), Validators.max(10)]),
      actor: new FormControl('', [Validators.required, Validators.min(2), Validators.max(50)]),
      director: new FormControl('', [Validators.required, Validators.min(2), Validators.max(50)]),
      content: new FormControl('', [Validators.required, Validators.min(10), Validators.max(1000)]),
      duration: new FormControl('', [Validators.required, Validators.min(10), Validators.max(200)]),
      fromDate: new FormControl('', [Validators.required]),
      toDate: new FormControl('', [Validators.required]),
      movieCompany: new FormControl('', [Validators.required, Validators.min(2), Validators.max(50)]),
      themeImage: new FormControl('', [Validators.required]),
      active: new FormControl(true)
    });
  }

  // Convert dd/MM/yyyy to yyyy-MM-dd for backend
  // private formatDateForBackend(dateString: string): string {
  //   const [day, month, year] = dateString.split('/').map(Number);
  //   return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
  // }

  // Convert yyyy-MM-dd to dd/MM/yyyy for input fields
  // private formatDateForInput(dateString: string): string {
  //   if (!dateString) return '';
  //   const [year, month, day] = dateString.split('-');
  //   return `${day}/${month}/${year}`;
  // }
  
  public onSubmit(): void {
    if (this.formEditCreate.invalid) {
      return;
    }

    const data = this.formEditCreate.value;

    if (this.isEdit && this.selectItem != null) {
      this.movieService.update(this.selectItem?.id, data).subscribe((result: MovieMasterDTO) => {
        if (result) {
          console.log('Update Success');
          this.cancel.emit();
        } else {
          console.log('Update failed');
        }
      });
    }
    this.movieService.create(data).subscribe((result: MovieMasterDTO) => {
      console.log(result);
      if (result != null) {
        this.cancel.emit();
      }
    });
  }

  public onCancel(): void {
    this.cancel.emit();
  }
}
