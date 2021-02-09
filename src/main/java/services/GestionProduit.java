package services;

import entity.Produit;

public interface GestionProduit {
	public boolean creerProduit(Produit nouvProduit);
	public boolean creerProduit(int id, String nom, int quantite, String description, String url_image, double prix);
}
