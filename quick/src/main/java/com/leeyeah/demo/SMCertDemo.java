package com.leeyeah.demo;

import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.zz.gmhelper.cert.SM2PublicKey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.*;
import java.util.Base64;

public class SMCertDemo {

    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
    private static ECParameterSpec ecParameterSpec = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());
    public static final String SIGN_ALGO_SM3WITHSM2 = "SM3withSM2";

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }

        //原文链接：https://blog.csdn.net/qq_38026782/article/details/100585512
    }


    public static void main(String[] args) throws Exception{

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        SecureRandom secureRandom = new SecureRandom("ADEFF".getBytes());
        byte[] tmpBytes;

        String subject = "CN=sign.sm.yeah, OU=ASP, O=AisaInfo-Sec, L=beijing, ST=beijing, C=CN";
        X500Name x500Name = new X500Name(subject);

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("EC", "BC");
        kpGen.initialize(ecParameterSpec, secureRandom);
        KeyPair kp = kpGen.generateKeyPair();
        System.out.println("a1-"+kp.getPublic().getFormat()+"-"+kp.getPublic().getAlgorithm());

        SM2PublicKey sm2PublicKey = new SM2PublicKey(kp.getPublic().getAlgorithm(),(BCECPublicKey) kp.getPublic());
        System.out.println(sm2PublicKey. getFormat()+"-"+ sm2PublicKey.getAlgorithm());

        System.out.println(kp.getPrivate().getFormat()+"-"+kp.getPrivate().getAlgorithm());

        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance( sm2PublicKey.getEncoded());
        System.out.println(subjectPublicKeyInfo.getAlgorithm().getAlgorithm().toString());

        CertificationRequestInfo certificationRequestInfo = new CertificationRequestInfo(
                x500Name,
                subjectPublicKeyInfo,
                new DERSet());

        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(GMObjectIdentifiers.sm2sign_with_sm3, DERNull.INSTANCE);

        byte[] userid = {0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38};
        SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userid);
        Signature signer = Signature.getInstance("SM3withSM2", "BC");
        signer.setParameter(parameterSpec);
        signer.initSign(kp.getPrivate(), secureRandom);
        signer.update(certificationRequestInfo.getEncoded(ASN1Encoding.DER));
        tmpBytes = signer.sign();

        //原文链接：https://blog.csdn.net/pridas/article/details/86118774

        PKCS10CertificationRequest pkcs10CertificationRequest = new PKCS10CertificationRequest(
                new CertificationRequest(
                        certificationRequestInfo
                        ,algorithmIdentifier
                        ,new DERBitString(tmpBytes)
                )
        );


        //生成CSR文件
        tmpBytes = pkcs10CertificationRequest.getEncoded();
        String tmpStr = Base64.getMimeEncoder().encodeToString(tmpBytes);

        File file = new File("/Users/lee/Desktop/sign.sm.yeah.csr");
        if (file.exists()) {
            file.delete();
        }

        try (FileWriter fileWriter = new FileWriter(file); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write("-----BEGIN CERTIFICATE REQUEST-----");
            //bufferedWriter.write(System.getProperty("line.separator"));
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.write(tmpStr);
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.write("-----END CERTIFICATE REQUEST-----");
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }


    }

}
