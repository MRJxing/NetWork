import java.io.IOException;
import java.util.Scanner;


/**
 * @author MR.J
 * @date 2019.5.19
 */
public class POPClient {


    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("USAGE: USER|PASS|DELE|STAT|RETR|QUIT|List ***");
        PopFunction popFunction = new PopFunctionImpl("pop.163.com", 110);

        String connectState = popFunction.getCommand();
        System.out.println(connectState);
        //连接给定服务器不成功
        if (!"+OK".equals(popFunction.getResult(connectState))) {
            throw new IOException("Connect error!");
        }

        Scanner scanner = new Scanner(System.in);

        /*
         * authorization */
        while (true) {

            System.out.println("Please Check in");
            System.out.println("USAGE: Firstly> USER [mailbox] ; Lastly>PASS [secret]");
            String[] commands = scanner.nextLine().split(" ");
            if (commands.length == 2 && commands[0].equals("USER")) {
                System.out.println(popFunction.user(commands[1]));
            } else {
                System.out.println("USAGE: Firstly> USER [mailbox] ; Lastly>PASS [secret], please continue");
                continue;
            }

            while (true) {
                String[] com = scanner.nextLine().split(" ");
                if (com.length == 2 && com[0].equals("PASS")) {
                    System.out.println(popFunction.pass(com[1]));
                } else {
                    System.out.println("USAGE: Firstly> USER [mailbox] ; Lastly>PASS [secret], please continue");
                    continue;
                }
                break;
            }
            break;
        }

        /* event handler  and update */
        while (true) {
            String[] cmd = scanner.nextLine().split(" ");
            switch (cmd[0]) {
                case "LIST":
                    if (2 == cmd.length) {
                        System.out.println(popFunction.list(Integer.parseInt(cmd[1])));
                    } else {
                        System.out.println(popFunction.list());
                    }
                    break;
                case "DELE":
                    popFunction.dele(Integer.parseInt(cmd[1]));
                    break;
                case "STAT":
                    System.out.println(popFunction.stat());
                    break;
                case "RETR":
                    popFunction.retr(Integer.parseInt(cmd[1]));
                    break;
                case "QUIT":
                    popFunction.quit();
                    return;
                default:
                    System.out.println("Please try again ");
            }
        }
    }
}
