package com.leeyeah.demomvn.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


public class MyException implements HandlerExceptionResolver
{
    @Override
    public ModelAndView resolveException(javax.servlet.http.HttpServletRequest httpServletRequest,
                                         javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) {

        System.out.println(o.getClass().getTypeName());
        System.out.println(o);


        Map<String,Exception> model = new HashMap<>();
        model.put("ex",e);
        if(e instanceof ExcetionAA){

            return new ModelAndView("erroraa",model);
        }

        StackTraceElement[] arr = e.getStackTrace();

        /*
        for (StackTraceElement ste:arr
             ) {
            System.out.println(ste.toString());
        }

        System.out.println(arr[0].getMethodName());
        System.out.println(arr[0].getClassLoaderName());
        System.out.println(arr[0].getClassName());
        System.out.println(arr[0].getFileName());
        System.out.println(arr[0].getModuleName());
        System.out.println(arr[0].getLineNumber());
        System.out.println(arr[0].getModuleVersion());
        System.out.println(e.getMessage());
        System.out.println("----------------------------------------------------------");
        System.out.println(arr[0].toString());
        System.out.println("----------------------------------------------------------");
        System.out.println(o);

        */

        return null;
    }
}
