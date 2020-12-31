import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Date;

public class DateServer {
  private static final int PORT = 59090;

  public static void main(String[] args) throws IOException {
    try (var listener = new ServerSocket(PORT)) {
      System.out.println("The date server is running...");
      while (true) {
        try (var socket = listener.accept()) {
          var out = new PrintWriter(socket.getOutputStream(), true);
          out.println(new Date().toString()); //sends the client the current datetime
        }
      }
    }
  }
}
