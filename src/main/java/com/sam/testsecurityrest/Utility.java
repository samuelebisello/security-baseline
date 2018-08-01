package com.sam.testsecurityrest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;

import java.util.Arrays;
import java.util.List;

public class Utility {

    private static Logger log = LoggerFactory.getLogger(Utility.class);

    public static List<String> getLoginCredentials(HttpHeaders headers) throws Exception {
        List<String> Authorization =  headers.get(HttpHeaders.AUTHORIZATION);
        for(String a: Authorization) {
            log.info(a);
        }
        String auth = Authorization.get(0).substring("Basic ".length()).trim(); //gestire eccezione
        byte[] info = Base64Utils.decodeFromString(auth);
        String credentials = new String(info);
        int delimiter = new String(info).indexOf(":");
        if(delimiter != -1) {
            return Arrays.asList(credentials.substring(0 , delimiter).trim(),
                    credentials.substring(delimiter +1 ).trim());
        }
        return null;

    }

}
