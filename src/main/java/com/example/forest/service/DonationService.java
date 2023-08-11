package com.example.forest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.donation.DonationCreateDto;
import com.example.forest.dto.donation.DonationListDto;
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
    
    public void saveDonation(DonationCreateDto dto) {
    	log.info("saveDonation(dto = {})", dto);
    	
    	Donation entity = dto.toEntity();
    	
    	donationRepository.save(entity);
    }
    
    public List<DonationListDto> getDonatorList() {
    	log.info("getDonatorList()");
    	
    	return donationRepository.getAllByAmountDesc();
    }
    
//    public int insertDonationData(DonationDto dto) {
//        log.info("insertDonationData={}", dto);
//        
//        return donationRepository.insertDonation(dto.toEntity());
//    }
//    
//    public DonationDto findAllDonationsOrderByAscId() {
//        Donation donation = donationRepository.findAllDonationsOrderByAscId();
//        
//        log.info("donation = {}", donation);
//        
//        return DonationDto.fromEntity(donation);
//    }
    

}
