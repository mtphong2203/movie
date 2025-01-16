import { Component, Inject, OnInit } from '@angular/core';
import { MovieHomeService } from '../../../../services/movie/movie-home.service';
import { CommonModule } from '@angular/common';
import { MOVIE_SERVICE } from '../../../../constants/injection.constant';
import { MovieService } from '../../../../services/movie/movie.service';
import { combineLatest } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-movie-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home-movie-details.component.html',
  styleUrl: './home-movie-details.component.css'
})
export class HomeMovieDetailsComponent implements OnInit {

  public movies: any[] = [];
  public movieInformation: any[] = [];
  public combineMovies: any[] = [];

  constructor(
    private movieItem: MovieHomeService,
    @Inject(MOVIE_SERVICE) private movieService: MovieService, private router: Router
  ) { }

  ngOnInit(): void {
    // User combineLatest to combine data
    combineLatest([
      this.movieItem.getMovie(), // Observable for movies
      this.movieService.search(''), // Observable for movieInformation
    ]).subscribe(([movies, searchResult]) => {
      this.movies = movies;
      this.movieInformation = searchResult.data;

      // Kết hợp dữ liệu dựa trên name
      this.combineMovies = this.movies.map((movie) => {
        const info = this.movieInformation.find((item) => item.name === movie.name);
        return {
          ...movie,
          ...info,
        };
      });
    });
  }

  public getTicket(): void {
    this.router.navigate(['/ticket']);
  }

}
