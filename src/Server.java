import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {

    public static void main(String[] args) throws IOException {
        Server.start();
    }

    public static void start() throws IOException {
        //定义端口，然后定义一个ServerSocket
        int port = 9999;
        ServerSocket server = new ServerSocket(port);
        Socket socket;
        InputStream inputStream;
        OutputStream outputStream;

        while(true) {
            //ServerSocket accept a socket
            socket = server.accept();

            //define a InputStream
            inputStream = socket.getInputStream();

            //define a byte array, to get input
            byte[] bytes = new byte[1024];
            int len = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while((len = inputStream.read(bytes)) != -1) {
                stringBuilder.append(new String(bytes, 0, len, "UTF-8"));
            }
            String message = stringBuilder.toString();

            outputStream = socket.getOutputStream();
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
            socket.shutdownOutput();

            outputStream.close();
            inputStream.close();
            socket.close();
        }
    }

}
