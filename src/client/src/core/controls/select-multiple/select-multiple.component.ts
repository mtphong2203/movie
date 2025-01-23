import { CommonModule } from '@angular/common';
import { Component, forwardRef, HostListener, Input } from '@angular/core';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
@Component({
  selector: 'app-select-multiple',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './select-multiple.component.html',
  styleUrl: './select-multiple.component.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectMultipleComponent),
      multi: true
    }
  ]
})
export class SelectMultipleComponent implements ControlValueAccessor {
  @Input() options: any[] = [];

  isShowOptions: boolean = false;
  keyword: string = '';
  filteredOptions: any[] = [];
  selectedOptions: any[] = [];

  private onChange: (value: any) => void = () => { };
  private onTouched: () => void = () => { };
  disabled: boolean = false;

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

  onToggle() {
    if (this.disabled) return;
    this.isShowOptions = !this.isShowOptions;
  }

  onRemove(option: any) {
    this.selectedOptions = this.selectedOptions.filter(item => item.id !== option.id);
    this.onChange(this.selectedOptions.map(option => option.name));
  }

  onSearch(event: Event) {
    const keyword = (event.target as HTMLInputElement).value.toLowerCase();
    this.filteredOptions = this.options.filter(option =>
      option.name.toLowerCase().includes(keyword)
    );
  }

  onSelect(option: any) {
    if (!this.isSelected(option) && !this.disabled) {
      this.selectedOptions.push(option);
      this.onChange(this.selectedOptions.map(option => option.name));
    }
    this.keyword = '';
    this.filteredOptions = this.options;
  }

  isSelected(option: any): boolean {
    return this.selectedOptions.some(item => item.id === option.id);
  }

  writeValue(value: any): void {
    if (value) {
      this.selectedOptions = this.options.filter(option => value.includes(option.name));
    } else {
      this.selectedOptions = [];
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
}
