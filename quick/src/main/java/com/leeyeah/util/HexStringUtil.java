package com.leeyeah.util;

//import jdk.incubator.http.HttpClient;
//import jdk.incubator.http.HttpRequest;
//import jdk.incubator.http.HttpResponse;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.concurrent.Flow;

public class HexStringUtil {


    /**
     * @param bytes 字节数组
     * @return 十六进制字符串。大写。
     */
    public static String bytes2HexStr(byte[] bytes) {

        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }

        return stringBuilder.toString().toUpperCase();
    }

    /**
     * @param hexStr 十六进制字符串。
     * @return 字节数组。
     */
    public static byte[] hexStr2bytes(String hexStr) {

        int length = hexStr.length() / 2;
        byte[] bytes = new byte[length];

        String temp = null;
        for (int i = 0; i < length; i++) {
            temp = hexStr.substring(i * 2, i * 2 + 2);
            bytes[i] = Integer.valueOf(temp, 16).byteValue();
        }
        return bytes;
    }


    /**
     * @param key key
     * @param data 输入的字节数组。
     * @return 经过HmacSHA1计算后的字节数组
     */
    public static byte[] computeHmacSha1(byte[] key, byte[] data) {

        byte[] result = null;
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            result = mac.doFinal(data);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    static void testhamcsha1() {

        //测试数据
        String hexStr = "FF01Feab";
        /* ctpass分配 */
        String keystr = "F182A6759AB42291002A65755513A481";
        byte[] data = hexStr2bytes(hexStr);
        byte[] key = hexStr2bytes(keystr);
        byte[] result = computeHmacSha1(key, data);


        String temp = bytes2HexStr(result);

        System.out.println(temp.toUpperCase());
    }


    /**
     * Http Post 请求示例
     */
    static void testHttpPost() {

        String url = "http://10.1.198.233:8835/querynp.ashx";
        //url="http://10.1.198.233:7001/Handler.ashx";
        //CTPass平台分配
        String keystr = "F182A6759AB42291002A65755513A481";
        byte[] key = hexStr2bytes(keystr);

        //假设有上送appid、seqID、sign 参数
        String appid = "35555555";
        String seqID = "dddddd";
        String sign = null;

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(appid);
        arrayList.add(seqID);
        //除sign以外按升序拼接，得到source
        arrayList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        String source = "";
        for (String item : arrayList
        ) {
            source = source.concat(item);
        }

        //计算sign
        byte[] srcbytes = source.getBytes(Charset.forName("UTF-8"));
        byte[] signbytes = computeHmacSha1(key, srcbytes);
        sign = Base64.getEncoder().encodeToString(signbytes);

        //POST操作
        //POST Body参数
        StringBuilder paramBuilder = new StringBuilder();
        try {

            paramBuilder.append("appid=" + URLEncoder.encode(appid, "UTF-8"));
            paramBuilder.append("&seqid=" + URLEncoder.encode(seqID, "UTF-8"));
            //paramBuilder.append("&addr="+URLEncoder.encode("http://www.b.com?od=2&c=d","UTF-8"));
            //paramBuilder.append("&mdn=18911069509");
            paramBuilder.append("&sign=" + URLEncoder.encode(sign, "UTF-8"));

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest.BodyPublisher bodyPublisher = new HttpRequest.BodyPublisher() {
                @Override
                public long contentLength() {
                    return 0;
                }

                @Override
                public void subscribe(Flow.Subscriber<? super ByteBuffer> subscriber) {

                }
            };
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    //.POST(HttpRequest.BodyProcessor.fromString(paramBuilder.toString()))
                    .POST(HttpRequest.BodyPublishers.ofString(paramBuilder.toString()))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("**the rsp**");
            System.out.println( response.body());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {


        testHttpPost();
        //testhamcsha1();
        //7B3E94AFAED7E7F4CA3C1393B0024BFFB36A12FE
        //7B3E94AFAED7E7F4CA3C1393B0024BFFB36A12FE

        /*
        String tmp = "0a14c80a";
        String substr = tmp.substring(6, 8);
        System.out.println(substr);
        System.out.println(Integer.valueOf("ff", 16).byteValue());
        byte[] bytes = hexStr2bytes("0a14c80a");
        byte[] src = new byte[]{10, 20, -56, 10};
        System.out.println(bytes2HexStr(src));
        //0a14c80a
        //ChTICg==
        //ChTICg==
        //byte[] src = hexStr2bytes("0a14c80a");
        System.out.println(src);
        String bstr = Base64.getEncoder().encodeToString(src);
        System.out.println(bstr);

        Error:(3, 21) java: 程序包 jdk.incubator.http 不可见
  (程序包 jdk.incubator.http 已在模块 jdk.incubator.httpclient 中声明, 但该模块不在模块图中)
        */
    }

}

