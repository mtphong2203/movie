package com.jaf.movietheater.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jaf.movietheater.services.AzureStorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "File Management", description = "Endpoints for managing files")
public class FileController {

    private final AzureStorageService azureStorageService;

    @Autowired
    public FileController(AzureStorageService azureStorageService) {
        this.azureStorageService = azureStorageService;
    }

    @Operation(summary = "Upload a file", description = "Uploads a file to Azure Storage.", responses = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully", content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "File upload failed", content = @Content)
    })
    @PostMapping(value = "/upload-file", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = azureStorageService.uploadFile(
                    file.getInputStream(),
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize());

            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);

            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload file failed");
        }
    }

}
