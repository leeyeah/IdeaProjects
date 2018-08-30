package com.leeyeah;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Hello world!
 */
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }


    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        System.out.println(App.class.getModule().getName());

        //return;
        //logger.info("this is a log4j {}",123);
        //logger.info("exception:[{}]", new RuntimeException("ao"));

        Book book = new Book();
        book.setLevel(5);
        book.setPrice(100.0);
        book.setTime(LocalDateTime.now());
        book.setTitle("abc");

        book.setDay(Date.from(Instant.now()));
        System.out.println(book.getDay().toString());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        System.out.println(sdf.format(book.getDay()));

        //logger.error(book);


        String json = objectMapper.writeValueAsString(book);

        System.out.println(json);


        logger.info("{a:1}");
    }


}
