package org.jcammm.demos.camel.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UUIDGenerator {

    public synchronized String get() {
        return UUID.randomUUID().toString();
    }
    
}
