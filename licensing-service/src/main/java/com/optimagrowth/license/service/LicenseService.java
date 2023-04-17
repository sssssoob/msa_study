package com.optimagrowth.license.service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    MessageSource messages;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig serviceConfig;

    public License getLicense(String organiztionId, String licenseId) {

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organiztionId, licenseId);

        if (null == license) {
            throw new IllegalArgumentException(
                    String.format(messages.getMessage(
                            "license.search.error.message", null, null),
                            licenseId, organiztionId));
        }
        return license.withComment(serviceConfig.getProperty());
    }

    public License createLicense(License license) {

        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(serviceConfig.getProperty());
    }

    public License updateLicense(License license) {

        licenseRepository.save(license);
        return license.withComment(serviceConfig.getProperty());
    }

    public String deleteLicense(String licenseId) {

        String responseMessage = null;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage
                = String.format("license.delete.message", null, null);
        return responseMessage;
    }


}


