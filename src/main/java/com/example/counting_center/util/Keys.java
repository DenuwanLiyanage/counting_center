package com.example.counting_center.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Slf4j
@Component
@NoArgsConstructor
public class Keys {
    private final String SIGN_CERTIFICATE_PATH = "keys/sign/certificate.crt";
    private final String ENCRYPT_CERTIFICATE_PATH = "keys/encrypt/certificate.crt";

    private final String SIGN_PRIVATE_KEY_PATH = "keys/sign/private.der";
    private final String ENCRYPT_PRIVATE_KEY_PATH = "keys/encrypt/private.der";


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

    public RSAPrivateKey getSignPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return getPrivateKey(SIGN_PRIVATE_KEY_PATH);
    }

    public RSAPrivateKey getEncryptPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return getPrivateKey(ENCRYPT_PRIVATE_KEY_PATH);
    }

    private RSAPrivateKey getPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File file = ResourceUtils.getFile("classpath:" + path);
        byte[] privateKeyByteArray = Files.readAllBytes(file.toPath());

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByteArray);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    private Certificate getCertificate(String path) throws FileNotFoundException, CertificateException {
        File file = ResourceUtils.getFile("classpath:" + path);
        FileInputStream inputStream = new FileInputStream(file);
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return factory.generateCertificate(inputStream);
    }

    private String getCertificateString(String path) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + path);
        return Files.readString(file.toPath());
    }

}
