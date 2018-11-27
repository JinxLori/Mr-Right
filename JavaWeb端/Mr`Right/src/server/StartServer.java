package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
            ServerSocket serverSocket = new ServerSocket(30000);
            System.out.println("服务器已连接");
            while(true){
                System.out.println("123");
                Socket socket = serverSocket.accept();
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write("你好，我是服务器\n".getBytes("utf-8"));
                outputStream.close();
                socket.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
