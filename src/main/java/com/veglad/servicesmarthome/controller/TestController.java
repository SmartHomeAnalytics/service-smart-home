package com.veglad.servicesmarthome.controller;

import com.veglad.servicesmarthome.DbTest;
import com.veglad.servicesmarthome.TestDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/test")
public class TestController {

    private final TestDao testDao;

    public TestController(TestDao testDao) {
        this.testDao = testDao;
    }

    @GetMapping
    public List<DbTest> getTests() {
        return testDao.findAll();
    }
}
