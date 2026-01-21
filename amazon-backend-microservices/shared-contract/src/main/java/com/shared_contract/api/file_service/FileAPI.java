package com.shared_contract.api.file_service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileAPI {
	
	@PostMapping("files")
	List<String> uploadFile(@RequestParam("files") List<MultipartFile> files);

	@PostMapping("files/base64")
	Map<String, String> getBase64Files(@RequestBody List<String> ids);
}
