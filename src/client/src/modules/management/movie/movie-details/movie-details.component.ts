import { CommonModule, formatDate } from '@angular/common';
import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faCancel, faRotateLeft, faSave, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { CINEMAROOM_SERVICE, MOVIE_SERVICE, MOVIETYPE_SERVICE, SCHEDULE_SERVICE, SHOWDATE_SERVICE } from '../../../../constants/injection.constant';
import { IMovie } from '../../../../services/movie/movie.interface';
import { MovieMasterDTO } from '../../../../models/movie/movie-master-dto.model';
import { CinemaRoom } from '../../../../models/room/cinema-room.model';
import { MovieType } from '../../../../models/movietype/movie-type.model';
import { Schedule } from '../../../../models/schedule/schedule.model';
import { IMovietypeService } from '../../../../services/movietype/movietype.interface';
import { ICinemaRoomService } from '../../../../services/cinemaroom/cinemaroom.interface';
import { IScheduleService } from '../../../../services/schedule/schedule.interface';
import { MultiSelectComponent } from '../../../../core/components/multi-select/multi-select.component';
import { IShowdateService } from '../../../../services/showdate/showdate.interface';
import { ShowDateMasterDTO } from '../../../../models/showdate/showdate.model';

type MovieMasterDTOOrNull = MovieMasterDTO | null | undefined;
@Component({
  selector: 'app-movie-details',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule, CommonModule, MultiSelectComponent],
  templateUrl: './movie-details.component.html',
  styleUrl: './movie-details.component.css'
})
export class MovieDetailsComponent implements OnInit{
  //PROPERTIES
  // properties for formGroup - ReactiveForm
  public formEditCreate!: FormGroup;

  public formEditSchedule!: FormGroup;

  public isShowFormCreate: boolean = true;

  public isShowFormEditSchedule: boolean = false;

  // Dynamically generated list of dates
  dateList: string[] = [];

  // Available ShowDate
  public availableShowDates!: ShowDateMasterDTO[];

  // Cinema Room
  public cinemaRooms!: CinemaRoom[];

  // Movie Type
  public movieTypes!: MovieType[];

  // Schedule
  public schedules!: Schedule[];

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
      // Ensure getAllAvailableDates() is only called if movie ID exists
      if (this.selectItem?.id) {
        this.getAllAvailableDates();
      }
      this.isShowFormCreate = false;
      this.isShowFormEditSchedule = true;
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
  constructor(
    private formBuilder: FormBuilder, 
    @Inject(MOVIE_SERVICE) private movieService: IMovie, 
    @Inject(MOVIETYPE_SERVICE) private movieTypeService: IMovietypeService,
    @Inject(CINEMAROOM_SERVICE) private cinemaRoomService: ICinemaRoomService,
    @Inject(SCHEDULE_SERVICE) private scheduleService: IScheduleService,
    @Inject(SHOWDATE_SERVICE) private showDateService: IShowdateService
  ) {}

  //METHODS
  ngOnInit(): void {
    this.createForm();
    this.updatedForm();
    this.getAllMovieType();
    this.getAllSchedules();
    this.getAllCinemaRooms();
    this.getAllAvailableDates();
    
    // Watch for changes to fromDate and toDate
    // this.formEditCreate.get('fromDate')?.valueChanges.subscribe(() => this.updateDateCheckboxes());
    // this.formEditCreate.get('toDate')?.valueChanges.subscribe(() => this.updateDateCheckboxes());
  }

  // updateDateCheckboxes(): void {
  //   const fromDate = this.formEditCreate.get('fromDate')?.value;
  //   const toDate = this.formEditCreate.get('toDate')?.value;

    // if (!fromDate || !toDate) {
    //   this.clearDateCheckboxes();
    //   return;
    // }

    // const startDate = new Date(fromDate);
    // const endDate = new Date(toDate);

    // if (startDate > endDate) {
    //   this.clearDateCheckboxes();
    //   alert('From Date must be earlier than or equal to To Date');
    //   return;
    // }

  //   this.dateList = this.getDatesBetween(startDate, endDate);

  //   // Update FormArray
  //   const checkboxArray = this.formEditCreate.get('dateCheckboxes') as FormArray;
  //   checkboxArray.clear(); // Remove old checkboxes
  //   this.dateList.forEach(() => checkboxArray.push(new FormControl(false))); // Add new controls
  // }

    /**
   * Clear the checkboxes if dates are invalid.
   */
    // private clearDateCheckboxes(): void {
    //   this.dateList = [];
    //   const checkboxArray = this.formEditCreate.get('dateCheckboxes') as FormArray;
    //   checkboxArray.clear();
    // }
  
    /**
     * Get all dates between two dates.
     */
    // private getDatesBetween(startDate: Date, endDate: Date): string[] {
    //   const dates: string[] = [];
    //   let currentDate = new Date(startDate);
  
    //   while (currentDate <= endDate) {
    //     dates.push(formatDate(currentDate, 'dd-MM-yyyy', 'en-US'));
    //     currentDate.setDate(currentDate.getDate() + 1); // Move to the next day
    //   }
  
    //   return dates;
    // }

  updatedForm() {
    if (this.formEditSchedule && this.selectedItem) {
      this.formEditSchedule.patchValue(this.selectedItem)

      // Prepopulate FormArray with selected rooms
      // const formArray = this.formEditCreate.get('cinemaRoom') as FormArray;
      // formArray.clear();
      // if (this.selectedItem.cinemaRoom) {
      //   this.selectedItem.cinemaRoom.forEach((id: string) => {
      //     formArray.push(new FormControl(id));
      //   });
      // }
    }
  }

