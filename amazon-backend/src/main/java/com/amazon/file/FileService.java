package com.amazon.file;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.common.Response;
import com.amazon.product.Product;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetBucketLocationRequest;
import software.amazon.awssdk.services.s3.model.GetBucketLocationResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import static com.amazon.common.Logger.log;;

@Service
public class FileService {

	@Autowired
	private FileRepository repository;
	
	@Autowired
	private S3Client s3Client;

	private final String bucket = "bucket1.kumar";

	// to MYSQL database
	public List<String> uploadFile(List<MultipartFile> files) {
		if(files == null || files.isEmpty()) {
			return null;
		}
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
	
	// from MYSQL database
	public Map<String, String> getBase64Files(List<String> ids) {
		if(ids == null || ids != null && ids.isEmpty()) {
			return null;
		}
		Map<String, String> base64Files = new HashMap<>();
		for(String id : ids) {
			File file = repository.findById(id).orElse(null);
			if(file == null) {
				continue;
			}
			byte[] fileData = file.getData();
			String base64FileData = Base64.getEncoder().encodeToString(fileData);
			base64Files.put(id, base64FileData);
		}
		return base64Files;
	}
	
	//==========	AWS S3 start	============
	// to AWS S3 database
	public List<String> uploadFileToS3(List<MultipartFile> files) {
	    if (files == null || files.isEmpty()) {
	        return null;
	    }
	    List<String> fileIds = new ArrayList<>();
	    try {
	        for (MultipartFile file : files) {
	            String id = UUID.randomUUID().toString();
	        	PutObjectRequest putRequest =
	                    PutObjectRequest.builder()
	                            .bucket(bucket)
	                            .key(id)
	                            .contentType(file.getContentType())
	                            .build();
	            RequestBody requestBody =
	                    RequestBody.fromInputStream(
	                            file.getInputStream(),
	                            file.getInputStream().available()
	                    );
	            s3Client.putObject(putRequest, requestBody);
	            fileIds.add(id);
	        }
	    } catch (Exception ex) {
	        log.error("Failed to upload files to S3", ex);
	    }
	    return fileIds;
	}
	
	// from AWS S3 database
	public Map<String, String> getBase64FilesFromS3(List<String> ids) {
	    if (ids == null || ids.isEmpty()) {
	        return null;
	    }
	    Map<String, String> base64Files = new HashMap<>();
	    try {
	        for (String id : ids) {
	            GetObjectRequest getRequest =
	                    GetObjectRequest.builder()
	                            .bucket(bucket)
	                            .key(id)
	                            .build();
	            ResponseInputStream<GetObjectResponse> s3Object =
	                    s3Client.getObject(getRequest);
	            byte[] fileBytes = s3Object.readAllBytes();
	            String base64 = Base64.getEncoder().encodeToString(fileBytes);
	            base64Files.put(id, base64);
	        }
	    } catch (Exception ex) {
	        log.error("Failed to get files from S3", ex);
	    }
	    return base64Files;
	}
	//==========	AWS S3 end	============
	
}
