package com.amazon.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
public class S3Resource {

	@Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        return s3Service.uploadFile(file.getOriginalFilename(), file.getInputStream());
    }

    @GetMapping("/download/{fileName}")
    public byte[] download(@PathVariable String fileName) throws Exception {
        return s3Service.downloadFile(fileName);
    }
}
