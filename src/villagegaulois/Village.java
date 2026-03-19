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

	public String afficherVillageois() throws VillageSansChefException {
		if (this.chef == null) {
			throw new VillageSansChefException("Impossible d'afficher les villageois : le village n'a pas de chef !");
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- ");
				chaine.append(villageois[i].getNom());
				chaine.append("\n");
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
			chaine.append("Aucun etal disponible");
		}
		else {
			marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur ");
			chaine.append(vendeur.getNom());
			chaine.append(" vend des ");
			chaine.append(produit);
			chaine.append(" à l'étal n°");
			chaine.append(indiceEtalLibre+1);
			chaine.append("\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalsProduits = marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		int nombreEtals = etalsProduits.length;
		
		if(nombreEtals <=0) {
			chaine.append("Il n'y a pas de vendeur qui propose des ");
			chaine.append(produit);
			chaine.append(" au marché.\n");
			
			return chaine.toString();
		}
		else if(nombreEtals == 1) {
			Gaulois vendeur = etalsProduits[0].getVendeur();
			chaine.append("Seul le vendeur ");
			chaine.append(vendeur.getNom());
			chaine.append(" propose des ");
			chaine.append(produit);
			chaine.append(" au marché.\n");
			return chaine.toString();
		}
		else {
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for(int i=0; i<nombreEtals; i++) {
				chaine.append("- ");
				chaine.append(etalsProduits[i].getVendeur().getNom());
				chaine.append("\n");
			}
			return chaine.toString();
		}
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return rechercherEtal(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		return this.marche.afficherMarche();
	}
	
	private static class Marche{
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
		        this.etals[i] = new Etal(); 
		    }
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}
		
		private int trouverEtalLibre() {
			for(int i=0; i<etals.length; i++) {
				if(etals[i]!=null && !etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtals = 0;
			for(int i=0; i<etals.length; i++) {
				if(etals[i]!=null && etals[i].contientProduit(produit) && etals[i].isEtalOccupe()){
					nbEtals++;
				}
			}
			Etal[] tableau = new Etal[nbEtals];
			int indiceTableau = 0;
			for(int k=0; k<etals.length; k++) {
				if(etals[k]!=null && etals[k].contientProduit(produit) && etals[k].isEtalOccupe()){
					tableau[indiceTableau] = etals[k];
					indiceTableau++;
				}
			}
			return tableau;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
		    StringBuilder chaine = new StringBuilder();
		    int nbEtalsVides = 0; 
		    
		    for(int i=0; i<etals.length; i++) {
		        if(etals[i].isEtalOccupe()) {
		             chaine.append(etals[i].afficherEtal());
		        } else {
		             nbEtalsVides++; 
		        }
		    }
		    
		    if(nbEtalsVides > 0) {
		        chaine.append("Il reste ");
		        chaine.append(nbEtalsVides);
		        chaine.append(" étals non utilisés dans le marché.\n");
		    }
		    return chaine.toString();
		}
	}
	
}