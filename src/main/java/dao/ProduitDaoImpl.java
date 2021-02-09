package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import entity.Produit;

public class ProduitDaoImpl implements ProduitDao {
	
	/**
	 * Insertion de produits dans la base de données avec une
	 * requête préparée.
	 */
	public void insert(Produit t) throws SQLException { 
			Connection con = ConnectBd.con;
			String sql = "INSERT INTO produit (id_produit, nom, quantite, description, url_image, id_categorie, prix_actuel) VALUES(?,?,?,?,?,?,?);";
			PreparedStatement prep = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prep.setInt(1, t.getId());
			prep.setString(2, t.getNom());
			prep.setInt(3, t.getQuantite());
			prep.setString(4, t.getDescription());
			prep.setString(5, t.getUrl_image());
			prep.setInt(6, t.getId_categorie());
			prep.setDouble(7, t.getPrix());
			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				t.setId(rs.getInt(1));
		}
            
	}
	
	/**
	 * Supprime un produit de la base de données avec une
	 * requête préparée.
	 */
	public boolean delete(int id) {
		try {
			Connection con = ConnectBd.con;
			Statement canal = con.createStatement();
			canal.executeUpdate("delete produit where id_produit=" +id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Tous les produits de la base de données sont insérés
	 * dans une liste HashMap.
	 * @return HashMap lesProduits
	 */
	public HashMap<Integer, Produit> findAll() {
		HashMap<Integer, Produit> lesProduits = new HashMap<Integer, Produit>();
		Connection connexion = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connexion = ConnectBd.con;
			stmt = connexion.createStatement();
			rs = stmt.executeQuery("select * from produit GROUP BY id_produit ORDER BY prix_actuel, nom ASC");
            while( rs.next() ){
				Produit p = new Produit();
				p.setId( rs.getInt("id_produit") );
				p.setNom ( rs.getString("nom") );
				p.setQuantite ( rs.getInt("quantite") );
				p.setDescription ( rs.getString("description") );
				p.setUrl_image ( rs.getString("url_image") );
				p.setId_categorie(rs.getInt("id_categorie") );
				p.setPrix ( rs.getDouble("prix_actuel") );
				lesProduits.put(p.getId(), p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lesProduits;
	}
	
	/**
	 * Filtre les produits par id dans la base de données pour ensuite mettre ce 
	 * résultat dans l'objet Produit.
	 * @return Produit p
	 */
	public Produit findById(int id) {
		Produit p = new Produit();
		try {
			Connection con = ConnectBd.con;
            String sql = "SELECT * FROM produit WHERE id_produit =?";                     
            PreparedStatement canal = con.prepareStatement(sql);                         
            canal.setInt(1, id);                                                         
            ResultSet res = canal.executeQuery();                                         
			res.next();
			p.setId( res.getInt("id_produit") );
			p.setNom ( res.getString("nom") );
			p.setQuantite ( res.getInt("quantite") );
			p.setDescription ( res.getString("description") );
			p.setUrl_image ( res.getString("url_image") );
			p.setId_categorie(res.getInt("id_categorie") );
			p.setPrix ( res.getDouble("prix_actuel") );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	/**
	 * Filtre les produits par nom dans la base de données pour ensuite mettre ce
	 * résultat dans une liste d'objets produit
	 * @return HashMap lesProduits
	 */
	public HashMap<Integer, Produit>  findByMotCle(String nom) { 
		HashMap<Integer, Produit> lesProduits = new HashMap<Integer, Produit>();
		int i = 0;
		try {
			Connection con = ConnectBd.con;
            String sql = "SELECT * FROM produit WHERE nom LIKE CONCAT( '%', TRIM(?), '%') OR description LIKE CONCAT( '%', TRIM(?), '%')";                    
            PreparedStatement canal = con.prepareStatement(sql);                         
            canal.setString(1, nom);  
            canal.setString(2, nom);  
            ResultSet res = canal.executeQuery();                                         
            while( res.next() ){
				Produit p = new Produit();
				p.setId( res.getInt("id_produit") );
				p.setNom ( res.getString("nom") );
				p.setQuantite ( res.getInt("quantite") );
				p.setDescription ( res.getString("description") );
				p.setUrl_image ( res.getString("url_image") );
				p.setId_categorie(res.getInt("id_categorie") );
				p.setPrix ( res.getDouble("prix_actuel") );
				i+=1;
				lesProduits.put(i, p);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lesProduits;
		
	}
	
	/**
	 * @see #findByPrice(int prix)
	 * @return HashMap lesProduits
	 */
	public HashMap<Integer, Produit> findByPrice(int prix) {
		HashMap<Integer, Produit> lesProduits = new HashMap<Integer, Produit>();
		int i = 0;
		try {
			Connection con = ConnectBd.con;
            String sql = "SELECT * FROM produit WHERE prix_actuel <= ?";                  
            PreparedStatement canal = con.prepareStatement(sql);                         
            canal.setDouble(1,  prix);                                                     
            ResultSet res = canal.executeQuery();                                         
            while( res.next() ){
    			Produit p = new Produit();
    			p.setId( res.getInt("id_produit") );
    			p.setNom ( res.getString("nom") );
    			p.setQuantite ( res.getInt("quantite") );
    			p.setDescription ( res.getString("description") );
    			p.setUrl_image ( res.getString("url_image") );
    			p.setId_categorie(res.getInt("id_categorie") );
    			p.setPrix ( res.getDouble("prix_actuel") );
    			i+=1;
    			lesProduits.put(i, p);
                }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lesProduits;
	}

	/**
	 * @see #findByCategorie(int id_categorie)
	 */
	public HashMap<Integer, Produit>  findByCategorie(int id_categorie) { 
		HashMap<Integer, Produit> lesProduits = new HashMap<Integer, Produit>();
		int i = 0;
		try {
			Connection con = ConnectBd.con;
            String sql = "SELECT * FROM produit WHERE id_categorie =?";                  
            PreparedStatement canal = con.prepareStatement(sql);                         
            canal.setInt(1, id_categorie);                                                         
            ResultSet res = canal.executeQuery();                                         
            while( res.next() ){
    			Produit p = new Produit();
    			p.setId( res.getInt("id_produit") );
    			p.setNom ( res.getString("nom") );
    			p.setQuantite ( res.getInt("quantite") );
    			p.setDescription ( res.getString("description") );
    			p.setUrl_image ( res.getString("url_image") );
    			p.setId_categorie(res.getInt("id_categorie") );
    			p.setPrix ( res.getDouble("prix_actuel") );
    			i+=1;
    			lesProduits.put(i, p);
                }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lesProduits;
	}
	
	/**
	 * @see #findByNameCategorie(String nom, int id_categorie)
	 */
	public HashMap<Integer, Produit> findByNameCategorie(String nom, int id_categorie) {
		HashMap<Integer, Produit> lesProduits = new HashMap<Integer, Produit>();
		int i = 0;
		try {
			Connection con = ConnectBd.con;
            String sql = "SELECT * FROM produit WHERE (nom LIKE CONCAT( '%', TRIM(?), '%') OR description LIKE CONCAT( '%', TRIM(?), '%')) AND id_categorie = ? ";                     
            PreparedStatement canal = con.prepareStatement(sql);
            canal.setString(1, nom);
            canal.setString(2, nom);  
            canal.setInt(3, id_categorie);  
            ResultSet res = canal.executeQuery();                                         
            while( res.next() ){
    			Produit p = new Produit();
    			p.setId( res.getInt("id_produit") );
    			p.setNom ( res.getString("nom") );
    			p.setQuantite ( res.getInt("quantite") );
    			p.setDescription ( res.getString("description") );
    			p.setUrl_image ( res.getString("url_image"));
    			p.setId_categorie(res.getInt("id_categorie") );
    			p.setPrix ( res.getDouble("prix_actuel") );
    			i+=1;
    			lesProduits.put(i, p);
                }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lesProduits;
	}
	
	/**
	 * @see #findByNamePrice(String nom, int prix)
	 */
	public HashMap<Integer, Produit> findByNamePrice(String nom, int prix) {
		HashMap<Integer, Produit> lesProduits = new HashMap<Integer, Produit>();
		int i = 0;
		try {
			Connection con = ConnectBd.con;
            String sql = "SELECT * FROM produit p WHERE (nom LIKE CONCAT( '%', TRIM(?), '%') OR description LIKE CONCAT( '%', TRIM(?), '%')) AND prix_actuel <= ? ";                     
            PreparedStatement canal = con.prepareStatement(sql);
            canal.setString(1, nom);
            canal.setString(2, nom);  
            canal.setDouble(3, prix);  
            ResultSet res = canal.executeQuery();                                         
            while( res.next() ){
    			Produit p = new Produit();
    			p.setId( res.getInt("id_produit") );
    			p.setNom ( res.getString("nom") );
    			p.setQuantite ( res.getInt("quantite") );
    			p.setDescription ( res.getString("description") );
    			p.setUrl_image ( res.getString("url_image"));
    			p.setId_categorie(res.getInt("id_categorie") );
    			p.setPrix ( res.getDouble("prix_actuel") );
    			i+=1;
    			lesProduits.put(i, p);
                }
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return lesProduits;
	}
	
	/**
	 * @see #findByPriceCategorie(int id_categorie, int prix)
	 */
	public HashMap<Integer, Produit> findByPriceCategorie(int id_categorie, int prix) {
		HashMap<Integer, Produit> lesProduits = new HashMap<Integer, Produit>();
		int i = 0;
		try {
			Connection con = ConnectBd.con;
            String sql = "SELECT * FROM produit WHERE id_categorie = ? AND prix_actuel <= ? ";                     
            PreparedStatement canal = con.prepareStatement(sql);
            canal.setInt(1, id_categorie);
            canal.setInt(2, prix);  
            ResultSet res = canal.executeQuery();                                         
            while( res.next() ){
    			Produit p = new Produit();
    			p.setId( res.getInt("id_produit") );
    			p.setNom ( res.getString("nom") );
    			p.setQuantite ( res.getInt("quantite") );
    			p.setDescription ( res.getString("description") );
    			p.setUrl_image ( res.getString("url_image"));
    			p.setId_categorie(res.getInt("id_categorie") );
    			p.setPrix ( res.getDouble("prix_actuel") );
    			i+=1;
    			lesProduits.put(i, p);
                }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lesProduits;
	}
	
	/**
	 * @see #findByAll(String nom, int prix, int id_categorie)
	 */
	public HashMap<Integer, Produit> findByAll(String nom, int prix, int id_categorie) {
		HashMap<Integer, Produit> lesProduits = new HashMap<Integer, Produit>();
		int i = 0;
		try {
			Connection con = ConnectBd.con;
            String sql = "SELECT * FROM produit p WHERE (nom LIKE CONCAT( '%', TRIM(?), '%') OR description LIKE CONCAT( '%', TRIM(?), '%')) AND prix_actuel <= ? AND id_categorie = ?";   
            System.out.println(sql);
            PreparedStatement canal = con.prepareStatement(sql);
	            canal.setString(1, nom);
	            canal.setString(2, nom);
            	canal.setInt(3, prix);  
            	canal.setInt(4, id_categorie);
            ResultSet res = canal.executeQuery();                                         
            while( res.next() ){
    			Produit p = new Produit();
    			p.setId( res.getInt("id_produit") );
    			p.setNom ( res.getString("nom") );
    			p.setQuantite ( res.getInt("quantite") );
    			p.setDescription ( res.getString("description") );
    			p.setUrl_image ( res.getString("url_image"));
    			p.setId_categorie(res.getInt("id_categorie") );
    			p.setPrix ( res.getDouble("prix_actuel") );
    			i+=1;
    			lesProduits.put(i, p);
                }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lesProduits;
	}

	
	
	
	
}
