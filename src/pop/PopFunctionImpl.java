import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/**
 * @author: MR.J
 * @description:
 * @date 2019/5/19
 */
public class PopFunctionImpl implements PopFunction {

    private Socket socket = null;

    private boolean debug = true;

    private BufferedReader in;
    private BufferedWriter out;


    PopFunctionImpl(String server, int port) throws UnknownHostException, IOException {
        try {
            //在新建socket的时候就已经与服务器建立了连接
            socket = new Socket(server, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (Exception e) {
            System.out.println("Can't connect your pop server !");
            e.printStackTrace();

        } finally {

            System.out.println("建立连接！");
        }

    }


    @Override
    public String getResult(String line) {

        StringTokenizer st = new StringTokenizer(line, " ");

        return st.nextToken();
    }


    /**
     * @return return state information from server.
     * @description: get the first line only.
     */
    @Override
    public String getCommand() {

        String line = "";
        try {
            line = in.readLine();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return line;
    }

    /**
     * @return java.lang.String
     * @author MR.j
     * @date 2019/5/20
     * @description: get detail return command.
     */
    public String getDetailMessage(BufferedReader in)  {

        String message = "";

        String line = "";

        try {
            while (!".".equalsIgnoreCase(line)) {

                line = in.readLine();
                message = message + line + "\n";


            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return message;
    }

    @Override
    public String sendCommand(String str) throws IOException {
        out.write(str);
        out.newLine();
        out.flush();
        System.out.println("Send command successfully!");
        return getCommand();
    }


    @Override
    public String user(String user) throws IOException {

        return sendCommand("user " + user);
    }


    @Override
    public String pass(String password) throws IOException {
        return sendCommand("pass " + password);
    }


    @Override
    public String stat() throws IOException {
        return sendCommand("stat");
    }

    @Override
    public String list() throws IOException {
        return sendCommand("list");
    }


    @Override
    public String list(int mailNumber) throws IOException {

        return sendCommand("list " + mailNumber);
    }


    @Override
    public void retr(int mailNum) throws IOException, InterruptedException {

        String result = null;

        result = getResult(sendCommand("retr " + mailNum));

        if (!"+OK".equals(result)) {

            throw new IOException("接收邮件出错!");
        }

        System.out.println("第" + mailNum + "封");
        System.out.println(getDetailMessage(in));
        Thread.sleep(3000);
    }

    @Override
    public String quit() throws IOException {

        return sendCommand("QUIT");

    }


    @Override
    public void dele(int mailNum) throws IOException {
        String result;
        result = getResult(sendCommand("DELE " + mailNum));
        if (!"+OK".equals(result)) {

            throw new IOException("未能正确退出");
        }
    }
}
