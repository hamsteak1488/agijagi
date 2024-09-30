package com.password926.agijagi.docs.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/docs")
public class DocsController {

    @GetMapping
    public String index() {
        return "redirect:/docs/index.html";
    }

    @GetMapping(value = "/copy", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public byte[] getIndexHtml() throws Exception {
        Path path = new ClassPathResource("static/docs/index.html").getFile().toPath();
        return Files.readAllBytes(path);
    }
}
