import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class MovieHomeService {
    constructor() {
        this.getMovie();
    }

    public getMovie(): Observable<any> {
        return of([
            { image: './assets/images/movie-01.webp', name: 'Matrix 01' },
            { image: './assets/images/movie-02.webp', name: 'Avenger' },
            { image: './assets/images/movie-03.webp', name: 'Ant 1' },
            { image: './assets/images/movie-04.webp', name: 'Thor1' },
            { image: './assets/images/movie-05.webp', name: 'Ant 4' },
            { image: './assets/images/movie-06.webp', name: 'Hulk' },
            { image: './assets/images/movie-07.webp', name: 'Iron man 2' },
            { image: './assets/images/movie-08.webp', name: 'Sonic' },
            { image: './assets/images/movie-09.webp', name: 'Ant 2' },
            { image: './assets/images/movie-10.webp', name: 'Hulk 2' },
            { image: './assets/images/movie-11.jpg', name: 'Iron man' },
            { image: './assets/images/movie-12.jpg', name: 'Hulk 3' },
        ]);
    }
}