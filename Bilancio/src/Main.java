
public class Main {
    public Main() {
    }

    public static void main(String[] var0) {
        if (var0.length != 1) {
            System.out.println("Please specify port number");
            System.exit(-1);
        }

        Server var1 = new Server(Integer.parseInt(var0[0]));
        var1.go();
    }
}
