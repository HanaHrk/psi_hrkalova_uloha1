import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            int port = Integer.parseInt(args[0]);
            startWithPort(port);
        } else if (args.length == 2) {
            int port = Integer.parseInt(args[0]);
            InetAddress ipAddress = InetAddress.getByName(args[1]);
            startWithAddressAndPort(ipAddress, port);
        } else if (args.length == 3) {
            int port = Integer.parseInt(args[0]);
            InetAddress ipAddress = InetAddress.getByName(args[1]);
            String message = args[2];
            startWithAddressAndPortInline(ipAddress, port, message);
        } else {
            System.out.println("Program is expecting parameteters (port, address, message)");
        }
    }


    private static void startWithAddressAndPort(InetAddress inetAddress, int port) throws Exception {
        try (Socket socket = new Socket()) {
            SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
            socket.connect(socketAddress);
            Scanner scanner = new Scanner(System.in);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            while (true) {
                System.out.print("Input: ");
                String input = scanner.nextLine();
                bw.write(((char) input.length()) + input);
                bw.flush();
                String output = br.readLine();
                System.out.print("Output: ");
                System.out.println(output);
                System.out.println();
            }
        }
    }

    private static void startWithPort(int port) throws Exception {
        startWithAddressAndPort(InetAddress.getLocalHost(), port);
    }


    private static void startWithAddressAndPortInline(InetAddress inetAddress, int port, String message) throws Exception {
        try (Socket socket = new Socket()) {
            SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
            socket.connect(socketAddress);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            bw.write(((char) message.length()) + message);
            bw.flush();
            String output = br.readLine();
            System.out.println("Input: " + message);
            System.out.print("Output: ");
            System.out.println(output);
            System.out.println();
        }
    }
}
