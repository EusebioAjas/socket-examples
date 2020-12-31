import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CapitalizeClient {
  private static final String SERVER_IP = "localhost";
  private static final int SERVER_PORT = 59898;

  public static void main(String[] args) throws IOException {
    try (var socket = new Socket(SERVER_IP, SERVER_PORT)) {
      var scanner = new Scanner(System.in);
      var in = new Scanner(socket.getInputStream());
      var out = new PrintWriter(socket.getOutputStream(), true);
      while (scanner.hasNextLine()) {
        out.println(scanner.nextLine());
        System.out.println(in.nextLine());
      }
    }
  }
}
