package com.example.forest.controller;

import com.example.forest.dto.donation.DonationCreateDto;
import com.example.forest.service.DonationService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/donation")
public class DonationRestController {

      @Autowired
      private DonationService donationService;
      
      private final IamportClient iamportClient;
      private final String REST_API = "1348125428827842";
      private final String REST_API_SECRET = "Kvqki7KYhS3qGqpWnnYyx7gNFA0BBr2ekr8uKwwOwcW4FL4oWukxvHzBtXXSo7CdhstdAAt8jXmPMbML";
          
    public DonationRestController() {
        this.iamportClient = new IamportClient(REST_API, REST_API_SECRET);
        
    }    
        
    @RequestMapping("/verify/{impUid}")
    @ResponseBody
    public IamportResponse<Payment> paymentByImpUid(@PathVariable("impUid") String impUid)
            throws IamportResponseException, IOException {
        log.info("paymentByImpUid({})", impUid);
        
        return iamportClient.paymentByImpUid(impUid);
    }
    
    @PostMapping("/save")
    public ResponseEntity<Integer> saveDonation(@RequestBody Map<String, String> map) {
    	
    	for(String key : map.keySet()) {
    		log.info("key = " + key + ", " + map.get(key));
    	}
    	
    	DonationCreateDto dto = DonationCreateDto.builder()
    			.impUid(map.get("impUid"))
    			.merchantUid(map.get("merchantUid"))
    			.amount(Integer.parseInt(map.get("amount")))
    			.donator(map.get("donator"))
    			.build();
    	
    	donationService.saveDonation(dto);
    	
    	return ResponseEntity.ok(1);
    }
    
}
