package com.example.forest.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

@Service
public class IpService {

    public String getServerIp() {
        
        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
        }
        catch ( UnknownHostException e ) {
            e.printStackTrace();
        }
            
        if( local == null ) {
            return "";
        }
        else {
            String ip = local.getHostAddress();
            return ip;
            
        }
        
    }
    
    
}
