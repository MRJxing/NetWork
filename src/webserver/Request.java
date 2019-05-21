
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @author MR.J
 * @Description: This part is used to parse data in request from client.
 */
public class Request {
    private InputStream input;
    private  String uri;
    private  String httpMethod;
    private  String httpVersion;
    private  String requestLine = "";
    private  String sendEntityBody = "";

    /** save maps in headlines.
     */
    Map<String, String> requestHeadLine = new HashMap<String, String>();


    public Request(InputStream input) {
        this.input = input;
    }
    public  void  parse(){

        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.print(request.toString());
        parse2ThreeParts(request.toString());
        httpMethod = requestLine.split(" ")[0];
        uri = requestLine.split(" ")[1];
        httpVersion = requestLine.split(" ")[2];

    }

    private void parse2ThreeParts(String requestString) {
        int index1, index2,index3;
        index1 = requestString.indexOf("\r\n");
        // get the first line of the request.
        requestLine = requestString.substring(0,index1);

        index2 = requestString.lastIndexOf("\r\n");
        // get the head line of the request.
        String heads = requestString.substring( index1 + 2, index2);

        do{
           index3 = heads.indexOf("\r\n") ;
           String line = heads.substring(0, index3);
           String [] splits = line.split(": ");
           requestHeadLine.put(splits[0],splits[1]);
           heads = heads.substring(index3 +2);
        }while (!"".equals(heads));


        //if post method, there's gonna be request entity.
        sendEntityBody = requestString.substring(index2 + 2);

    }



    public  String getUri(){
        return  uri;
    }

    public String gethttpMethod() {
        return httpMethod;
    }

    public String getSendEntityBody() {
        return sendEntityBody;
    }


}
