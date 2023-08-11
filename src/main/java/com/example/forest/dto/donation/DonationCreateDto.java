package com.example.forest.dto.donation;

import com.example.forest.model.Donation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonationCreateDto {
	
	private String impUid;
	private String merchantUid;
	private int amount;
	private String donator;
	
	public Donation toEntity() {
		return Donation.builder()
				.impUid(impUid)
				.merchantUid(merchantUid)
				.amount(amount)
				.donator(donator)
				.build();
	}

}
