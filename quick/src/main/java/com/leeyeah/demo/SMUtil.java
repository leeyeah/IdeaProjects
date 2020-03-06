package com.leeyeah.demo;



import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.signers.DSAEncoding;
import org.bouncycastle.crypto.signers.StandardDSAEncoding;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
//     org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;


import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import static org.zz.gmhelper.SM2Util.CURVE;

// HexStr转换成国密公私钥
//https://blog.csdn.net/binchel/article/details/88063161?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task

public class SMUtil {

    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
    private static ECDomainParameters ecDomainParameters = new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    private final static int SM2_KEY_LEN = 32;

    public static ECPublicKey encodEcPublicKey(byte[] publickey)
            throws Exception {
        KeyFactory factory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        if (publickey.length != SM2_KEY_LEN * 2)
            throw new RuntimeException("err key. ");
        BigInteger X = new BigInteger(1, Arrays.copyOfRange(publickey, 0, SM2_KEY_LEN));
        BigInteger Y = new BigInteger(1, Arrays.copyOfRange(publickey, SM2_KEY_LEN, SM2_KEY_LEN * 2));
        ECPoint point = CURVE.createPoint(X, Y);

        //org.bouncycastle.jce.spec.ECParameterSpec ecParameterSpec = new org.bouncycastle.jce.spec.ECParameterSpec()
        ECParameterSpec ecParameterSpec = new ECParameterSpec(x9ECParameters.getCurve(),x9ECParameters.getG(),x9ECParameters.getN());
        ECPublicKeySpec keySpec ;
        //keySpec = new ECPublicKeySpec(point,ecDomainParameters);
        keySpec = new ECPublicKeySpec(point,ecParameterSpec);

        return (ECPublicKey) factory.generatePublic(keySpec);
    }

    public static ECPrivateKey encodEcPrivateKeyKey(byte[] privatekey)
            throws Exception {
        KeyFactory factory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        if (privatekey.length != SM2_KEY_LEN)
            throw new RuntimeException("err key. ");
        ECParameterSpec ecParameterSpec = new ECParameterSpec(x9ECParameters.getCurve(),x9ECParameters.getG(),x9ECParameters.getN());

        //原来的
        //ECPrivateKeySpec keySpec = new ECPrivateKeySpec(new BigInteger(1, privatekey), ecDomecainParameters);
        ECPrivateKeySpec keySpec = new ECPrivateKeySpec(new BigInteger(1,privatekey),ecParameterSpec);
        return (ECPrivateKey) factory.generatePrivate(keySpec);
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        ECParameterSpec ecParameterSpec = new ECParameterSpec(x9ECParameters.getCurve(),x9ECParameters.getG(),x9ECParameters.getN());

        //keyPairGenerator.initialize(ecDomainParameters, new SecureRandom());
        keyPairGenerator.initialize(ecParameterSpec,new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }


    public void genSigWithRS(byte[] rbytes,byte[] sbytes){

        //BigInteger r = new BigInteger(1,)
        //要保证大数是正数
        BigInteger r = new BigInteger(1, rbytes);
        BigInteger s = new BigInteger(1, sbytes);

        DSAEncoding encoding = StandardDSAEncoding.INSTANCE;



    }

    /**
     * 把纯R+S字节流转换成DER编码字节流
     * @param rbytes
     * @param sbytes
     * @return
     * @throws IOException
     */
    public static byte[] encodeSM2SignToDER(byte[] rbytes,byte[] sbytes) throws IOException  {
        //要保证大数是正数
        BigInteger r = new BigInteger(1, rbytes);
        BigInteger s = new BigInteger(1, sbytes);
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(r));
        v.add(new ASN1Integer(s));
        return new DERSequence(v).getEncoded(ASN1Encoding.DER);
    }



    public void test(){

    }
}
