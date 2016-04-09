package com.company;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Created by xueph on 2016/4/10.
 */
public class ClientTest {
    private static AtomicInteger count = new AtomicInteger();
    public static void main(String[] args) {
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> dealSocket()).start();;
        }
       /* SocketClientTestThread sctt = new SocketClientTestThread();
        Thread t = new Thread(sctt);
        t.start();*/
    }

    static void dealSocket() {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 81);
            int curCount = count.incrementAndGet();
            System.out.println("+curCount:" + curCount);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(socket != null) {
            try {
                socket.close();
                int curCount = count.decrementAndGet();
                System.out.println("-curCount:" + curCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
