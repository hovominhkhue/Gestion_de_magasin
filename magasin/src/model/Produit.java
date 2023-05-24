package model;
import java.util.*;

public class Produit {
    public String nom;
    public String modèle;
    public double prix;
    public int nombre_Stock;    
    public Vector<Vente> listVentes = new Vector<Vente>();
    public int quantiteVendue; 
    
    public Produit(String n, String m, double p, int nS) {
        nom = n; 
        modèle = m; 
        prix = p; 
        nombre_Stock = nS;
        quantiteVendue = 0; 
    }

    public String getNom() {
        return nom;
    }
    
    public String getModele() {
        return modèle;
    }
    
    public double getPrix() {
        return prix;
    }
    
    public int getStock() {
        return nombre_Stock;
    }
    
    public boolean verifierStock(int quantiteDemandee) {
        return quantiteDemandee <= nombre_Stock;
    }
    
    public int getQuantiteVendue() {
        return quantiteVendue;
    }
    
    public void vendre(int quantite) {
        if (quantite <= nombre_Stock) {
            nombre_Stock -= quantite;
            quantiteVendue += quantite;
        }
    }
    
    public void annuler(int quantite) {
        nombre_Stock += quantite;
        quantiteVendue -= quantite;
    }
    
    public String toString() {
        return getNom();
    }
}