package model;

import java.time.LocalDate;
import java.util.Vector;

public class Vente {
    public LocalDate date;
    public Vendeur vend;
    public Client identifiant;
    public Produit nom;
    public Produit prix;
    public int quantite;
    public double total;
    public static Vector<Produit> listProds = new Vector<Produit>();
    private Produit produit;

    public Vente(LocalDate d, Client id, Produit n, Produit p, int q, double t) {
        date = d;
        identifiant = id;
        nom = n;
        prix = p;
        quantite = q;
        total = t;
    }
    
    public Produit getProduit() {
        return produit;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTotal() {
        return prix.getPrix() * quantite;
    }

    public int getQuantite() {
        return quantite;
    }
}