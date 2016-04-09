package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger count = new AtomicInteger(0);
    public static ServerSocket startSocketServer() {
        System.out.println("startSocket");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(81);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverSocket;
    }

    static void dealRequest(ServerSocket serverSocket) {
        System.out.println("enter dealRequest");
        while (true) {
            //阻塞接受客户端输入
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                //count.getAndIncrement(); //先得值再自增 i++
                count.incrementAndGet(); //先自增再得值 ++i
                printCurCount();
                DealRequestThread drt = new DealRequestThread(socket);
                Thread td = new Thread(drt);
                td.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 监听输入
     * @param socket
     * @throws IOException
     */
    public static void readRequest(Socket socket) throws IOException {
        String line;
        BufferedReader br = null;
        PrintStream printStream = null;
        try{
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());
            //打印欢迎词
            printWelcome(printStream);
            //读取输入
            line = br.readLine();
            while(!"quit".equals(line)) {
                System.out.println("line:" + line);
                //处理业务操作
                doSomething(line, printStream);
                line = br.readLine();
            }
            System.out.println("line:" + line);
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(br != null)
                br.close();
            if(printStream != null)
                printStream.close();
        }
    }

    /**
     * 根据命令执行相关操作
     * @param command
     */
    private static void doSomething(String command, PrintStream printStream) {
        switch (command) {
            case "a":
                a(printStream);
                break;
            case "b":
                b(printStream);
                break;
            default:
                defaultDeal(printStream);
        }
    }

    private static void printWelcome(PrintStream printStream) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("******************** welcome ********************")
                     .append("                                                 ")
                     .append("                  欢迎使用此服务                  ")
                     .append("                                                 ")
                     .append("注：1、输入help或-h查看帮助                        ");
        printStream.println(stringBuilder.toString());
    }

    static void a(PrintStream printStream) {
        String sayStr = "my name is a!";
        System.out.println(sayStr);
        printStream.println(sayStr);
    }

    static void b(PrintStream printStream) {
        String sayStr = "my name is b!";
        System.out.println(sayStr);
        printStream.println(sayStr);
    }

    static void defaultDeal(PrintStream printStream) {
        String sayStr = "no match!";
        System.out.println(sayStr);
        printStream.println(sayStr);
    }

    static void printCurCount() {
        System.out.println("当前连接人数:" + count.get());
    }



    public static void main(String[] args) {
        dealRequest(startSocketServer());
        System.out.println("end");
    }
}
