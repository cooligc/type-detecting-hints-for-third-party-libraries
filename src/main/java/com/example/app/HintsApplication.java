package com.example.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class HintsApplication {


    public static void main(String[] args) {
        SpringApplication.run(HintsApplication.class, args);
    }
}

@Slf4j
@Controller
@ResponseBody
class ShapesController {


    @GetMapping("/shapes/{type}")
    Object get(@PathVariable String type) throws Exception {
        var shape = Class.forName(type);
        log.info("found "+ shape.getName());
        var ctor = shape.getDeclaredConstructors()[0];
        ReflectionUtils.makeAccessible(ctor);
        return ctor.newInstance();
    }

}

// todo make this work on native images http://192.168.4.21:8080/shapes/circle


