package dao;


import java.util.HashMap;

import entity.Produit;

public interface ProduitDao extends Dao <Produit> {
	public HashMap<Integer, Produit> findByMotCle(String nom);
	public HashMap<Integer, Produit> findByPrice(int prix);
	public HashMap<Integer, Produit> findByCategorie(int id_categorie);
	public HashMap<Integer, Produit> findByNameCategorie(String nom, int id_categorie);
	public HashMap<Integer, Produit> findByNamePrice(String nom, int prix);
	public HashMap<Integer, Produit> findByPriceCategorie(int id_categorie, int prix);
	public HashMap<Integer, Produit> findByAll(String nom, int prix, int ref);
}
