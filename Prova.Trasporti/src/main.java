public class main {

    public static void main (String [] args){


        Auto auto =new Auto();
        Moto moto =new Moto();
        Treno treno=new Treno();
        Banner4Life banner= new Banner4Life();
        DrivingSchool price= new DrivingSchool();

        treno.start();
        treno.move();
        treno.tipoTrasporto();

        auto.move();
        auto.tipoTrasporto();

        moto.move();
        moto.tipoTrasporto();

        banner.ShowMyBanner(treno);
        banner.ShowMyBanner(moto);


        price.typeOfLicense(auto);
        price.typeOfLicense(moto);
        price.typeOfLicense(treno);

    }
}
