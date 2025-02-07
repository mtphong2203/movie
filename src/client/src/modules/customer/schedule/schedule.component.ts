import { Component } from '@angular/core';
import { HomeComponent } from "../home/home.component";
import { HomeMovieDetailsComponent } from "../home/home-movie-details/home-movie-details.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-schedule',
  standalone: true,
  imports: [CommonModule, HomeMovieDetailsComponent],
  templateUrl: './schedule.component.html',
  styleUrl: './schedule.component.css'
})
export class ScheduleComponent {

  public selectedTab: string = 'today';

  public date: string;
  public nextDate: string;

  constructor() {
    const today = new Date();
    const dateFormat = new Intl.DateTimeFormat('en-GB', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    });
    this.date = dateFormat.format(today);

    const nextDay = new Date(today);
    nextDay.setDate(today.getDate() + 1);

    this.nextDate = dateFormat.format(nextDay);
  }

  public getSchedule(event: string): void {
    this.selectedTab = event;
  }
}
