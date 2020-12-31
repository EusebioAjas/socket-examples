import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class CapitalizeServer {
  private static final int SERVER_PORT = 59898;

  public static void main(String[] args) throws IOException {
    try (var listener = new ServerSocket(SERVER_PORT)) {
      System.out.println("The capitalization server is running...");
      var pool = Executors.newFixedThreadPool(20);
      while (true) {
        pool.execute(new Capitalizer(listener.accept()));
      }
    }
  }


}
