package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
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
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalsProduit=marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		
		if(etalsProduit.length==0)
			chaine.append("Personne ne propose l'article '" + produit + "'\n");
		else {
			chaine.append("La ou les personne(s) proposant l'article '" + produit + "' est / sont :\n");
			for(int i=0; i<etalsProduit.length; i++) 
				chaine.append("- " + etalsProduit[i].getVendeur().getNom()).append("\n");
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int etalLibre = marche.trouverEtalLibre();
		if(etalLibre==-1)
			chaine.append("Il n'y a pas d'etal libre sur lequel pourrait s'installer " + vendeur + "\n");
		else {
			marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
			chaine.append(vendeur.getNom() + " s'est installé au marché pour vendre un stock de " 
							+ nbProduit + " " + produit + "\n");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etalLibere = marche.trouverVendeur(vendeur);
		etalLibere.libererEtal();
		chaine.append(vendeur.getNom() + " quitte le marche et libere sont etal \n");
		return chaine.toString();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}
	
	private class Marche {
		private Etal[] etals;
		private int nbEtal;
		
		private Marche(int nbEtal) {
			this.nbEtal = nbEtal;
			etals = new Etal[nbEtal];
			
		    for (int i = 0; i < nbEtal; i++)
		        etals[i] = new Etal();
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,
				String produit, int nbProduit) {
			Etal etalActuel = etals[indiceEtal];
			etalActuel.occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for(int i=0; i<nbEtal; i++){
				if(!etals[i].isEtalOccupe()) 
					return i;
			}
			
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalProduit=0;
			for(int i=0; i<nbEtal; i++) {
				if(etals[i].contientProduit(produit))
					nbEtalProduit++;
			}
			
			Etal[] etalProduit;
			etalProduit = new Etal[nbEtalProduit];
			
			int indiceEtalProduit=0;
			for(int j=0; j<nbEtal; j++) {
				if(etals[j].contientProduit(produit)) {
					etalProduit[indiceEtalProduit]=etals[j];
					indiceEtalProduit++;
				}
			}
			
			return etalProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<nbEtal; i++) {
				if(etals[i].isEtalOccupe() && etals[i].getVendeur()==gaulois)
					return etals[i];
			}
			return null;
		}
		
		
		private String afficherMarche() {
			StringBuilder chaine=new StringBuilder();
			int nbEtalOcc=0;
			for(int i=0; i<nbEtal; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
					nbEtalOcc++;
				}
			}
			int nbEtalVide=nbEtal-nbEtalOcc;
			if(nbEtalOcc!=nbEtal)
				chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché. \n");
			
			return chaine.toString();
		}
	}
}