import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer{
  private static Set<String> names = new HashSet<>();
  private static Set<PrintWriter> writers = new HashSet<>();
  private static final int SERVER_PORT = 59001;

  public static void main(String[] args) throws Exception {
    System.out.println("The chat server is runnig...");
    var pool = Executors.newFixedThreadPool(500);
    try(var listener = new ServerSocket((SERVER_PORT))) {
        while (true) {
          pool.execute(new Handler(listener.accept()));
        }
    }
  }

  private static class Handler implements Runnable {
    private String name;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public Handler(Socket socket) {
      this.socket = socket;
    }

    public void run(){
      try {
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
          out.println("SUBMITNAME");
          name = in.nextLine();
          if(name == null) {
           return; 
          }
          synchronized (names){
            if(!name.isBlank() && !names.contains(name)){
              names.add(name);
              break;
            }
          }
        }

        out.println("NAMESACCEPTED " + name);
        for (PrintWriter writer: writers) {
          writer.println("MESSAGE " + name + " has joined");
        }
        writers.add(out);

        while (true) {
          String input = in.nextLine();
          if(input.toLowerCase().startsWith("/quit")){
            return;
          }
          for (PrintWriter  writer: writers ){
            writer.println("MESSAGE " + name + ": " + input);
          } 
        }
      }catch (Exception e) {
        System.out.println(e);
      }finally{
        if(out != null){
          writers.remove(out);
        }
        if(name != null){
          System.out.println(name + " is leaving");
          names.remove(name);
          for(PrintWriter writer: writers){
            writer.println("MESSAGE " + name + " has left");
          }
        }
        try{
          socket.close();
        }catch(IOException e){

        }
      }
    }

  }
}

