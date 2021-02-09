package dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import entity.Produit;


public class ProduitBean implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6528237508765647044L;
	private String recherche;

	public String getRecherche() {
		return this.recherche;
	}
	
	public void setRecherche(String recherche) {
		this.recherche=recherche;
	}

	public List<Produit> getProduit() throws SQLException {
		List<Produit> lesProduits;
		ConnectBd.connecte();
		ProduitDaoImpl dao = new ProduitDaoImpl();
		if (getRecherche() == null)
		{
			HashMap<Integer, Produit> catalogue = dao.findAll();
			lesProduits = new ArrayList(catalogue.values());
		}
		else {
			HashMap<Integer, Produit> catalogue = dao.findByMotCle(recherche);
			System.out.println(recherche);
			lesProduits = new ArrayList(catalogue.values());
		}
		
		return lesProduits;
		
}
	
	public String doRecherche() {
		recherche = getRecherche();
		return recherche;
		}
	
//	public List<Produit> getRecherche() throws SQLException {
//		ConnectBd.connecte();
//		ProduitDaoImpl dao = new ProduitDaoImpl();
//		
//		HashMap<Integer, Produit> catalogue = dao.findByMotCle("recherche");
//		List<Produit> lesProduits = new ArrayList(catalogue.values());
//		System.out.println(lesProduits);
//		return lesProduits;
//}

	
//public int getSize() throws SQLException {
//	return getProduit().size();
//}

}	


