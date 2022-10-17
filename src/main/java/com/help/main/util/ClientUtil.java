package com.help.main.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientUtil { 
    
    public static HashMap<String, Object> getInfo(HttpServletRequest request){    
        HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("ip", getRemoteIp(request)); 
        hm.putAll(getBrowser(request));
        hm.put("device", deviceCheck(request));
        
        log.info("==========clientInfo============== : {}", hm);

        return hm;
    }
    
    public static String getRemoteIp(HttpServletRequest request){
         String ip = request.getHeader("X-FORWARDED-FOR"); 
         
         //proxy 환경일 경우
         if (ip == null || ip.length() == 0) {
             ip = request.getHeader("Proxy-Client-IP");
         }
 
         //웹로직 서버일 경우
         if (ip == null || ip.length() == 0) {
             ip = request.getHeader("WL-Proxy-Client-IP");
         }
 
         if (ip == null || ip.length() == 0) {
             ip = request.getRemoteAddr() ;
         }
         
         return ip;
    }

    public static HashMap<String, Object> getBrowser(HttpServletRequest request) {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        String browser 	 = "";
        String userAgent = request.getHeader("User-Agent");		
            
        if(userAgent.indexOf("Trident") > -1) {												// IE
            browser = "ie";
        } else if(userAgent.indexOf("Edge") > -1) {											// Edge
            browser = "edge";
        } else if(userAgent.indexOf("Whale") > -1) { 										// Naver Whale
            browser = "whale";
        } else if(userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { 		// Opera
            browser = "opera";
        } else if(userAgent.indexOf("Firefox") > -1) { 										 // Firefox
            browser = "firefox";
        } else if(userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1 ) {	 // Safari
            browser = "safari";		
        } else if(userAgent.indexOf("Chrome") > -1) {										 // Chrome	
            browser = "chrome";
        }
            
        log.info("---------------------------------------------");
        log.info("User-Agent : {}", userAgent);
        log.info("Browser : {}", browser);
        log.info("---------------------------------------------");
 
        hm.put("agent", userAgent); 
        hm.put("browser", browser); 
        return hm;
    }

    public static String deviceCheck(HttpServletRequest request) {
        
        log.info("deviceCheck");

        Device device = DeviceUtils.getCurrentDevice(request);
        String deviceType = "unknown";
        if (device == null) {
            return deviceType;
        }
        
        if (device.isMobile()) {
            log.info("Hello mobile user!");
            deviceType = "mobile";
        } else if (device.isTablet()) {
            log.info("Hello tablet user!");
            deviceType = "tablet";
        } else if (device.isNormal()) {
            log.info("Hello desktop user!");         
            deviceType = "desktop";            
        }

        log.info("deviceType : {}", deviceType);

        return deviceType;
    }
}