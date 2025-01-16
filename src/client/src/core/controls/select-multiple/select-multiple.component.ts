import { CommonModule } from '@angular/common';
import { Component, ElementRef, HostListener, Output, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faAngleDown, faTimes, IconDefinition } from '@fortawesome/free-solid-svg-icons';

type Option = {
  id: number;
  name: string;
}

@Component({
  selector: 'app-select-multiple',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './select-multiple.component.html',
  styleUrl: './select-multiple.component.css'
})
export class SelectMultipleComponent {
  public faTimes: IconDefinition = faTimes;
  public faAngleDown: IconDefinition = faAngleDown;
  public isShowOptions: boolean = false;
  public keyword: string = '';
  public options: Option[] = [
    { id: 1, name: 'Admin' },
    { id: 2, name: 'Manager' },
    { id: 3, name: 'Customer' },
    { id: 4, name: 'Staff' },
    { id: 5, name: 'Partime Staff' },
  ]

  public filteredOptions: Option[] = this.options;

  @Output() public selectedOptions: Option[] = [];

  public isSelected = (option: Option) => {
    return this.selectedOptions.filter(selectedOption => selectedOption.id === option.id).length > 0;
  }

  public onSearch = (event: any) => {
    this.keyword = event.target.value;
    this.filteredOptions = this.options.filter(option => option.name.toLowerCase().includes(this.keyword.toLowerCase()));
  }

  public onSelect = (option: Option) => {
    if (this.isSelected(option)) {
      this.selectedOptions = this.selectedOptions.filter(selectedOption => selectedOption.id !== option.id);
    } else {
      this.selectedOptions.push(option);
    }
  }

  public onRemove = (option: Option) => {
    this.selectedOptions = this.selectedOptions.filter(selectedOption => selectedOption.id !== option.id);
  }

  public onToggle = () => {
    this.isShowOptions = !this.isShowOptions;

  }
  // Using viewChild to get the element option container
  @ViewChild('optionsContainer') optionsContainer!: ElementRef;

  // Hidden options when click outside of the options container element
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    // If the click is outside the dropdown, do somehing
    if (this.optionsContainer && !this.optionsContainer.nativeElement.contains(event.target)) {
      this.isShowOptions = false;
    }
  }
}
