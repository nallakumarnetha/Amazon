package com.shared_contract.api.file_service;

@RequestMapping("/s3")
public interface S3API {

    @PostMapping("/upload")
    String upload(@RequestParam("file") MultipartFile file);

    @GetMapping("/download/{fileName}")
    byte[] download(@PathVariable String fileName);
}
