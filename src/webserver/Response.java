import  entity.*;

import java.io.*;
import java.util.ArrayList;

public class Response {

    Request request;
    DataOutputStream output;
    String version = "HTTP/1.1 ";
    String status;
    byte[] entityBody;
    ArrayList<String> headers = new ArrayList<>();

    public Response(OutputStream output) {
        this.output = new DataOutputStream(output);
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendResponse() throws IOException {
        if(request.gethttpMethod().equals(Method.GET)){
            doGet();
        }
        else if(request.gethttpMethod().equals(Method.POST)){
            doPost();
        }

    }


    /**
    * @Description:  simply get resource.
    * @time: 2019.5.17.
    */
    private  void  doGet() throws IOException {
        FileInputStream fis = null;
        try {
            //将web文件写入到OutputStream字节流中
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            int file_Length = (int) file.length();
            byte[] bytes = new  byte[file_Length];
            if (file.exists()) {
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, file_Length);
                String responseLine = "HTTP/1.1 200\r\n" + "Content-Type: text/html\r\n" + "Content-Length: " + file.length() + "\r\n" + "\r\n";
                output.write(responseLine.getBytes());
                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, file_Length);
                }
            } else {
                // file not found
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                        + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }

            output.flush();
        } catch (Exception e) {
            // thrown if cannot instantiate a File object
            System.out.println(e.toString());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
    * @Description:  after user's submit, send user-related entity data.(using send entity data in Request.)
    */
    void doPost() throws IOException {
        fillHeads(Status.OK);
        String preFormHtml = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Post_Response</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "User: \n" +
                "This is a Post_Response";

        String user = request.getSendEntityBody().substring(9, request.getSendEntityBody().indexOf("&"));

        fillEntity(preFormHtml + user + "Welcome! </body></html>");
        for(String head: headers){
            output.write(head.getBytes());
        }
        output.write(entityBody);
        output.flush();

    }

    private  void fillHeads(String status){
        headers.add(version);
        headers.add(status + "\r\n");

        headers.add(getContentType(request.getUri())+ "\r\n");

    }

    private void  fillEntity(String entity){
        entityBody = entity.getBytes();
    }


    /**
     * 设置返回文件的类型
     *
     * @param uri
     *
     */
    private String getContentType(String uri) {
        String ext = "";
        try {

            if(request.gethttpMethod().equals(Method.GET)) {
                ext = uri.substring(uri.indexOf(".") + 1);
            }
            else if (request.gethttpMethod().equals(Method.POST)){
                ext = MIME.HTML.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MIME NOT FOUND");
        }
        return  ext;
    }
}
