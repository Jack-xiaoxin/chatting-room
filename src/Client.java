import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {

    String message;

    public Client(String message) {
        this.message = message;
    }

    public static void main(String[] args) throws IOException {
        new Client("Hi").send();
    }

    public String send() throws IOException {
        int port = 9999;
        String host = "127.0.0.1";
        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        socket.shutdownOutput();
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while((len = inputStream.read(bytes)) != -1) {
            stringBuilder.append(new String(bytes, 0, len, "UTF-8"));
        }
        socket.close();
        inputStream.close();
        outputStream.close();
        return stringBuilder.toString();
    }
}
