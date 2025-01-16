import { Component, OnInit } from '@angular/core';
import { SelectMultipleComponent } from '../../../core/controls/select-multiple/select-multiple.component';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-demo-control',
  standalone: true,
  imports: [SelectMultipleComponent, ReactiveFormsModule, CommonModule],
  templateUrl: './demo-control.component.html',
  styleUrl: './demo-control.component.css'
})
export class DemoControlComponent implements OnInit {
  public checkboxOptions: any[] = [

  ];

  public roles: FormControl = new FormControl([]);

  public form: FormGroup = new FormGroup({
    roles: this.roles,
  });

  constructor(
    // @Inject() private readonly showTimeService: ShowTimeService,
  ) {
  }

  ngOnInit(): void {
    this.getShowTimes();
  }

  public getShowTimes = () => {
    // Call api to get list of show times
    // this.showTimeService.getAll().subscribe((showTimes: any[]) => {
    //   this.checkboxOptions = showTimes;
    // });
  }
}
