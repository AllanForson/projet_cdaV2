package dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectBd {
	public static Connection con = null;
	// Erreurs MySQL classiques
	public static final int DOUBLON = 1062;
	public static final int LIGNE_REFERENCEE = 1451;
	public static final int PARENT_INEXISTANT = 1452;
	
	public static final int NOM_VIDE = 3000;
	public static final int PRENOM_VIDE = 3001;
	public static final int DATE_NAISSANCE_VIDE = 3002;
	public static final int PRIX_INVALIDE = 3003;
	public static final int QUANTITE_NEGATIVE = 3004;
	
	public static void connecte() throws SQLException{ 
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
		}
		
		catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/projet_cda_1","root","root");
	        Statement canal = con.createStatement();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
		
		/** Reinitialise la base encheres en exécutant la procédure stockée
		 * _refresh.
		 * @throws SQLException
		 */
		public static void reset() throws SQLException {
			if (con == null) {
				connecte();
			}
			CallableStatement stmt = (CallableStatement) con.prepareCall("CALL projet_cda_1_refresh()");
			stmt.execute();
		}
	

}
