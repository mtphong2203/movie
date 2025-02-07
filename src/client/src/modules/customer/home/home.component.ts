import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faCircle, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { HomeMovieDetailsComponent } from "./home-movie-details/home-movie-details.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FontAwesomeModule, ReactiveFormsModule, HomeMovieDetailsComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {

  public form!: FormGroup;

  public homeLogo: string = './assets/images/home.webp';
  public circle: IconDefinition = faCircle;

  ngOnInit(): void {
    this.createForm();
  }

  private createForm(): void {
    this.form = new FormGroup({
      name: new FormControl('', Validators.required),
    });
  }

  public onSubmit(): void {

  }
}
