import java.io.*;


public class Balancer implements Runnable{


    Server con =new Server();
    String value=null;


    public void run(){
        while(true) {


            try {
                var fw = new FileWriter("lastbalance.txt", false);
                value=String.valueOf(con.getBalance());
                fw.write("last balance is: " + value);
                fw.close();
                Thread.sleep(10000);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }


    }



}
