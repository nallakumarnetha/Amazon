package com.common_service.preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.amazon.common.Response;

import static com.amazon.common.Logger.log;

@RestController
@RequestMapping("preferences")
public class PreferencesResource {

    @Autowired
    private PreferencesService service;

    @GetMapping
    public Preferences find() {
        log.info("request received: find preferences");
        Preferences response = service.find();
        log.info("response sent: find preferences");
        return response;
    }

    @PutMapping
    public Preferences update(@RequestBody Preferences request) {
        log.info("request received: update preferences");
        Preferences response = service.update(request);
        log.info("response sent: update preferences");
        return response;
    }
    
//    @PostMapping
//    public Preferences create(@RequestBody Preferences request) {
//        log.info("request received: create preferences");
//        Preferences response = service.save(request);
//        log.info("response sent: create preferences");
//        return response;
//    }
//
//    @DeleteMapping("/{id}")
//    public Response delete(@PathVariable String id) {
//        log.info("request received: delete preferences");
//        Response response = service.delete();
//        log.info("response sent: delete preferences");
//        return response;
//    }
}
