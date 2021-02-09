package entity;

public class Commande {
private int id;
private String date_validation;



public Commande() {
}



public Commande(int id, String date_validation) {
	super();
	this.id = id;
	this.date_validation = date_validation;
}



public int getId() {
	return id;
}



public void setId(int id) {
	this.id = id;
}



public String getDate_validation() {
	return date_validation;
}



public void setDate_validation(String date_validation) {
	this.date_validation = date_validation;
}


}
