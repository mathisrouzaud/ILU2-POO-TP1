package histoire;
import villagegaulois.Etal;
import personnages.Gaulois;

public class ScenarieCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		etal.libererEtal();
		System.out.println("Fin du test");
		Gaulois acheteur = new Gaulois("Astérix", 8);
		
		etal.libererEtal();
		try {
			System.out.println("Test de quantité négative :");
			etal.acheterProduit(-1, acheteur);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("\nTest de l'étal non occupé :");
			etal.acheterProduit(5, acheteur);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("\nTest de l'acheteur null (doit être géré en interne) :");
			etal.occuperEtal(new Gaulois("Obélix", 25), "menhirs", 5);
			etal.acheterProduit(2, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin du test");
	}
		
}


