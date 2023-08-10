package com.example.forest.dto.donation;

import com.example.forest.model.Donation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationDto {

    private long id;
    // 후원자 이름
    private String donator;
    
    public Donation toEntity() {
        return Donation.builder()
                .donator(donator)
                .build();
    }
    
    public static DonationDto fromEntity(Donation entity) {
        return DonationDto.builder()
                .id(entity.getId())
                .donator(entity.getDonator())
                .build();
    }
    
}
