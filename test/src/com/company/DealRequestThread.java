package com.company;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by xueph on 2016/4/2.
 */
public class DealRequestThread implements Runnable{
    private Socket socket;
    public DealRequestThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            Main.readRequest(socket);
            Main.count.decrementAndGet();
            Main.printCurCount();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
