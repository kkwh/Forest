package com.example.forest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
@Entity
@Table(name = "DONATION")
@SequenceGenerator(name = "DONATION_SEQ_GEN", sequenceName = "DONATION_SEQ", allocationSize = 1)
public class Donation {
	
	@Id
	@GeneratedValue(generator = "DONATION_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	// 후원자 이름
	private String donator;

}
