public class Treno extends MezzoSuRotaia implements Advertisable, Driveable{

    void start() {

        System.out.println("Il treno è in partenza");
    }
    void move () {

        System.out.println("Il treno si muove sui binari");

    }

    void tipoTrasporto() {
        System.out.println("Il treno trasporta persone, auto e moto");
    }

    public String getName() {
        String s= "UN TRENO";
        return s;
    }
    public String getSlogan() {
        String s= "Non avrai dove metterlo, ma vuoi mettere l'invidia dei vicini?";
        return s;
    }
    public String getType(){
        String s= "un brevetto:";
        return s;
    }
    public String getLicense() {
        String s = "GUIDA MEZZI PUBBLICI SU BINARI";
        return s;
    }

    public String getPrice() {
        String s = "2500 euro";
        return s;
    }
}

