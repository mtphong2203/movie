import { CommonModule } from '@angular/common';
import { Component, EventEmitter, input, Input, Output } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faAngleDoubleLeft, faAngleDoubleRight, faAngleLeft, faAngleRight, faEdit, faTrash, faTurkishLiraSign, IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [FontAwesomeModule, CommonModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent {

  @Input('data') data: any;
  @Input('columns') columns: any[] = [];
  @Input('currentPage') currentPage: number = 0;
  @Input('pageInfo') pageInfo: any;
  @Input('pageLimit') pageLimit!: number;
  @Input('pageSizes') pageSizes: number[] = [];

  @Output() edit: EventEmitter<string> = new EventEmitter<string>();
  @Output() delete: EventEmitter<string> = new EventEmitter<string>();
  @Output() changeSize: EventEmitter<number> = new EventEmitter<number>();
  @Output() changeNumber: EventEmitter<number> = new EventEmitter<number>();

  public faEdit: IconDefinition = faEdit;
  public faDelete: IconDefinition = faTrash;
  public faRight: IconDefinition = faAngleRight;
  public faDoubleRight: IconDefinition = faAngleDoubleRight;
  public faLeft: IconDefinition = faAngleLeft;
  public faDoubleLeft: IconDefinition = faAngleDoubleLeft;

  public onEdit(id: string): void {
    this.edit.emit(id);
  }

  public onDelete(id: string): void {
    this.delete.emit(id);
  }

  public getPageList(): number[] {
    const start = Math.max(0, this.pageInfo?.number - this.pageLimit);
    const end = Math.min(this.pageInfo?.totalPages - 1, this.pageInfo?.number + this.pageLimit);
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  }

  public onChangeSize(size: any): void {
    this.changeSize.emit(size);
  }

  public onChangeNumber(number: number): void {
    this.changeNumber.emit(number);
  }

}
