package com.example.forest.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forest.dto.donation.DonationListDto;
import com.example.forest.service.DonationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/donation")
public class DonationController {
	
	private final DonationService donationService;

	@GetMapping("/donation")
	public String donation(Model model) {
		log.info("donation");
		
		List<DonationListDto> list = donationService.getDonatorList();
		model.addAttribute("list", list);

		return "/donation/donation";
	}

}
