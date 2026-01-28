package com.ecommerce.controller;

import com.ecommerce.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/immagini")
@RequiredArgsConstructor
public class ImmaginiController {
    private final StorageService storage;

    @GetMapping(value = "/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> get(@PathVariable String filename){
        return ResponseEntity.ok(storage.load(filename));
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        return storage.store(file);
    }
}
