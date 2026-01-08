package com.common_service.preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common_service.client.UserClient;


@Service
public class PreferencesService {

	@Autowired
	private PreferencesRepository repository;
	
	@Autowired
	private UserClient userClient;

	public Preferences find() {
		Preferences preferences = repository.findByUserId(userClient.getCurrentUser().getId());
		if(preferences == null) {
			preferences = new Preferences();
			preferences.setUserId(userClient.getCurrentUser().getId());
			preferences = repository.save(preferences);
		}
		return preferences;
	}

	public Preferences update(Preferences request) {
		Preferences preferences = repository.findByUserId(userClient.getCurrentUser().getId());
		preferences.setAi(request.isAi());
		preferences.setPrime(request.isPrime()); 
		preferences.setColor(request.getColor());
		preferences.setTextValue(request.getTextValue());
		preferences.setHexColor(request.getHexColor());
		return repository.save(preferences);
	}

//	public Preferences save(Preferences request) {
//		return repository.save(request);
//	}
//
//	public Response delete() {
//		//        repository.deleteById(id);
//
//		Response response = new Response();
//		response.setMessage("preferences deleted");
//		return response;
//	}
}
