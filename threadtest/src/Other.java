public class Other implements Runnable {
    public void otherThingsToDo(){
        System.out.println("Sto facnedo altre robe");
    }
    public void doSomething(){
        System.out.println("Sto facendo robe");
        otherThingsToDo();
    }
    public void run() {
        System.out.println("Sto partendo");
        doSomething();
    }
}
