import { CommonModule } from '@angular/common';
import { Component, forwardRef, HostListener, Input, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-multi-select',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './multi-select.component.html',
  styleUrl: './multi-select.component.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => MultiSelectComponent),
      multi: true,
    }
  ]
})
export class MultiSelectComponent implements ControlValueAccessor{
  // List option from parent component
  @Input() options: any[] = [];

  // Show or hide the option list based on toggle
  isShowOptions: boolean = false;
  keyword: string = '';

  // List options that are filtered based on the keyword
  filteredOptions: any[] = [];

  // List options that are selected
  selectedOptions: any[] = [];

  private onChange: (value: any) => void = () => { };
  private onTouched: () => void = () => { };

  @Input() disabled: boolean = false;

  ngOnInit(): void {
    this.filteredOptions = this.options;
  }

  @HostListener('document:click', ['$event'])
  handleClick(event: Event) {
    const target = event.target as HTMLElement;
    if (!target.closest('.select-control')) {
      this.isShowOptions = false;
      this.onTouched();
    }
  }

  // Show or hide the option list
  onToggle() {
    if (this.disabled) return;
    this.isShowOptions = !this.isShowOptions;
  }

  // Remove the selected option
  onRemove(option: any) {
    this.selectedOptions = this.selectedOptions.filter(item => item.id !== option.id);
    this.onChange(this.selectedOptions.map(option => option.name));
  }

  // Filter the options based on the keyword
  onSearch(event: Event) {
    const keyword = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredOptions = this.options.filter(option =>
      option.name.toLowerCase().includes(keyword)
    );
  }

  // Select the option of filtered options
  onSelect(option: any) {
    if (!this.isSelected(option) && !this.disabled) {
      this.selectedOptions.push(option);
      this.onChange(this.selectedOptions.map(option => option.id));
    }
    this.keyword = '';
    this.filteredOptions = this.options;
  }

  // Check if the option is selected and style it
  isSelected(option: any): boolean {
    return this.selectedOptions.some(item => item.id === option.id);
  }

  // Write the value to the parent component
  writeValue(value: any): void {
    if (value && this.options) {
      this.selectedOptions = this.options.filter(option => value.includes(option.id));
    } else {
      this.selectedOptions = [];
    }
  }

  // Register the change event
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  // Register the touch event
  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  // Set the disabled state
  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
}
