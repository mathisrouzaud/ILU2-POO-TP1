package villagegaulois;

public class VillageSansChefException extends Exception {
	//TODO Bonne pratique exception personnalisée
    public VillageSansChefException(String message) {
        super(message);
    }
}