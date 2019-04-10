public class DrivingSchool {

    void typeOfLicense(Driveable D){

        System.out.println("");
        System.out.println("Per poter guidare il mezzo, hai bisogno di");
        System.out.println(D.getType());
        System.out.println(D.getLicense());
        System.out.println("----------------------");
        System.out.println("");
        System.out.println("Il prezzo è di: ");
        System.out.println(D.getPrice());

    }
}
