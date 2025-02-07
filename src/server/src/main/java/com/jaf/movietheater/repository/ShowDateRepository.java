package com.jaf.movietheater.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jaf.movietheater.entities.ShowDate;

public interface ShowDateRepository extends JpaRepository<ShowDate, UUID>, JpaSpecificationExecutor<ShowDate>{
    ShowDate findByShowDate(LocalDate showDate);

    List<ShowDate> findByShowDateBetween(LocalDate fromDate, LocalDate toDate);
}
