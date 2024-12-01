package com.measure.measure_water_gas.application.factories;

import com.measure.measure_water_gas.domain.enums.StorageProviderTypeEnum;
import com.measure.measure_water_gas.domain.interfaces.service.StorageProvider;
import com.measure.measure_water_gas.infra.providers.GoogleCloudStorageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StorageFactory {
    private final GoogleCloudStorageProvider googleProvider;

    @Autowired
    public StorageFactory(GoogleCloudStorageProvider googleProvider) {
        this.googleProvider = googleProvider;
    }

    public StorageProvider getProvider (StorageProviderTypeEnum providerType)
    {
        switch (providerType) {
            case GOOGLE:
                return googleProvider;
            default:
                throw new IllegalArgumentException("Provider desconhecido: " + providerType);
        }
    }
}
