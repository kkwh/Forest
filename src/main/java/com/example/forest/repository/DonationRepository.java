package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.donation.DonationListDto;
import com.example.forest.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Modifying
    @Query("INSERT INTO Donation (donator) VALUES (:donator)")
    int  insertDonation(Donation donation);
    
    @Transactional
    @Query("select new com.example.forest.dto.donation.DonationListDto(d.id, d.amount, d.donator, d.createdTime) "
    		+ " from Donation d "
    		+ " order by d.amount desc, donator")
    List<DonationListDto> getAllByAmountDesc();

    
}
