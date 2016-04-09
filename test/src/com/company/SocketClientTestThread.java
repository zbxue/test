package com.company;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by xueph on 2016/4/10.
 */
public class SocketClientTestThread implements  Runnable {

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 81);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
