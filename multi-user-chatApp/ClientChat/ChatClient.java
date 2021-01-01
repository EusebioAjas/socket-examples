import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatClient {
  public static final String SERVER_IP = "localhost";
  public static final int SERVER_PORT = 59001;
  Scanner in;
  PrintWriter out;
  JFrame frame = new JFrame("chatter");
  JTextField textField = new JTextField(50);
  JTextArea messageArea = new JTextArea(16, 50);

  public ChatClient(){
    textField.setEditable(false);
    messageArea.setEditable(false);
    frame.getContentPane().add(textField, BorderLayout.SOUTH);
    frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
    frame.pack();

    textField.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        out.println(textField.getText());
        textField.setText("");
      }
    });
  }

  private String getName(){
    return JOptionPane
      .showInputDialog(
      frame, 
      "Choose a screen name: ", "Screen name selection",
      JOptionPane.PLAIN_MESSAGE);
  }

  private void run() throws IOException{
    try{
      var socket = new Socket(SERVER_IP, SERVER_PORT);
      in = new Scanner(socket.getInputStream());
      out = new PrintWriter(socket.getOutputStream(), true);

      while (in.hasNextLine()) {
        var line = in.nextLine();
        if(line.startsWith("SUBMITNAME")){
          out.println(getName());
        }else if(line.startsWith("NAMESACCEPTED")){
          this.frame.setTitle("Chatter - " + line.substring(13));           
          textField.setEditable(true);
        }else if(line.startsWith("MESSAGE")){
          messageArea.append(line.substring(8) + "\n");
        }
      }
    } finally {
      frame.setVisible(false);
      frame.dispose();
    }
  }
  
  public static void main(String[] args) throws Exception {
    var client = new ChatClient();
    client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    client.frame.setVisible(true);
    client.run();
  }
}
