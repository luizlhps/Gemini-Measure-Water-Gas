package com.measure.measure_water_gas.domain.interfaces.service;

public interface StorageProvider {
    String uploadFile(String fileName, byte[] data, String contentType);
    String getFileUrl(String fileName, long expirationTimeInDays);
    void deleteFile(String fileName);
}
