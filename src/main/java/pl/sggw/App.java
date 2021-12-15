package pl.sggw;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
public class App {



    public static void main(String[] args) throws IOException, InterruptedException {

        Database db = new Database();
        Api api = new Api(db);

        ServerSocket serverSocket = new ServerSocket(System.getenv("PORT"));
        while (true) {
            listenAndServe(serverSocket, api);
        }

    }

    private static void listenAndServe(ServerSocket serverSocket, Api api) throws IOException, InterruptedException {
        Socket socket = serverSocket.accept();
        Thread thread = new Thread(() -> {
            try {
                serveRequest(socket, api);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private static void serveRequest(Socket socket, Api api) throws IOException, InterruptedException {
        InputStream socketInputStream = socket.getInputStream();
        OutputStream socketOutputStream = socket.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socketOutputStream));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socketInputStream));
        String line = null;
        String request = "";
        Boolean isPost = false;
        while (((line = reader.readLine()) != null) && line.length()>0) {
            if(request.equals(""))
                request = line;
            if(request.contains("POST"))
                isPost = true;
            System.out.println("wczytane: " + line);
        }

        StringBuilder payload = new StringBuilder();
        if(isPost){
            System.out.println("Its post");
            while(reader.ready()){
                payload.append((char) reader.read());
            }
            System.out.println("Payload data is: "+payload.toString());
        }

        writer.write("HTTP/1.1 200 OK\n");
        writer.write("Connection: close\n");
        writer.write("Content-Type: text/html\n\n");
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Biblioteka</title>\n" +
                "    <link rel=\"stylesheet\" href=\"style.css\">\n" +
                "  </head>\n" +
                "  <body>\n" +
                api.call(request,payload.toString()) +
                "  </body>\n" +
                "</html>");
        writer.flush();
        writer.close();
    }

}