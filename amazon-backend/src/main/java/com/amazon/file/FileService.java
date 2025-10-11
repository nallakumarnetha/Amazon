package com.amazon.file;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.common.Response;

import static com.amazon.common.Logger.log;;

@Service
public class FileService {

	@Autowired
	private FileRepository repository;
	
	public List<String> uploadFile(List<MultipartFile> files) {
		if(files == null || files.isEmpty()) return null;
		List<String> fileIds = new ArrayList<>();
		try {
			for(MultipartFile file : files) {
				File f = new File();
				f.setName(file.getOriginalFilename());
				f.setType(file.getContentType());
				f.setData(file.getBytes());
				File response = repository.save(f);
				fileIds.add(response.getId());
			}
		} catch (Exception ex) {
			log.error("failed to upload files", ex);
		}
		return fileIds;
	}
}
