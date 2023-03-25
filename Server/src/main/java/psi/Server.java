package psi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    private void listen(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    private Socket accept() throws Exception {
        if (serverSocket == null) {
            throw new IllegalArgumentException("Server socket is null. Cannot accept new connections.");
        }
        return serverSocket.accept();
    }

    public void start(int port) throws Exception {
        listen(port);
        while (true) {
            Socket client = accept();
            ClientThread clientThread = new ClientThread(client);
            clientThread.start();
        }
    }

    private static class ClientThread extends Thread {

        private final BufferedWriter writer;
        private final BufferedReader reader;

        public ClientThread(Socket clientSocket) throws Exception {
            if (clientSocket == null) {
                throw new IllegalArgumentException("Client socket can not be null.");
            }
            this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        public void run() {
            char[] buffer = new char[255];
            while (true) {
                try {
                    int readChars = reader.read(buffer);
                    if (readChars == -1) continue;
                    String line = new String(buffer, 0, readChars);
                    if (!line.isEmpty() && readChars - 1 == line.charAt(0)) {
                        line = line.substring(1);
                        String output = reverseString(line);
                        System.out.printf("Input: %s\nOutput: %s\n\n", line, output);
                        writer.write(output + "\n");
                        writer.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        private String reverseString(String in) {
            if (in == null) {
                return "";
            }
            return new StringBuilder(in).reverse().toString();
        }
    }


}
