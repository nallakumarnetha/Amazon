package com.shared_contract.api.common_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common_service.preferences.Preferences;
import com.common_service.preferences.PreferencesService;

@RequestMapping("preferences")
public interface PreferencesAPI {

    @GetMapping
    PreferencesDTO find();

    @PutMapping
    PreferencesDTO update(@RequestBody Preferences request);
    
//    @PostMapping
//    PreferencesDTO create(@RequestBody Preferences request);

//    @DeleteMapping("/{id}")
//    ResponseDTO delete(@PathVariable String id);
}
