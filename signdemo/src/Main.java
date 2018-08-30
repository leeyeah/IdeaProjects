//https://www.jb51.net/article/135576.htm
/*
不可读类
        比如sun.security.x509，在java9中归到java.base模块中，但是该模块没有export该package
        可以通过运行的时候添加--add-exports java.base/sun.security.x509=ALL-UNNAMED来修改exports设定
        内部类
        比如sun.misc.Unsafe，原本只想让oracle jdk team来使用，不过由于这些类应用太广泛了，为了向后兼容，java9做了妥协，只是将这些类归到了jdk.unsupported模块，并没有限定其可读性。
Error:(30, 20) java: 程序包 sun.security.x509 不可见
  (程序包 sun.security.x509 已在模块 java.base 中声明, 但该模块未将它导出到未命名模块)


**/
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println(Main.class.getModule().toString());
    }
}


//--add-exports <module>/<package>=<target-module>(,<target-module>)*
//--add-exports java.base/sun.security.x509=ALL-UNNAMED
//--add-exports java.base/sun.security.provider=ALL-UNNAMED,java.base/sun.security.pkcs=ALL-UNNAMED,java.base/sun.security.util=ALL-UNNAMED,java.base/sun.security.x509=ALL-UNNAMED