package com.amazon.file;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.common.Response;
import com.amazon.product.Product;

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
	
	public Map<String, String> getBase64Files(List<String> ids) {
		if(ids == null || ids != null && ids.isEmpty()) {
			return null;
		}
		Map<String, String> base64Files = new HashMap<>();
		for(String id : ids) {
			File file = repository.findById(id).orElse(null);
			byte[] fileData = file.getData();
			String base64FileData = Base64.getEncoder().encodeToString(fileData);
			base64Files.put(id, base64FileData);
		}
		return base64Files;
	}
}
