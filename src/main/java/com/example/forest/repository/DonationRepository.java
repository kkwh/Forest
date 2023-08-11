package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.forest.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Modifying
    @Query("INSERT INTO Donation (donator) VALUES (:donator)")
    int  insertDonation(Donation donation);
    
    @Query("SELECT d FROM Donation d ORDER BY d.id ASC")
    Donation findAllDonationsOrderByAscId();

    
}
