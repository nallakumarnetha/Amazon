package com.amazon.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.common.Response;

@RestController
@RequestMapping("files")
public class FileResource {

	@Autowired
	private FileService service;
	
	@PostMapping
	public List<String> uploadFile(@RequestParam("files") List<MultipartFile> files) {
		List<String> response = service.uploadFile(files);
		return response;
	}
	
}
