package villagegaulois;
import java.util.Iterator;

import personnages.Gaulois;

public class Marche {
	private Etal[] etals;
	private int nbEtal;
	
	public Marche(int nbEtal) {
		this.nbEtal = nbEtal;
		etals = new Etal[nbEtal];
	}
	
	public void utiliserEtal(int indiceEtal, Gaulois vendeur,
			String produit, int nbProduit) {
		Etal etalActuel = etals[indiceEtal];
		etalActuel.occuperEtal(vendeur, produit, nbProduit);
	}
	
	int trouverEtalLibre() {
		for(int i=0; i<nbEtal; i++){
			if(etals[i].isEtalOccupe()) {
				i++;
			}
			else
				return i;
		}
		
		return -1;
	}
	
	Etal[] trouverEtals(String produit) {
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
	
	Etal trouverVendeur(Gaulois gaulois) {
		for(int i=0; i<nbEtal; i++) {
			if(etals[i].isEtalOccupe() && etals[i].getVendeur()==gaulois)
				return etals[i];
		}
		return null;
	}
	
}
