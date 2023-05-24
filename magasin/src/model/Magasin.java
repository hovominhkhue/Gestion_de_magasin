package model;
import java.util.*;


public class Magasin {
	public String nom;
	public String adresse;
	public static Vector<Produit> listProds = new Vector<Produit>();
	public Vector<Vendeur> listVends = new Vector<Vendeur>();
	public static Vector<Client> listClients = new Vector<Client>();
    
    public Magasin(String n, String a) {
    	nom = n; 
    	adresse = a;
    }
    
    public static void addProduit(Produit p) {
    	listProds.add(p);
    }
    
    public static void deleteProduit(Produit p) {
    	listProds.remove(p);
    }
    
    public static void addClient(Client c) {
    	listClients.add(c);
    }
    
    public static void deleteClient(Client c) {
    	listClients.remove(c);
    }
    
    public static Vector<Client> getListClients() {
        return listClients;
    }
    
    public static Vector<Produit> getListProduits() {
        return listProds;
    }
}