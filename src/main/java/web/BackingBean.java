package web;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import dao.ConnectBd;
import dao.ProduitDaoImpl;
import entity.Lot;
import entity.Produit;

@SessionScoped
public class BackingBean implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6528237508765647044L;
	private String recherche;
	private String filtre ="tout";
	private Integer categorie = 0;
	private Integer prix = 0;
	private Produit selectedProduit;
	private double total ;
	/**
	 * basket est le panier qui contiendra une liste d'articles
	 */
	private List<Lot> basket = new ArrayList<>();
	
	public List<Lot> getBasket() {
		return basket;
	}
	/**
	 * Initialise la quantit� du panier � 0 et augmente la quantit� pour chaque lot ajout� dans le panier
	 * @return int
	 */
	public int getBasketSize() {
		int quantity = 0;
		for( Lot lot : basket ) quantity += lot.getQuantity();
		return quantity;
	}
	/**
	 * getters et setters
	 * @return
	 * @throws SQLException
	 */
	public int getProduitSize() throws SQLException {
		return getProduit().size();
	}
	
	public String getFiltre() {
		return filtre;
	}
	
	public void setFiltre(String filtre) {
		this.filtre = filtre;
	}
	
	public Integer getPrix() {
		return prix;
	}

	public void setPrix(Integer prix) {
		this.prix = prix;
	}

	public Integer getCategorie() {
		return categorie;
	}

	public void setCategorie(Integer categorie) {
		this.categorie = categorie;
	}

	public String getRecherche() {
		return this.recherche;
	}
	
	public void setRecherche(String recherche) {
		this.recherche=recherche;
	}
	/**
	 * Modifie la liste des produits affich�s dans le catalogue selon la valeur que prend filtre.
	 * la liste sera apr�s affich�e par produit-contents.xhtml
	 * @return lesProduits
	 * @throws SQLException
	 */
	public List<Produit> getProduit() throws SQLException {
		List<Produit> lesProduits;
		HashMap<Integer, Produit> listeProduits = new HashMap<>();
		ConnectBd.connecte();
		ProduitDaoImpl dao = new ProduitDaoImpl();
		switch (filtre) {
		default : listeProduits = dao.findAll(); 
			break;
		case "motCle" : listeProduits = dao.findByMotCle(recherche);
			break;
		case "categorie" : listeProduits = dao.findByCategorie(categorie);
			break;
		case "prix" : listeProduits = dao.findByPrice(prix);
			break;
		case "motCleCategorie" : listeProduits = dao.findByNameCategorie(recherche, categorie);
			break;
		case "motClePrix" : listeProduits = dao.findByNamePrice(recherche, prix);
			break;
		case "categoriePrix" : listeProduits = dao.findByPriceCategorie(categorie, prix);
			break;
		case "motCleCategoriePrix" : listeProduits = dao.findByAll(recherche, prix, categorie);
	}
		lesProduits = new ArrayList<Produit>(listeProduits.values());
		return lesProduits;	
//		if (filtre == "tout") {
//			listeProduits = dao.findAll(); 
//		}
//		else if (filtre == "motCle"){
//			listeProduits = dao.findByMotCle(recherche);
//		}
//		else if (filtre == "motCleCategorie"){
//			listeProduits = dao.findByNameCategorie(recherche, categorie);
//		}
//		else if (filtre == "categorie"){
//			listeProduits = dao.findByCategorie(categorie);
//		}
//		else if (filtre == "prix"){
//			listeProduits = dao.findByPrice(prix);
//		}
//		else if (filtre == "motClePrix"){
//			listeProduits = dao.findByNamePrice(recherche, prix);
//		}
//		else if (filtre == "categoriePrix"){
//			listeProduits = dao.findByPriceCategorie(categorie, prix);
//		}
//		else if (filtre == "motCleCategoriePrix"){
//			listeProduits = dao.findByAll(recherche, prix, categorie);
//		}
	}
	
	/**
	 * assigne � filtre sa valeur selon les param�tres sur lesquels on trie
	 * @see getProduit qui s'appuie sur cette methode pour fonctionner.
	 * @return filtre
	 */
	public String filtrer() {
		if (getRecherche() == null && getCategorie() == 0 && getPrix() ==0) {
			filtre = "tout"; 
		}
		else if (getRecherche() != null && getCategorie() == 0 && getPrix() ==0){
			filtre = "motCle";
		}
		else if (getRecherche() == null && getCategorie() != 0 && getPrix() ==0){
			filtre = "categorie";
		}
		else if (getRecherche() != null && getCategorie() != 0 && getPrix() ==0){
			filtre = "motCleCategorie";
		}
		else if (getRecherche() == null && getCategorie() == 0 && getPrix() !=0) {
			filtre = "prix";
		}
		else if (getRecherche() != null && getCategorie() == 0 && getPrix() !=0) {
			filtre = "motClePrix";
		}
		else if (getRecherche() == null && getCategorie() != 0 && getPrix() !=0) {
			filtre = "categoriePrix";
		}
		else if (getRecherche() != null && getCategorie() != 0 && getPrix() !=0) {
			filtre = "motCleCategoriePrix";
		}
		
		System.out.println(recherche + categorie + prix);
		return filtre;
	}
	
	/**
	 * selectedProduit est le produit s�lectionn� par le client.
	 * C'est � partir de cette variable qu'on va extraire le prix unitaire d'un produit 
	 * et l'ajouter au prix � payer lorsqu'il a �t� mis dans le panier.
	 * @return
	 */
	public Produit getSelectedProduit() {
		return selectedProduit;
	}

	public void setSelectedProduit(Produit selectedProduit) {
		this.selectedProduit = selectedProduit;
	}
	
	/**
	 * Ajout du produit selectionn� dans le panier
	 * @param event
	 */
	public void processAddAction( ActionEvent event) {
		total += getSelectedProduit().getPrix();
		for( Lot batch : basket ) {
			if (batch.getProduit().getQuantite() <= batch.getQuantity()) {
				total -= getSelectedProduit().getPrix();
				return;
				}
			else if ( batch.getProduit().getId() == getSelectedProduit().getId() ) {
				batch.addOne();
				return;
			}
		}
		basket.add( new Lot( getSelectedProduit(), 1));
	}
	
	/**
	 * Retrait d'un exemplaire d'un produit du panier.
	 * Retire �galement le produit du panier si la quantit� atteint 0.
	 * @param event
	 */
	public void processRemoveAction( ActionEvent event) {
		total -= getSelectedProduit().getPrix();
		for( Lot batch : basket ) {
			if (batch.getQuantity() > 0)
				batch.removeOne();
			if (batch.getQuantity() == 0)
				basket.remove(batch);
				return;
		}
	}
	
	/**
	 * Arrondit le total � payer � au centi�me
	 * afin d'�viter des prix � plus de 2 chiffres apr�s la virgule
	 * @see Math.round()
	 * @return total
	 */
	public double getTotal() {
		return  Math.round(total * 100.0) / 100.0;
	}
}	


