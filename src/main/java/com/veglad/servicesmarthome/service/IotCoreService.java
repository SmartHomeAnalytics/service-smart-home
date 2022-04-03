package com.veglad.servicesmarthome.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.cloudiot.v1.CloudIot;
import com.google.api.services.cloudiot.v1.model.Device;
import com.google.api.services.cloudiot.v1.model.DeviceCredential;
import com.google.api.services.cloudiot.v1.model.GatewayConfig;
import com.google.api.services.cloudiot.v1.model.PublicKeyCredential;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static java.nio.file.Files.readAllBytes;

@Service
@Slf4j
public class IotCoreService {

    private static final String IOT_REGISTRY_NAME = "smart-home-monitoring-registry";

    private static final String CLOUD_REGION = "europe-west1";

    private final String projectId;

    private final String applicationName;

    public IotCoreService(@Value("${smart-home-service.gcp.project-id}") String projectId,
                          @Value("${spring.application.name}") String applicationName) {
        this.projectId = projectId;
        this.applicationName = applicationName;
    }

    public void createIoTGateway(String algorithm,
                                 String gatewayId,
                                 String certificateFilePath) throws IOException, GeneralSecurityException {

        GoogleCredentials credential = GoogleCredentials.getApplicationDefault();

        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        HttpRequestInitializer init = new HttpCredentialsAdapter(credential);
        final CloudIot cloudIot = new CloudIot.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, init)
                .setApplicationName(applicationName)
                .build();

        final String registryPath = String.format(
                "projects/%s/locations/%s/registries/%s", projectId, CLOUD_REGION, IOT_REGISTRY_NAME);

        System.out.println("Creating gateway with id: " + gatewayId);
        Device device = new Device();
        device.setId(gatewayId);

        GatewayConfig gwConfig = new GatewayConfig();
        gwConfig.setGatewayType("GATEWAY");
        gwConfig.setGatewayAuthMethod("ASSOCIATION_ONLY");

        String keyFormat = "RSA_X509_PEM";
        if ("ES256".equals(algorithm)) {
            keyFormat = "ES256_PEM";
        }

        PublicKeyCredential publicKeyCredential = new PublicKeyCredential();
        byte[] keyBytes = readAllBytes(Paths.get(certificateFilePath));
        publicKeyCredential.setKey(new String(keyBytes, StandardCharsets.US_ASCII));
        publicKeyCredential.setFormat(keyFormat);
        DeviceCredential deviceCredential = new DeviceCredential();
        deviceCredential.setPublicKey(publicKeyCredential);

        device.setGatewayConfig(gwConfig);
        device.setCredentials(Collections.singletonList(deviceCredential));
        Device createdDevice = cloudIot
                .projects()
                .locations()
                .registries()
                .devices()
                .create(registryPath, device)
                .execute();

        System.out.println("Created gateway: " + createdDevice.toPrettyString());
    }
}
