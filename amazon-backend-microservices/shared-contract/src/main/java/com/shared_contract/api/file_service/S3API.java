package com.shared_contract.api.file_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/s3")
public interface S3API {

    @PostMapping("/upload")
    String upload(@RequestParam("file") MultipartFile file);

    @GetMapping("/download/{fileName}")
    byte[] download(@PathVariable String fileName);
}
