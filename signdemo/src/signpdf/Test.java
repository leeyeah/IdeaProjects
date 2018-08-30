package signpdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.ini4j.Wini;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

public class Test {

	public static final String INI_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "config.ini";
	
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
	public static final String KEYSTORE = Thread.currentThread().getContextClassLoader().getResource("").getPath()
			+ "my.keystore";

//	public static final String CERTPATH = Thread.currentThread().getContextClassLoader().getResource("").getPath()
//			+ "my.cer";

	public static final int KEYSIZE = 1024;
	public static final int VALIDITY = 1096;
	public static final String ALIAS = "zq";
	public static final char[] PASSWORD = "123".toCharArray();// keystory密码
	public static final String SRC = Thread.currentThread().getContextClassLoader().getResource("").getPath()
			+ "src.pdf";
	public static final String DEST = Thread.currentThread().getContextClassLoader().getResource("").getPath()
			+ "signed_dest.pdf";

	public static final String YZPATH = Thread.currentThread().getContextClassLoader().getResource("").getPath()
			+ "yz.png";


	public static void main(String[] args) {
		try {

			while(true) {
				System.out.println("请选择 #按q键退出程序");
				System.out.println("1.生成KeyStore");
				System.out.println("2.签名");
				System.out.println("3.校验");

				Scanner input = new Scanner(System.in);
				String number = input.next();
				
				if (number.equals("1")) {

					File fi =new File(INI_PATH);
					if(!fi.exists()) {
						System.out.println("缺少配置文件");
						return;
					}
					
					Wini ini = new Wini(new File(INI_PATH));
					
					String CN = ini.get("cert", "CN", String.class);
					String OU = ini.get("cert", "OU", String.class);
					String O = ini.get("cert", "O",String.class);
					String L = ini.get("cert", "L",String.class);
					String S = ini.get("cert", "S",String.class);
					String C = ini.get("cert", "C",String.class);

					System.out.println(String.format("CN=%s, OU=%s, O=%s ,L=%s ,S=%s ,C=%s",CN,OU,O,L,S,C));
					
					System.out.println("请设置密钥口令：");
					input = new Scanner(System.in);
					String passWord=input.next();
					
					KeyStore ks = KeyStore.getInstance("pkcs12");
		            ks.load(null, null);
		            
		            CertAndKeyGen keypair = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
		            X500Name x500Name = new X500Name(CN,OU,O,L,S,C);
		            keypair.generate(KEYSIZE);
		            
		            PrivateKey privateKey = keypair.getPrivateKey();
		            X509Certificate[] chain = new X509Certificate[1];
		            chain[0] = keypair.getSelfCertificate(x500Name, new Date(), (long)VALIDITY*24*60*60);
		            
		            // store away the key store
		            FileOutputStream fos = new FileOutputStream(KEYSTORE);
		            ks.setKeyEntry(ALIAS, privateKey, passWord.toCharArray(), chain);
		            ks.store(fos, passWord.toCharArray());
		            fos.close();
		            System.out.println("create Success");
		       
		        
				} else if (number.equals("2")) {

					System.out.println("请输入密钥口令：");
					input = new Scanner(System.in);
					String passWord=input.next();
					
					KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
					ks.load(new FileInputStream(KEYSTORE), passWord.toCharArray());
					PrivateKey pk = (PrivateKey) ks.getKey(ALIAS, passWord.toCharArray());
					Certificate[] chain = ks.getCertificateChain(ALIAS);
					SignatureUtils app = new SignatureUtils();
					// 读取图章图片，这个image是itext包的image
					Image image = Image.getInstance(YZPATH);
					app.sign(SRC, DEST, chain, pk, DigestAlgorithms.SHA1, null, CryptoStandard.CMS, "重要文件", "BeiJing",
							image);
					System.out.println("已生成文件。");

				} else if (number.equals("3")) {
					// 读取pdf文件 暂未实现
					BouncyCastleProvider bcp = new BouncyCastleProvider();
					Security.insertProviderAt(bcp, 1);
					PdfReader reader = new PdfReader(DEST);

					AcroFields fields = reader.getAcroFields();

					List<String> names = fields.getSignatureNames();
					for (String name : names) {
						System.out.println("Signature name: " + name);
						System.out.println("Signature covers whole document: " + fields.signatureCoversWholeDocument(name));
						PdfPKCS7 pk = fields.verifySignature(name);
						System.out.println("Subject: " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
						PublicKey publicKey = pk.getSigningCertificate().getPublicKey();
						Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
						// sign.initVerify(publicKey);
						System.out.println(publicKey.toString());
						sign.initVerify(publicKey);
						System.out.println(sign.verify(Base64.getDecoder().decode(publicKey.getEncoded())));
						// System.out.println(sign.verify(new
						// String("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKWXFLA3VO+m6vCRpoe28S3Cwc0R3d2E+4XUIvY+LoR8zRLciuPMX/NfUsmC/BHHHOr3yRc9+u/142Zyuxo3Xh2PxSq1yUw69OkZc79RaC971eMmkK/cZBBXIOPEVw+6p8OFff1WsEd42xHsFqa20j8HX7lsVYppIYua/0kPsDdTAgMBAAECgYBMBG2E8AHX+uLECblPeFaD0XKyt88IkLH/IAHk1Wr5e2mRjRldtkceLNaOl8ksSCIoZBQ2zUdTvaflMipewKg9YLie5mRi7FTiweDrvjliJOFXBm05ToT4gGl6V2GHPgC6y4mNtvrgmQONc0mSsPaZhEmq5NGw5m+lz0wjXXsjMQJBANcZ1fMyL0d3jYRqrPEHp3vR8cqaL/MEN/0iFPraN+ImR0D48E6MjHJZfu5fzp8Ix+BnFVLW5mTVNZCL/9ouyO8CQQDFE0eQCUZd1gdWT21LE/+q5XbvQCNnk58xmFYw6AztsXxvgQHLos6DBlOks7aHcoRZvhbl6JRTQanuUfnxZU/dAkEAubWemFdtpmA7mGWZZofbO/XosakpWMD9rditEF8y/FdcEWZb4xVJdLh79EZXV8r29nFJYboUNF433tJgVPkrlQJAP8PmMsedef0ncJjQfytgXRZT7kOtcPUF3FEzCi37COmVnqp6Pz5VXXWtmKwBUkAObM8hr5AadfnYFuF1YtLqDQJBAJ5ygOpJqxRFvNgtBgsN9UnuZ4bzGyhW6P8CkYq0Z/eZCn+1tK2uc5L+WAq/wQ7XOe8bj1ObMOpp56v4TYCehWs=").getBytes()));
						// System.out.println(sign.verify(publicKey.getEncoded()));
						// sign.verify(publicKey.getEncoded());
						// sig.update(data);
						// System.out.println("Document verifies: " +
						// sign.verify(Base64.getDecoder().decode(
						// "VL3uH0KrouAr1Gep2utlwdMGlhXeboTGoT/DdMkQB6ZnsNhNH4QO7VwYYWr+15rsxw/oW1OxhBRUfcoJPD+WAKy5hi3nN4zo+0P83fGl+eCs/eCLA/wlOTpEthRnQj2ATjgP8mzhXfM0Ump++6rY5LisXM71Eae8dh0rYj1Expo="
						// .getBytes())));

					}

				}else if(number.equals("q")) {
					break;
				}
			}
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
