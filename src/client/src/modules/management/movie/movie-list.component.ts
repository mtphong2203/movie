import { Component, Inject, OnInit } from '@angular/core';
import { TableComponent } from '../../../core/components/table/table.component';
import { MovieDetailsComponent } from './movie-details/movie-details.component';
import { faEdit, faPlus, faSearch, faTrashCan, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { MOVIE_SERVICE } from '../../../constants/injection.constant';
import { IMovie } from '../../../services/movie/movie.interface';
import { MovieMasterDTO } from '../../../models/movie/movie-master-dto.model';
import { PageInfo, ResponseData } from '../../../models/response.model';

@Component({
  selector: 'app-movie-list',
  standalone: true,
  imports: [TableComponent, MovieDetailsComponent, ReactiveFormsModule, FontAwesomeModule, CommonModule],
  templateUrl: './movie-list.component.html',
  styleUrl: './movie-list.component.css'
})
export class MovieListComponent implements OnInit{
  // PROPERTIES
  // property for show detail
  public isShowDetails: boolean = false;

  // property for edit movie
  public selectedItem!: MovieMasterDTO | null | undefined;

  // pagination properties
  public currentPageNumber: number = 0;
  public currentPageSize: number = 10;
  public pageSizeList: number[] = [10, 20, 50, 100];
  public pageLimit: number = 2;
  public pageInfo!: PageInfo;

  // fa-icon properties
  public faPlus: IconDefinition = faPlus;
  public faSearch: IconDefinition = faSearch;
  public faEdit: IconDefinition = faEdit;
  public faTrashCan: IconDefinition = faTrashCan;

  // Reactive Form properties
  public searchForm!: FormGroup;

  // Data get form API
  public data!: MovieMasterDTO[];

  // ConfigColums for table component
  public configsColumn: any[] = [
    {name: 'name', title: 'MovieName'},
    {name: 'formDate', title: 'FromDate'},
    {name: 'movieCompany', title: 'MovieCompany'},
    {name: 'duration', title: 'Duration'},
    {name: 'version', title: 'Version'},
  ]

  // CONSTRUCTOR
  constructor(@Inject(MOVIE_SERVICE) private movieSevice: IMovie) {}

  // METHODS
  ngOnInit(): void {
    this.createForm();
    this.search();
  }

  // search method with pagination
  private search(): void {
    const params: any = {
      keyword: this.searchForm.value.keyword,
      page: this.currentPageNumber,
      size: this.currentPageSize,
    };
    this.movieSevice.search(params).subscribe((result: ResponseData<MovieMasterDTO>) => {
      // Chi gan data._embedded.movieMasterDTOList cho data
      this.data = result.data
      // Update pagination properties
      this.pageInfo = result.page;
    });
  }

  private createForm(): void {
    this.searchForm = new FormGroup({
      keyword: new FormControl('', [Validators.maxLength(255)])
    });
  }

  // searchForm method
  public onSubmit() {
    if (this.searchForm.invalid) {
      return;
    }
    // call search method when click submit button and submit method is called
    this.search();
  }

  // delete method for movie item
  public onDelete(id: string): void {
    this.movieSevice.delete(id).subscribe((result: boolean) => {
      if (result) {
        // If return result, call search method to load
        this.search();
        console.log('Delete successful');
      } else {
        console.log('Delete failed');
      }
    });
  }

  // Edit method for moive item
  public onEdit(id: string): void {
    this.isShowDetails = false;
    this.selectedItem = this.data.find((item) => item.id === id);
    this.isShowDetails = true;
  }

  // Create method for movie item
  public onCreate(): void {
    this.isShowDetails = true;
    this.selectedItem = null;
  }

  // Cancel for movie-details
  public cancelDetail(): void {
    this.isShowDetails = false;
    this.search;
  }

  // Pagination methods
  // change page size method
  public onChangePageSize(pageSize: number) {
    this.currentPageSize = pageSize;
    this.search();
  }

  // change page number method
  public onChangePageNumber(pageNumber: number) {
    this.currentPageNumber = pageNumber;
    this.search();
  }
}
