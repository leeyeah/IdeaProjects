package one.sign;

import sun.security.tools.keytool.*;
import sun.security.x509.*;

import java.security.NoSuchAlgorithmException;


public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //CRLE
        // xtensions crlExtensions = null;

        CertAndKeyGen dd= new CertAndKeyGen("RSA",null);

        X509Key jo= new X509Key();
        System.out.print("ddddd");
    }
}


//--add-exports=java.base/sun.security.tools.keytool=one