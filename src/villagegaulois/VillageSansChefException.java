package villagegaulois;

public class VillageSansChefException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
    // defaut
    public VillageSansChefException() {
    }

    //avec message personnalisé
    public VillageSansChefException(String message) {
        super(message);
    }
    
    // cause de l'exception
    public VillageSansChefException(Throwable cause) {
        super(cause);
    }

    // message et cause
    public VillageSansChefException(String message, Throwable cause) {
        super(message, cause);
    }
}