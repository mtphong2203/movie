package com.jaf.movietheater.services;

import java.io.InputStream;

public interface AzureStorageService {
    String uploadFile(InputStream fileStream, String fileName, String contentType, long fileSize);
}
