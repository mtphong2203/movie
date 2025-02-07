package com.jaf.movietheater.services;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

@Service
public class AzureStorageServiceImpl implements AzureStorageService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Override
    public String uploadFile(InputStream fileStream, String fileName, String contentType, long fileSize) {
        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();

        BlobClient blobClient = containerClient.getBlobClient(fileName);

        blobClient.upload(fileStream, fileSize, true);
        blobClient.setHttpHeaders(new com.azure.storage.blob.models.BlobHttpHeaders().setContentType(contentType));

        return blobClient.getBlobUrl();
    }

}
