package com.sda.repository.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/repository")
public class FileController {

    @GetMapping("/{name:.+}")
    public Resource downloadPdf(@PathVariable String name) throws IOException {
        return new ClassPathResource(name);
    }

}
