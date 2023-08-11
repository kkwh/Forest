package com.example.forest.dto.donation;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationListDto {
	
	private long id;
	private int amount;
	private String donator;
	private LocalDateTime createdTime;

}
