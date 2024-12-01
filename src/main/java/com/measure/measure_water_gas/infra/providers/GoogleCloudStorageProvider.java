package com.measure.measure_water_gas.infra.providers;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.measure.measure_water_gas.domain.exception.GoogleCredentialsException;
import com.measure.measure_water_gas.domain.interfaces.service.StorageProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service("google")
public class GoogleCloudStorageProvider implements StorageProvider {

    @Value("${gcs-resource-test-bucket}")
    private String bucketName;

    private Storage storage;

    public GoogleCloudStorageProvider() throws IOException, GoogleCredentialsException {
        InputStream credentialsStream = getClass().getClassLoader().getResourceAsStream("credentials.google.json");

        if (credentialsStream == null) {
            throw new GoogleCredentialsException();
        }

        this.storage = StorageOptions
                .newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(credentialsStream))
                .build()
                .getService();
    }

    @Override
    public String uploadFile(String fileName, byte[] data, String contentType) {

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(contentType)
                .build();

        Blob blob = storage.create(blobInfo, data);

        return getFileUrl(fileName, 1);
    }

    @Override
    public String getFileUrl(String fileName, long expirationTimeInDays) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).build();

        URL signedUrl = storage.signUrl(
                blobInfo,
                expirationTimeInDays, TimeUnit.DAYS,
                Storage.SignUrlOption.withV4Signature()
        );

        return signedUrl.toString();
    }

    @Override
    public void deleteFile(String fileName) {
    }

}
