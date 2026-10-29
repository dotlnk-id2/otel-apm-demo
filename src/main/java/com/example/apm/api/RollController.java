package com.example.apm.api;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apm.eq.table.User;
import com.example.apm.service.ApmService;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RollController {

    @Resource
    ApmService apmService;


    @GetMapping("/rolldice")
    public Observable<List<User>> index() {
        return apmService.findUsersByUsername("abc");
    }

    // @GetMapping("/error")
    // public String error() {
    //     throw new RuntimeException("error");
    // }

    @GetMapping("/siglne")
    public Maybe<User> siglne() {
        return apmService.single("abc");
    }
}
