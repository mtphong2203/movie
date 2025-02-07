import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ROLE_SERVICE } from '../../../constants/injection.constant';
import { IRoleService } from '../../../services/role/role-service.interface';
import { CommonModule } from '@angular/common';
import { RoleDetailsComponent } from "./role-details/role-details.component";
import { TableComponent } from "../../../core/components/table/table.component";
import { Column } from '../../../models/common/column.model';
import { RoleMasterDto } from '../../../models/role/role-master-dto.model';
import { ResponseData } from '../../../models/response.model';
import { MasterListComponent } from '../master-list/master-list.component';

@Component({
  selector: 'app-role-list',
  standalone: true,
  imports: [ReactiveFormsModule, FontAwesomeModule, CommonModule, RoleDetailsComponent, TableComponent],
  templateUrl: './role-list.component.html',
  styleUrl: './role-list.component.css'
})
export class RoleListComponent extends MasterListComponent<RoleMasterDto> implements OnInit {

  public columns: Column[] = [
    { name: 'name', title: 'Name' },
    { name: 'description', title: 'Description' },
    { name: 'active', title: 'Active' },
  ]

  constructor(@Inject(ROLE_SERVICE) private roleService: IRoleService) { super() }

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
      page: this.currentPage,
      size: this.currentPageSize
    }
    this.roleService.search(param).subscribe((result: ResponseData<RoleMasterDto>) => {
      if (result) {
        this.data = result.data;
        this.pageInfo = result.page;
      }
    });
  }

  public onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    this.currentPage = 0;
    this.search();
  }


  public onDelete(id: string): void {
    const confirmDelete = confirm("Are you sure want to delete this");
    if (confirmDelete) {
      this.roleService.delete(id).subscribe((result) => {
        if (result) {
          this.search();
        }
      });
    }
  }

  public onCreate(): void {
    this.isShow = true;
    this.isEdit = false;
  }

  public onEdit(id: string): void {
    this.isShow = true;
    this.isEdit = true;
    this.dataEdit = this.data.find((item) => item.id === id);
  }


  public onCancelDetail(): void {
    this.isShow = false;
    this.search();
  }

  public onChangeSize(size: any) {
    this.currentPageSize = size.target.value;
    this.currentPage = 0;
    this.search();
  }

  public onChangeNumber(number: number): void {
    if (this.currentPage < 0 || this.currentPage - 1 > this.pageInfo!.totalPages) {
      return;
    }
    this.currentPage = number;
    this.search();
  }

}
