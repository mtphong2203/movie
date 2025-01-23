import { Component, Inject } from '@angular/core';
import { MasterListComponent } from '../master-list/master-list.component';
import { UserMasterDto } from '../../../models/user/user-master-dto.model';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { USER_SERVICE } from '../../../constants/injection.constant';
import { Column } from '../../../models/common/column.model';
import { ResponseData } from '../../../models/response.model';
import { IUserService } from '../../../services/user/user-service.interface';
import { TableComponent } from "../../../core/components/table/table.component";
import { AccountDetailsComponent } from "./account-details/account-details.component";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { SelectMultipleComponent } from "../../../core/controls/select-multiple/select-multiple.component";

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [TableComponent, AccountDetailsComponent, ReactiveFormsModule, FontAwesomeModule, CommonModule, SelectMultipleComponent],
  templateUrl: './account-list.component.html',
  styleUrl: './account-list.component.css'
})
export class AccountListComponent extends MasterListComponent<UserMasterDto> {
  public columns: Column[] = [
    { name: 'firstName', title: 'FirstName' },
    { name: 'lastName', title: 'LastName' },
    { name: 'username', title: 'Username' },
    { name: 'email', title: 'Email' },
    { name: 'gender', title: 'Gender' },
    { name: 'phoneNumber', title: 'Phone' },
    { name: 'address', title: 'Address' },
    { name: 'dateOfBirth', title: 'Birth' },
    { name: 'active', title: 'Active' },
  ]

  public genderTypes: any[] = [
    { id: 1, name: 'MAN' },
    { id: 2, name: 'WOMAN' },
    { id: 3, name: 'OTHER' },
  ]

  constructor(@Inject(USER_SERVICE) private readonly userService: IUserService) { super() }

  ngOnInit(): void {
    this.createForm();
    this.search();
  }

  private createForm(): void {
    this.form = new FormGroup({
      keyword: new FormControl('', Validators.maxLength(50)),
      phoneNumber: new FormControl('', Validators.maxLength(12)),
      genderType: new FormControl([], Validators.maxLength(12))
    });
  }

  private search(): void {

    const param: any = {
      keyword: this.form.value.keyword,
      phoneNumber: this.form.value.phoneNumber,
      gender: this.form.value.genderType,
      page: this.currentPage,
      size: this.currentPageSize
    }
    console.log(this.form.value.genderType);
    this.userService.search(param).subscribe((result: ResponseData<UserMasterDto>) => {
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
      this.userService.delete(id).subscribe((result: boolean) => {
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
