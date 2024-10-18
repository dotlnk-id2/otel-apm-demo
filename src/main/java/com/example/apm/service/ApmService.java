package com.example.apm.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.apm.matedata.Test;
import com.example.apm.matedata.TestRepository;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;

public interface ApmService {

    String runA();

    String runB(String s);

    String runC(Integer x);

    @Slf4j
    @Service
    public static class ApmServiceImpl implements ApmService {

        @Resource
        TestRepository testRepository;

        @Override
        @WithSpan
        public String runA() {
            Optional<Test> obj = testRepository.findById(1L);
            obj.ifPresent(o -> {
                log.warn("runA obj={}", o);
            });
            return "haha runA";
        }

        @Override
        @WithSpan
        public String runB(String s) {
            Optional<List<Test>> list = this.testRepository.findByName(s);
            log.info("runB=list{}", list.orElse(Collections.emptyList()).size());
            return s;
        }

        @Override
        @WithSpan
        public String runC(Integer x) {
            return x.toString() + "C";
        }

    }
}