  private createForm(): void {
    this.formEditCreate = this.formBuilder.group({
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
      active: new FormControl(true),
      movieTypes: this.formBuilder.array([], Validators.required),
    });
    this.formEditSchedule = this.formBuilder.group({
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
      active: new FormControl(true),
      movieTypes: this.formBuilder.array([], Validators.required),
      availableShowDates: this.formBuilder.array([], Validators.required),
      cinemaRooms: [[], Validators.required],
      schedules: this.formBuilder.array([], Validators.required),
    });
  }

  public getAllAvailableDates(): void {
    if (!this.selectItem?.id) {
      console.warn('Invalid movie selection. Cannot fetch show dates.');
      this.availableShowDates = [];
      return;
    }
    this.showDateService.getAllAvailableShowDates(this.selectItem.id).subscribe({
      next: (result: ShowDateMasterDTO[] | null | undefined) => {
        this.availableShowDates = result ?? [];
      },
      error: (error) => {
        console.error('Error fetching available show dates:', error);
        this.availableShowDates = [];
      }
    });
    debugger;
  }

  // Get all cinemaRooms
  public getAllCinemaRooms(): void {
    this.cinemaRoomService.getAll().subscribe((result: CinemaRoom[]) => {
      this.cinemaRooms = result;

      // Initialize the FormArray with the fetched cinemaRooms
      // const formArray = this.formEditCreate.get('cinemaRooms') as FormArray;
      // formArray.clear();
      // this.cinemaRooms.forEach(() => formArray.push(new FormControl(false)));
    });
  }

  // Get all movietypes
  public getAllMovieType(): void {
    this.movieTypeService.getAll().subscribe((result: MovieType[]) => {
      this.movieTypes = result;
      
      // Initialize the FormArray with the fetched movie types
      const formArray = this.formEditCreate.get('movieTypes') as FormArray;
      formArray.clear(); // Clear any existing controls
      this.movieTypes.forEach(() => formArray.push(new FormControl(false))); // Add one control per movie type
    });
  }

  // Get all schedules
  public getAllSchedules(): void {
    this.scheduleService.getAll().subscribe((result: Schedule[]) => {
      this.schedules = result;

      // Initialize the FormArray with the fetch schedules
      const formArray = this.formEditSchedule.get('schedules') as FormArray;
      formArray.clear();
      this.schedules.forEach(() => formArray.push(new FormControl(false)));
    });
  }

  // Getter for easy access to the movieType FormArray
  get movieTypeFormArray(): FormArray {
    return this.formEditCreate.get('movieTypes') as FormArray;
  }

  // Getter for easy access to the movieType FormArray
  get showDatesFormArray(): FormArray {
    return this.formEditCreate.get('availableShowDates') as FormArray;
  }

  // Getter for easy access to the movieType FormArray
  get scheduleFormArray(): FormArray {
    return this.formEditSchedule.get('schedules') as FormArray;
  }

  // Get list dates and create array, then generate list checkbox of these days for choose
  
  public onSubmit(): void {
    if (this.formEditCreate.invalid) {
      return;
    }

    // Map the selected checkboxes to their corresponding movie type IDs
    const selectedMovieTypeIds = this.movieTypeFormArray.value
    .map((checked: boolean, index: number) => (checked ? this.movieTypes[index].id : null))
    .filter((id: string | null) => id !== null);

    const selectedShowDateIds = this.showDatesFormArray.value
    .map((checked: boolean, index: number) => (checked ? this.availableShowDates[index].id : null))
    .filter((id: string | null) => id !== null);

    // Map the selected checkboxes to their corresponding movie type IDs
    const selectedScheduleIds = this.scheduleFormArray.value
    .map((checked: boolean, index: number) => (checked ? this.schedules[index].id : null))
    .filter((id: string | null) => id !== null);

    const dataCreate = {
      ...this.formEditCreate.value,
      movieTypes: selectedMovieTypeIds, // Replace the FormArray values with selected IDs
    };

    const dataEdit = {
      ...this.formEditSchedule.value,
      schedules: selectedScheduleIds
    };
    // Edit movie
    if (this.isEdit && this.selectItem != null) {
      this.movieService.update(this.selectItem?.id, dataEdit).subscribe((result: MovieMasterDTO) => {
        if (result) {
          console.log('Update Success');
          this.cancel.emit();
        } else {
          console.log('Update failed');
        }
      });
    }
    // Create a new movie
    this.movieService.create(dataCreate).subscribe((result: MovieMasterDTO) => {
      console.log(result);
      if (result != null) {
        this.isShowFormCreate = false;
        this.isShowFormEditSchedule = true;
        this.selectItem = result;
        this.updatedForm();
        this.getAllAvailableDates();
      }
    });
  }

  public onCancel(): void {
    this.cancel.emit();
  }

  // public onRoomSelectionChange(event: Event): void {
  //   const selectElement = event.target as HTMLSelectElement;
  
  //   // Get selected options
  //   const selectedValues = Array.from(selectElement.selectedOptions).map(option => option.value);
  
  //   // Clear and update the FormArray
  // //   const formArray = this.formEditCreate.get('cinemaRoom') as FormArray;
  // //   formArray.clear();
  // //   selectedValues.forEach(value => formArray.push(new FormControl(value)));
  // // }
}
