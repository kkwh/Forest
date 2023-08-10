package com.example.forest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.donation.DonationDto;
import com.example.forest.model.Donation;
import com.example.forest.repository.DonationRepository;
import com.siot.IamportRestClient.response.IamportResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class DonationService {
    
    private final DonationRepository donationRepository;
    
    @Transactional
    public int verifyDonation(IamportResponse<Donation> irsp, int amount) {
        Donation donation = Donation.builder()
                .donator(irsp.getResponse().getDonator())
                .build();
        
        
      return donationRepository.insertDonation(donation);
    }
    
    public int insertDonationData(DonationDto dto) {
        log.info("insertDonationData={}", dto);
        
        return donationRepository.insertDonation(dto.toEntity());
    }
    
    public DonationDto findAllDonationsOrderByAscId() {
        Donation donation = donationRepository.findAllDonationsOrderByAscId();
        
        log.info("donation = {}", donation);
        
        return DonationDto.fromEntity(donation);
    }
    

}
