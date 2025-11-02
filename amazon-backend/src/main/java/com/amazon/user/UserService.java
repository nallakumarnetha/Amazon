package com.amazon.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.common.Response;
import com.amazon.file.FileService;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private FileService fileService;
    
    static String userId = "u1";	//to do

    // Fetch all users (with files in base64)
    public Response findAllUsers() {
        List<User> users = repository.findAll();
        for (User user : users) {
            List<String> fileIds = user.getFiles(); // assuming User has List<String> files
            if (fileIds != null && !fileIds.isEmpty()) {
                Map<String, String> base64FilesData = fileService.getBase64Files(fileIds);
                user.setBase64Files(base64FilesData);
            }
        }
        Response response = new Response();
        response.setUsers(users);
        response.setTotal(users.size());
        return response;
    }

    // Fetch single user (with files)
    public User findUserById(String id) {
        User user = repository.findById(id).orElse(null);
        if (user != null && user.getFiles() != null && !user.getFiles().isEmpty()) {
            Map<String, String> base64FilesData = fileService.getBase64Files(user.getFiles());
            user.setBase64Files(base64FilesData);
        }
        return user;
    }

    // Add new user
    public User addUser(User request) {
        return repository.save(request);
    }

    // Update user
    public User updateUser(String id, User request) {
        User entity = repository.findById(id).orElse(null);
        if (entity == null) return null;

        if (request.getName() != null && !request.getName().isEmpty()) {
            entity.setName(request.getName());
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
            entity.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getGender() != null) {
            entity.setGender(request.getGender());
        }
        if (request.getLanguage() != null) {
            entity.setLanguage(request.getLanguage());
        }
        if (request.getFiles() != null && !request.getFiles().isEmpty()) {
            entity.setFiles(request.getFiles());
        }

        return repository.save(entity);
    }

    // Remove user
    public Response removeUser(String id) {
        repository.deleteById(id);
        Response response = new Response();
        response.setMessage("user deleted");
        return response;
    }
    
    public User getCurrentUser() {
    	User response = findUserById(userId);
		return response;
    }
}
