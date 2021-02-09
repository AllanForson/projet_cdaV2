package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import entity.Commande;
import entity.Produit;

public class CommandeDaoImpl implements CommandeDao {

	public void insert(Commande t) {
		try {
			Connection con = ConnectBd.con;
			Statement canal = con.createStatement();
			canal.executeUpdate("insert into commande VALUES(" + t.getId() + ", "  + t.getDate_validation() + ");");
			// recuperer le dernier index   ( = index du client)
			// mettre cet index dans l'idClient de la commande
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean delete(int id) {
		try {
			Connection con = ConnectBd.con;
			Statement canal = con.createStatement();
			canal.executeUpdate("delete commande where id=" +id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public HashMap<Integer, Commande> findAll() {
		HashMap<Integer, Commande> lesCommandes = new HashMap<>();
		try {
			Connection con = ConnectBd.con;
			Statement canal = con.createStatement();
			ResultSet res = canal.executeQuery("select * from commande");
			while( res.next() ){
				Commande c = new Commande();
				c.setId( res.getInt("id") );
				c.setDate_validation ( res.getString("date_validation") );
				lesCommandes.put(c.getId(), c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lesCommandes;
	}

	public Commande findById(int id) {
		Commande c = new Commande();
		try {
			Connection con = ConnectBd.con;
			Statement canal = con.createStatement();
			ResultSet res = canal.executeQuery("select * from commande where id=" + id);
			res.next();
			c.setId( res.getInt("id") );
			c.setDate_validation ( res.getString("date_validation") );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}



}
