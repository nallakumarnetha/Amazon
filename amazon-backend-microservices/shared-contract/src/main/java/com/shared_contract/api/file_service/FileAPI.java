package com.shared_contract.api.file_service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("files")
public interface FileAPI {
	
	@PostMapping
	List<String> uploadFile(@RequestParam("files") List<MultipartFile> files);

	@PostMapping("/base64")
	Map<String, String> getBase64Files(@RequestBody List<String> ids);
}
