import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ROLE_SERVICE } from '../../../constants/injection.constant';
import { IRoleService } from '../../../services/role/role-service.interface';
import { CommonModule } from '@angular/common';
import { faPlus, faSearch, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { RoleDetailsComponent } from "./role-details/role-details.component";
import { catchError, of } from 'rxjs';
import { TableComponent } from "../../../core/components/table/table.component";
import { Column } from '../../../models/common/column.model';
import { RoleMasterDto } from '../../../models/role/role-master-dto.model';
import { ResponseData } from '../../../models/response.model';

@Component({
  selector: 'app-role-list',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule, CommonModule, RoleDetailsComponent, TableComponent],
  templateUrl: './role-list.component.html',
  styleUrl: './role-list.component.css'
})
export class RoleListComponent implements OnInit {

  public form!: FormGroup;

  public columns: Column[] = [
    { name: 'name', title: 'Name' },
    { name: 'description', title: 'Description' },
    { name: 'active', title: 'Active' },
  ]

  public faSearch: IconDefinition = faSearch;
  public faPlus: IconDefinition = faPlus;

  public response: string = '';

  public isShow: boolean = false;
  public isEdit: boolean = false;

  public data: RoleMasterDto[] = [];
  public dataEdit: RoleMasterDto | undefined;

  constructor(@Inject(ROLE_SERVICE) private roleService: IRoleService) { }

  ngOnInit(): void {
    this.createForm();
    this.search();
  }

  private createForm(): void {
    this.form = new FormGroup({
      keyword: new FormControl('', Validators.maxLength(50))
    });
  }

  private search(): void {
    const param: any = {
      keyword: this.form.value.keyword,
    }
    this.roleService.search(param).subscribe((result: ResponseData<RoleMasterDto>) => {
      if (result) {
        this.data = result.data;
      }
    });
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    this.search();
  }

  public onEdit(id: string): void {
    this.isShow = true;
    this.isEdit = true;
    this.dataEdit = this.data.find((item) => item.id === id);
  }

  public onDelete(id: string): void {
    const confirmDelete = confirm("Are you sure want to delete this");
    if (confirmDelete) {
      this.roleService.delete(id).pipe(
        catchError(() => {
          this.response = 'Role has been set to user , can not delete!';
          return of(null);
        })
      ).subscribe((result) => {
        if (result) {
          this.response = 'Deleted Successfully!';
          this.search();
        }
      });

    }
  }

  public onCreate(): void {
    this.isShow = true;
    this.isEdit = false;
  }

  public onCancelDetail(): void {
    this.isShow = false;
    this.search();
  }

}
