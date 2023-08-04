package com.example.forest.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

// 채한별 : 댓글에서 쓸거임
// 김우현 쓸거면 커피사라
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
