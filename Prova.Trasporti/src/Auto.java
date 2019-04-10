public class Auto extends MezzoSuRuota implements Driveable {

    void move () {

        System.out.println("L'auto si muove sull'asfalto");

    }

    void tipoTrasporto() {
        System.out.println("L'auto trasporta persone ");
    }

    public String getType(){
        String s= "una patente di tipo";
        return s;
    }
    public String getLicense() {
        String s = "B";
        return s;
    }

    public String getPrice() {
        String s = "400 euro";
        return s;
    }


}


