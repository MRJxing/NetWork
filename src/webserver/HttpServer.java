import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * @author MR.J
 */
public class HttpServer{

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "Resource";

    // 关闭服务命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";



    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        //等待连接请求
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            //服务器套接字对象
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 循环等待一个请求
        while (true) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                //accept() listener;
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // create request
                Request request = new Request(input);
                request.parse();

                if (request.getUri().equals(SHUTDOWN_COMMAND)) {
                    break;
                }

                // response
                Response response = new Response(output);
                response.setRequest(request);
                response.sendResponse();

                //close
                socket.close();
                input.close();
                output.close();

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
