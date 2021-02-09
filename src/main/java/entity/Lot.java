package entity;

public class Lot {
	private Produit produit;
	private int quantity;

	public Lot(Produit produit, int quantity) {
		super();
		this.produit = produit;
		this.quantity = quantity;
	}

	public void addOne() {
		quantity++;
	}
	
	public void removeOne() {
		quantity--;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

}
