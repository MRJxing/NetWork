import java.io.BufferedReader;
import java.io.IOException;


/**
 * @author MR.J
 * @date: 2019.5.19
 */
public interface PopFunction {


    /**
     * @return back command from server.
     */
    String getCommand();


    /**
     * @param line : first line of returning command(state).
     * @return “OK“ or ”ERROR“;
     * @author MR.j
     * @date 2019/5/20
     * @description: return ok or error of sending's back command.
     */
    String getResult(String line);

    /**
     * @param str : given the sending command(completed).
     * @return :string
     * @author MR.j
     * @date 2019/5/20
     * @description: return the result(getCommand()) state line.
     */
    String sendCommand(String str) throws IOException;

    /**
     * @param user: given username
     * @return java.lang.String
     * @author MR.j
     * @date 2019/5/20
     */
    String user(String user) throws IOException;

    /**
     * @param password give password
     * @return java.lang.String
     * @author MR.j
     * @date 2019/5/20
     */
    String pass(String password) throws IOException;

    /**
     * @return java.lang.String
     * @author MR.j
     * @date 2019/5/20
     */
    String stat() throws IOException;


    /**
     * @return java.lang.String
     * @author MR.j
     * @date 2019/5/20
     */
    String list() throws IOException;


    /**
     * @param mailNum given the mail number.
     * @return java.lang.String
     * @author MR.j
     * @date 2019/5/20
     */
    String list(int mailNum) throws IOException;


    /**
     * @param mailNum give the mail number
     * @author MR.j
     * @date 2019/5/20
     */
    void dele(int mailNum) throws IOException;


    /**
     * @param mailNum give the mail number
     * @return void
     * @author MR.j
     * @date 2019/5/20
     */
    void retr(int mailNum) throws IOException, InterruptedException;

    /**
     * @return java.lang.String
     * @author MR.j
     * @date 2019/5/20
     */
    String quit() throws IOException;

}