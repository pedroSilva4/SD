import java.io.*;
import java.net.*;

class Client {
  public static void main(String[] args) {
    String host = args[0];
    int port = Integer.parseInt(args[1]);
    
    try(Socket socket = new Socket(host, port)) {
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter out = new PrintWriter(socket.getOutputStream());

      while(true) {
        String input = stdin.readLine();
        if(input == null) break;
        // stdin -> Stub
        // Stub -> server
        out.println(input);
        out.flush();
        String response = in.readLine();
        System.out.println("Response: " + response);
      }
      socket.close();
      System.out.println("Connection closed!");
    } catch(IOException e) { e.printStackTrace(); }
  }
}
