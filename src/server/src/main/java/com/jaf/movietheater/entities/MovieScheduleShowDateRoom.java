package com.jaf.movietheater.entities;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie_schedule_showdate_rooms")
public class MovieScheduleShowDateRoom{

    @EmbeddedId
    private MovieScheduleShowDateRoomId id;

    // One To Many
    @OneToMany(mappedBy = "movieScheduleShowDateRoom")
    private Set<ScheduleSeatMovieDate> scheduleSeatMovieDates;

    // Many to one
    // Movie
    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    // ShowDate
    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("showDateId")
    @JoinColumn(name = "show_date_id", referencedColumnName = "id")
    private ShowDate showDate;

    // Room
    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    // Schedule
    @ManyToOne
    @MapsId("scheduleId")
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;
}
