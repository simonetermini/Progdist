public class Other implements Runnable {
    public void otherThingsToDo(){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Sto facnedo altre robe");
    }

    public void doSomething() {
        System.out.println("");
        System.out.println("Sto facendo robe");
        otherThingsToDo();
    }
    public void run() {
        System.out.println("Sto partendo");
        doSomething();
    }
}
