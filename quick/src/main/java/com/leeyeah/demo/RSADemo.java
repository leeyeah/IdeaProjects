package com.leeyeah.demo;

import org.apache.ibatis.javassist.bytecode.ByteArray;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSADemo {


    // MIICWwIBAAKBgQC/98E7aCgxahU5CMdsWul39uKAnUI+fKxLwSCbVMD2ubEV0DU+YoSNmoNvAFIH9Z3d0cEKXGpHqHIm/TmsobUqvtLUlvyF7fK8OW7y1TPQmGvwh34M obpuAd/40EhnRCAVeEj01HPy4pLMsnx0bAy5am8k12beHmfvEllqED0XUwIDAQAB AoGATUtgzp69sEfai8BxK7vfTaJmyQqI5cglR8tXabPSjoVuPC49ggUBAlM4oLUsUnAfRHV0mMtlL70vWWuUkWH7I6tWB/3Xo79e/+1/8AvtTf+RLj6VUCva15FbtATm FeZ5+/lELb9jaQ09ARwN13lptP5fdcOiCUNLhkPNzsGzm+ECQQD374EP0bOtW1926rV6PknXKtdBmH6a3tvjzGORbexCNwJOelq51MGO9p7VYTzkeJAoD449Jk6cfrI2 ZBGfGPTlAkEAxjY4rQGYK4gyOWERHkr/CyXg5BKA8CCSlMomS3HfWmNdt0pcwbUJ MgX6oVEzMMxUBa1abIqN028QFYuJa1YP1wJACf3D1LBdrkG9YhxpPcjezuXyELmleHY8pDxrUsP8PlrbI/B4UX7KYgl8MxsdChP1szeVQs+VYWcVpW147FFkJQJALkbY gqP9qCQ7PaRI/96YnzyvvfAIBIhGUNDFkhW7BPpwFewZrewbgn9c3a78macK8uxfUTokFLo4ch9K7TGBrwJAWTI7LLXcQPRBU1YiGVLJdalBcPHxjaTg0COMQsGpQbgp6Asopi9ZK3JB1/NbKL5S+ztHM5DNSud2jzyafYFiQQ==

    //RSA密钥——JAVA与C#的区别和联系 转换 JAVA .Net RSA
    //https://www.cnblogs.com/lowcoders-Blog/p/5706463.html

    // https://www.cnblogs.com/linjiqin/p/6005626.html

    //https://blog.csdn.net/yi_zz32/article/details/50097325

    //x509证书解析
    //https://blog.csdn.net/xiyuan1999/article/details/6212746

    //base64在线转换
    //http://www1.tc711.com/tool/BASE64.htm

    public static byte[] srcData ;
    public static byte[] signResult;
    public static byte[] verifyResult;
    static String base64myPriKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDTLo6PCSHn1bdPl2OvwAiXvV5ehgQ8fV3lJT2bsJfC115IyGkaFOjaZOxBDFoMUo6QR9uoSkctJbmCUFYbahMnT+aJWgmfVJUwekD3Bj3lrD9Hs0qfLmnnuaCL6F2xOu2nFBQ2WRH0hgfEsckpv/BJe1TIqUuZGGcBH2HiyWAXfXR6OYKs+PvyIgsI54+X8qPrnJoWID3enwG8S8QCnLoEuTq/lRV961e+eHTWSkIQfKWbhkwBsuvIt0Dc5etoWzeo+K8HzG7g7pQbCytIFM9AEtA99ZjNBW66aOuy1Zxx7Aaiu4DXkOxeXVP3P/jQ/JBwNMDs1sk1u/E2ZalDm4rZAgMBAAECggEBAJfM/pKYyPOCL2uZ8gFSc5rHd79QbFki8MyXcWqpjN10vIRq8wnZf+wE4cJ8Y3o6GdpinOWixvkQBgJDRzIblLsMdzXCL+qE/VzEdFBc/z9KCo66tWgpD92PFHWdSUPtjQktqL3MVtjDQ3BDL8u/+bSIX9CBySK6ZMhsy4sen+EhcNwuSzZQkEa7g2zjV2gOPmsAes21GX8hQCavDSIdu2l23fwnq9H0BjBFJEi8OR78yXgAqqfwAgsnrmgTV3HOUX2yb/nlVcx8MgNpYGwaEZC49kOWW9/FVpudV3uMcKmtSWtpHG1hM+QpUtWiw/4LdV66tVwQ9QSe3EwoJAY+ZjUCgYEA+kENrY+6w40zGBHJ+OwHJ63Mp2dc0I8IwKYu23Ri5y06bN23iL6Ttujg9XGx/fjJF9v5Y2auJj1cVXvfy2BQ3NlRK6S94e8wiqZLzxziM9NXVtMLvEmv9ITLSsFjF/oEGz5b+u8o7jcpztANSDMEyAo9WKK0xh58inKHCfpBLGsCgYEA2AfYFtfT5Zd3m43NS+wB4e4veLSZqfzsVJ4FdJPtVZOrY7KiGCMInTgQMDY40gQ/VzpeQJv16MHS3ZljjXbyzSABQWcrORyeRXOp+jLCahElk8VCXaPRhDmVPkDNr5wi1MmVE/uD0H+WYnjkgSwK8m5NVSKeeKKN3y58gbNcdssCgYEA4oG7FZsAGjtVQbXoL0vC9iETGXouwf6Ul3pS8wMW+dMwDJVpp3WHUYjbBI0R46Qor1XFFjk76xSi3CSw3O2igyKXm38S9sp+DyCRgTbNbscdSFLhTl0Ly6/eKR18yZjb2qTIsAmD7Ik1aCFbxDuZWk4aVH2ATcoOQ2mB3IAMOV0CgYAOgqhfEGwpNb1Q83tgqB+QP2Fs6CSSKGzvWhXfuLfuUZbngW4l1OSVOtjLhDjY0nQ2tbLzAAbKdYpmXBE1xVGVuZqb/AQJOAThgV1fct1gJnqxrERC6ef+y6dcoRmvu/sS899RkklOTeOaBajDjGPH2OTU3Rhr3e9NmF/Ajn2ddQKBgEvKv8IherPe0iDYvLorbBZOO9V428sNLmIf6882oKpDqxgCwiavxdJDivdAi1CsB6PagiHtFndtJnjuSdeZCzhL+uqTz38oL3k9GIl9/0uIv7hgZVDfyGQyRlfrNtyIAbqZY71FKIPmAj575XAIBMBiCpxChw5cvzfUPL3Bru+v";
    public static void main(String[] args) throws Exception{


        //byte[] data = {1,0,1};
        //System.out.println(Base64.getEncoder().encodeToString(data));


        srcData = new byte[1];
        srcData[0]=10;

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);


        KeyPair keyPair= kpg.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println(publicKey.getFormat());
        System.out.println(privateKey.getFormat());


        byte[] pubKey = publicKey.getEncoded();
        byte[] priKey = privateKey.getEncoded();

        System.out.println(pubKey.length);
        System.out.println(priKey.length);


        priKey=Base64.getDecoder().decode(base64myPriKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);

        // 取私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(pkcs8KeySpec);
        //RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)pvkKey;
        //rsaPrivateKey.getPrivateExponent();
        pvkKey.getPrimeP().toByteArray();




        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(srcData);
        signResult = signature.sign();

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509keySpec = new X509EncodedKeySpec(pubKey);
        publicKey = keyFactory.generatePublic(x509keySpec);

        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(pubKey));
        byte[] tempbytes = rsaPublicKey.getModulus().toByteArray();
        System.out.println("rsa = " + rsaPublicKey.getModulus().toByteArray()[0]);

        tempbytes = rsaPublicKey.getPublicExponent().toByteArray();


        System.out.println(Base64.getEncoder().encodeToString(tempbytes));
        System.out.println("------------");



        signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(srcData);
        boolean flag  = signature.verify(signResult);



        System.out.println(flag);

        System.out.println(signResult.length);
    }

    static void Test(){
        byte[] data = {1,0,1};
        System.out.println(Base64.getEncoder().encodeToString(data));

    }

}
