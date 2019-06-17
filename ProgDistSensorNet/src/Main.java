
public class Main {

    public static void main(String[] args) {
        if (args.length!=1){
            System.out.println("Please specify port number");
            System.exit(-1);
        }
        SensorServer server = new SensorServer(Integer.parseInt(args[0]));

        server.go();

        // write your code here
    }
}