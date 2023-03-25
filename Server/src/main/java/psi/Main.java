package psi;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        if (args.length == 0) {
            server.start(9090);
        } else {
            server.start(Integer.parseInt(args[0]));
        }
    }
}
