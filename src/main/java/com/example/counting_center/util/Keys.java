package com.example.counting_center.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Component
@NoArgsConstructor
public class Keys {
    private final String SIGN_CERTIFICATE_PATH = "keys/sign/certificate.crt";
    private final String ENCRYPT_CERTIFICATE_PATH = "keys/encrypt/certificate.crt";

    public Certificate getSignCertificate() throws FileNotFoundException, CertificateException {
        return getCertificate(SIGN_CERTIFICATE_PATH);
    }

    public Certificate getEncryptCertificate() throws FileNotFoundException, CertificateException {
        return getCertificate(ENCRYPT_CERTIFICATE_PATH);
    }

    public String getSignCertificateString() throws IOException {
        return getCertificateString(SIGN_CERTIFICATE_PATH);
    }

    public String getEncryptCertificateString() throws IOException {
        return getCertificateString(ENCRYPT_CERTIFICATE_PATH);
    }

    private Certificate getCertificate(String path) throws FileNotFoundException, CertificateException {
        File file = ResourceUtils.getFile("classpath:" + path);
        FileInputStream inputStream = new FileInputStream(file);
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return factory.generateCertificate(inputStream);
    }

    private String getCertificateString(String path) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + path);
        return new String(Files.readAllBytes(file.toPath()));
    }

}
