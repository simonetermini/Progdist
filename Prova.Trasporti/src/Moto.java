public class Moto extends MezzoSuRuota implements Advertisable, Driveable{

    void move () {

        System.out.println("La moto si muove sull'asfalto");

    }

    void tipoTrasporto() {
        System.out.println("La moto trasporta 5 persone a Catania");
    }
    public String getName() {
        String s= "UNA MOTO";
        return s;
    }
    public String getSlogan() {
        String s= "Se non cadi dopo il primo giro, sei a metà dell'opera!";
        return s;
    }
    public String getType(){
        String s= "una patente di tipo";
        return s;
    }
    public String getLicense() {
        String s = "A";
        return s;
    }

    public String getPrice() {
        String s = "250 euro";
        return s;
    }



}