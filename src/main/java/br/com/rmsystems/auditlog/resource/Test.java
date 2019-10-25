package br.com.rmsystems.auditlog.resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class Test {

    @PostMapping("/test")
    @ResponseBody
    void login(@Valid @RequestBody String testParam, HttpSession session) {
        System.out.println("Test");
    }
}
