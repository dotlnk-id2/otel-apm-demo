package com.example.apm.api;

import java.util.Optional;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apm.service.ApmService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RollController {

    @Resource
    ApmService apmService;


    @GetMapping("/rolldice")
    public String index(@RequestParam("player") Optional<String> player) {
        
        return "test";
    }

    @GetMapping("/error")
    public String error(@RequestParam("player") Optional<String> player) {
        throw new RuntimeException("error");
    }

}
