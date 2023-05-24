package model;
import java.util.*;

public class Vendeur {
    public String nom;
    public char role;
    public static Vector<Vente> listVentes = new Vector<Vente>();
    
    public Vendeur(String n, char r) {
        nom = n;
        role = r;
    }
    
    public static void addVente(Vente v) {
    	listVentes.add(v);
    }
    
    public static void deleteVente(Vente v) {
    	listVentes.remove(v);
    }
    
    public static Vector<Vente> getListVentes() {
        return listVentes;
    }
}