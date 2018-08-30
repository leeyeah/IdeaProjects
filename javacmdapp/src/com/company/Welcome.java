package com.company;


import jdk.dynalink.StandardOperation;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;
import java.net.*;

public class Welcome {

    static void TestFI(Say item,String str){
        System.out.println(item.doSay(str));


    }


    public static void main(String[] args) throws IOException, InterruptedException {

       // FileA
        Path parentPath = Paths.get("/Users/lee/a.txt");
        //Files.createFile(parentPath);

        //FileInputStream fis = new FileInputStream("/Users/lee/a.txt");


        AsynchronousFileChannel channel = AsynchronousFileChannel.open(parentPath);
        ByteBuffer buffer = ByteBuffer.allocate(10000);
        channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println(attachment.array().length +"-----"+result);

            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });

        Thread.sleep(1000);

        return;









        /*
        Predicate<String> predicate = input->input.length()>2;
        System.out.println(predicate.test("ac"));

        Converter<String,Integer> converter = null;//(from)->Integer.valueOf(from) ;
        converter=Integer::valueOf;

        String obj = new String("123455");
        converter=obj::compareToIgnoreCase;
        System.out.println(converter.convert("123455"));



        PersonFactory<Person> factory = Person::new;
        Person p = factory.create("jim","green");
        System.out.println(p.firstName +"  "+p.lastName);
              */

        /*
        List<String> name = Arrays.asList("ab", "aa", "ac");
        Collections.sort(name, (a, b) -> {
            return a.compareToIgnoreCase(b);
        });
        */






/*
        Map<String, String> map = new HashMap<>(10);
        map.put("s01", "01");
        map.put("s02", "02");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());

        }   */
    }
}
