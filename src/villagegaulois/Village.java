package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder;
	}
	
	public static class Marche{
		Etal etals[];
		
		public Marche(int nbEtals) {
			for(int i=0; i<nbEtals; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
		}
		
		int trouverEtalLibre() {
			for(int i=0; i<etals.length; i++) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		Etal[] trouverEtals(String produit) {
			int nbEtals = 0;
			for(int i=0; i<etals.length; i++) {
				if(etals[i].contientProduit(produit)){
					nbEtals++;
				}
			}
			Etal[] tableau = new Etal[nbEtals];
			int indiceTableau = 0;
			for(int k=0; k<etals.length; k++) {
				if(etals[k].contientProduit(produit)){
					tableau[indiceTableau] = etals[k];
					indiceTableau++;
				}
			}
			return tableau;
		}
		
		Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		void afficherMarche() {
			for(int i=0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				}
			}
			int nbEtalsVides = trouverEtalLibre();
			if(nbEtalsVides > 0) {
				StringBuilder chaine = new StringBuilder("Il reste ");
				chaine.append(nbEtalsVides);
				chaine.append(" étals non utilisés dans le marché.\n");
				System.out.println(chaine);
			}
		}
	}
	
}