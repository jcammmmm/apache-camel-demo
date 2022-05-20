package org.jcammm.demos.camel.controller;

import org.jcammm.demos.camel.dto.Advice;
import org.springframework.stereotype.Component;

@Component
public class ControllerAdvisor {
    public Advice advice(String message) {
        return new Advice(message);
    }
}
