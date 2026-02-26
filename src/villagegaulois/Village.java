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
		StringBuilder chaine = new StringBuilder(vendeur.getNom());
		chaine.append(" cherche un endroit pour vendre ");
		chaine.append(nbProduit);
		chaine.append(" ");
		chaine.append(produit);
		chaine.append(".\n");
		
		int indiceEtalLibre = this.marche.trouverEtalLibre();
		if(indiceEtalLibre==-1) {
			System.out.println("Aucun etal disponible");
		}
		else {
			marche.etals[indiceEtalLibre].occuperEtal(vendeur, produit, nbProduit);
			chaine.append("Le vendeur ");
			chaine.append(vendeur.getNom());
			chaine.append(" vend des ");
			chaine.append(produit);
			chaine.append(" à l'étal n°");
			chaine.append(nbProduit);
			chaine.append("\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder(vendeur.getNom());
		Etal[] etalsProduits = marche.trouverEtals(produit);
		int nombreEtals = etalsProduits.length;
		
		if(nombreEtals <=0) {
			chaine.append("Il n'y a pas de vendeur qui propose des ");
			chaine.append("Il n'y a pas de vendeur qui propose des ");
			chaine.append(produit);
			chaine.append(" au marché.\n");
			
			return chaine.toString();
		}
		else if(nombreEtals == 1) {
			Gaulois vendeur = etalsProduits[0].getVendeur();
			chaine.append("Seul le vendeur ");
			chaine.append(vendeur.getNom());
			chaine.append(" propose des");
			chaine.append(produit);
			chaine.append(" au marché.\n");
			return chaine.toString();
		}
		else {
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for(int i=0; i<nombreEtals; i++) {
				chaine.append("- ");
				chaine.append(etalsProduits[i].getVendeur().toString());
				chaine.append("\n");
				
				return chaine.toString();
			}
			
		}
		
	}
	
	public static class Marche{
		Etal etals[];
		
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
		}
		
		int trouverEtalLibre() {
			for(int i=0; i<etals.length; i++) {
				if(etals[i]!=null && !etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		Etal[] trouverEtals(String produit) {
			int nbEtals = 0;
			for(int i=0; i<etals.length; i++) {
				if(etals[i]!=null && etals[i].contientProduit(produit)){
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