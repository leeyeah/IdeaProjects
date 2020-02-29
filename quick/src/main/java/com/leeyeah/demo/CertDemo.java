package com.leeyeah.demo;

import com.leeyeah.util.HexStringUtil;
import org.apache.ibatis.mapping.Environment;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CertDemo {

    //X.509
    static String pubKeyHexStr = "30820122300D06092A864886F70D01010105000382010F003082010A0282010100D78A743799816F4E5CC30E8F602C5F66D4E408563463CBD0CE5602CEE9063E3AA946062E8CCD7291D6252DBF177839A0CD82BCBD5717DCA89915C1EB7297C442D8E1A14B52E2F70AE2CFF15FBC16982A3E541B375E462872ADE5AEDFA9753001711EDE54F95C7FCC710BF93BE0AB462028FD9DF3E2A5A2C7835437D21EE96AC1C228397BA1EC2B24889C309035756DCE8EDCAA61C3EF1502ACCF07E16B7225D7F1DA91746720D3F2F44CF1EB6C663EF8FDC156BE6D1A6E2CCC91C11E60970D0D0D5E0685FBC5FBFC8134E8D7F07643F6F71F779B705CDC3A30433026798A7C4327DA17B62275FAE2A4C973C93A6EDD0E4CD9D662A5ED1E5407820BEC0A3DE0FF0203010001";
    //pkcs8
    static String priKeyHexStr = "308204BC020100300D06092A864886F70D0101010500048204A6308204A20201000282010100D78A743799816F4E5CC30E8F602C5F66D4E408563463CBD0CE5602CEE9063E3AA946062E8CCD7291D6252DBF177839A0CD82BCBD5717DCA89915C1EB7297C442D8E1A14B52E2F70AE2CFF15FBC16982A3E541B375E462872ADE5AEDFA9753001711EDE54F95C7FCC710BF93BE0AB462028FD9DF3E2A5A2C7835437D21EE96AC1C228397BA1EC2B24889C309035756DCE8EDCAA61C3EF1502ACCF07E16B7225D7F1DA91746720D3F2F44CF1EB6C663EF8FDC156BE6D1A6E2CCC91C11E60970D0D0D5E0685FBC5FBFC8134E8D7F07643F6F71F779B705CDC3A30433026798A7C4327DA17B62275FAE2A4C973C93A6EDD0E4CD9D662A5ED1E5407820BEC0A3DE0FF0203010001028201006ED72E68A13224B12196D557F9D717C063DE58432C71DD90256BA9CA099F42451C637C2E580E16CAD45B0E2964C258B04EBEDA9C6BBB43A818014E55EBFA0BDD6C76AB6176E06CB6566924F15C622A221F27554FA5D5625B86FDC46E1450C53D18950D34997F6DE10E895FC3E10772EA7AE9E15E57C161F243A0FD8BBA85C275ABF001054DED5D3DFD793A88EC02D63A25BCADEAC6E5C542874FCFD196FB19F26020F2703803F757ADD022DD68F48F084098707F1FA783396518C9955A0E7E5B6614272EB93F945FC546B8B5605A60A78DC601E509C219786A2D53F8569B1FEC79B8E465F95193BD762D63DA1F599FEB4A2F133A47DFBE31E2BEABE4560557E102818100EB5E385B2AFB7504D11838DFAF2F1C6760661D1ADA15AC81BA04D508DBD70847936CEB3A4411A0FB5C6F3F3CF2162F2FF6CB7183FFF71E75FF5B1FADFA2D02947EF325EF613A02297D8B58D20AE6735417248E5906858FB18E714C299792A3042E8F17BF11B5C72172402AB29DF82A4F27DF28900BF6A71D964783ED97C955F902818100EA6F4D0FC2ECEB73DFFE2BF6C88376C0AD7DFBBDF39D10826ED0866BEAC4897619F2B243F6744E7B86DA36BFB4F439EBF0F12D782A557FBE84467C62808BAC3C648D4329914EC914021BE9A7AB03864D6AB38E95F4EE1BA45C326F398E18EAE30CC239BFB15D2093133FE0AD7558A38C385C8511466FE505E97809E87707CCB702818054255D348ACB576C9F6C6F78D8E298DF0481BD4A0A49D16097A67E9B2A2D58526E8C7414629D82D61A97D31DF0D6A98C1649698DED86E5A6FDE210135CEB968E8C4C55759D66E5BAFB9D43E5E3EBBBB3868A953192B95144034ED211D494A21F2E9E0DB94B95975987C514FEC2A7244924520DE861BE8EC46F23A630DD2127D10281807BB9DA15E36E5EA57D260BD5E6E55B1B3D752C13882AF0B7A5FC6FB52CE625162B83A709AF5C9ABF1EE0B3DC618833B98E23FB375C405B7715208E2CFE62B8B7F6E986F4BD37B36FD7DD1B67DE1DB217D1A48A302260383E7A5A33D150695E2E0E61C350A42EDE2E410E89698271117FC38F30BB794D3CB5EE0DE84CC3E55DE90281800F57BAEAA2FE13D298F7262DEA04E3C7697A386030FA11FE24D2D40FAEBB528C4D670F13790155AF4F63BBD1728738B38E8F0EEA3601503FE84DA0A2CDB60B03DF32461F7BD30AC835C7401644467060CE7719AE7F6BE9AC0C244049D99939100578214E5D23F45DAC863FEC004DAADD178C8E0486B3C0D7E6641D0C1D27A464";

    public static void main(String[] args)throws Exception{
        Security.addProvider(new BouncyCastleProvider());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] pubKey = HexStringUtil.hexStr2bytes(pubKeyHexStr);
        byte[] priKey = HexStringUtil.hexStr2bytes(priKeyHexStr);
        KeyPair keyPair;
        PublicKey publicKey;
        PrivateKey privateKey;
        X509EncodedKeySpec x509EncodedKeySpec;
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec;
        X500Name x500Name;
        String subject = "CN=yeah, OU=ASP, O=AisaInfo-Sec, L=beijing, ST=beijing, C=CN";
        SubjectPublicKeyInfo subjectPublicKeyInfo;
        CertificationRequestInfo certificationRequestInfo;
        PKCS10CertificationRequest pkcs10CertificationRequest;
        byte[] certReqSign;
        byte[] tmpBytes;
        String tmpStr;


        /*
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        pubKey = publicKey.getEncoded();
        priKey = privateKey.getEncoded();
        System.out.println(publicKey.getFormat());
        System.out.println(privateKey.getFormat());
        System.out.println(HexStringUtil.bytes2HexStr( pubKey));
        System.out.println(HexStringUtil.bytes2HexStr( priKey));
        */

        pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKey);
        privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        x500Name = new X500Name(subject);
        subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
        certificationRequestInfo = new CertificationRequestInfo(x500Name,subjectPublicKeyInfo,new DERSet());


        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.sha256WithRSAEncryption, DERNull.INSTANCE);
        System.out.println(subjectPublicKeyInfo.getAlgorithm().getAlgorithm().toString());
        System.out.println(algorithmIdentifier.getAlgorithm().toString());

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(certificationRequestInfo.getEncoded(ASN1Encoding.DER));
        tmpBytes = signature.sign();


        pkcs10CertificationRequest = new PKCS10CertificationRequest(
                new CertificationRequest(certificationRequestInfo
                        ,algorithmIdentifier
                        ,new DERBitString(tmpBytes)));


        tmpBytes = pkcs10CertificationRequest.getEncoded();
        tmpStr = Base64.getMimeEncoder().encodeToString(tmpBytes);

        File file = new File("/Users/lee/Desktop/demo.csr");
        if(file.exists()){
            file.delete();
        }

        try(FileWriter fileWriter = new FileWriter(file); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            bufferedWriter.write("-----BEGIN CERTIFICATE REQUEST-----");
            //bufferedWriter.write(System.getProperty("line.separator"));
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.write(tmpStr);
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.write("-----END CERTIFICATE REQUEST-----");
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.flush();
            bufferedWriter.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }







    }
}
