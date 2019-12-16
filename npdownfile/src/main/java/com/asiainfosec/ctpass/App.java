package com.asiainfosec.ctpass;

import com.jcraft.jsch.*;

import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App {

    private static ChannelSftp sftp;

    private static Session session;
    /**
     * SFTP 登录用户名
     */
    private static String username;
    /**
     * SFTP 登录密码
     */
    private static String password;
    /**
     * 私钥
     */
    private static String privateKey;
    /**
     * SFTP 服务器地址IP地址
     */
    private static String host;
    /**
     * SFTP 端口
     */
    private static int port;

    public static void main(String[] args) {

        //ftp_udb jtnp_201812
        host = "10.128.44.6";
        port = 5151;
        username = "ftp_udb";
        password = "jtnp_201812";


        JSch jsch = new JSch();

        try {
            session = jsch.getSession(username, host, 5151);
            System.out.println("get session success ");

            session.setPassword(password);

            session.setUserInfo(new UserInfo() {
                @Override
                public String getPassphrase() {
                    return null;
                }

                @Override
                public String getPassword() {
                    return password;
                }

                @Override
                public boolean promptPassword(String s) {
                    return true;
                }

                @Override
                public boolean promptPassphrase(String s) {
                    return true;
                }

                @Override
                public boolean promptYesNo(String s) {
                    return true;
                }

                @Override
                public void showMessage(String s) {

                    System.out.println("showMessage");
                    System.out.println(s);

                }
            });

            Properties config = new Properties();
            //config.put("kex", "diffie-hellman-group1-sha1");
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            System.out.println("Session connected");

            /*
            Channel channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("channel connect" + channel.getId());

            sftp = (ChannelSftp) channel;
            System.out.println("congralation , link host@@@@@@@@=" + host + ".");
            */



        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if(sftp !=null){
                sftp.disconnect();
            }

            if(session != null){
                session.disconnect();
            }
        }
    }
}
