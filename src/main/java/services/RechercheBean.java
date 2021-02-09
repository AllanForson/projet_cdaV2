package services;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import dao.ConnectBd;
import dao.ProduitDaoImpl;
import entity.Produit;


@SessionScoped
public class RechercheBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7766002831914437093L;
//	private String recherche;
//
//
//	public String getRecherche() {
//		return this.recherche;
//	}
//	
//	public void setRecherche(String recherche) {
//		this.recherche=recherche;
//	}
//	
//	public doRecherche(ActionEvent event) throws SQLException{
//		setRecherche(getRecherche());
//		ConnectBd.connecte();
//		ProduitDaoImpl dao = new ProduitDaoImpl();
//		
//		HashMap<Integer, Produit> catalogue = dao.findByMotCle(getRecherche());
//		List<Produit> lesProduits = new ArrayList(catalogue.values());
//		System.out.println(lesProduits);
//		 
//		}
}

