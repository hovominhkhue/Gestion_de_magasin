package model;
import java.util.*;

public class Client {
	public String identifiant;
    public String nom;
    public String prenom;
    public String adresse;
    public String num_Tele;
    public String mail;
    public Vector<Vente> listVentes = new Vector<Vente>();
    
    public Client(String i, String n, String p, String a, String numT, String m) {
    	identifiant = i;
        nom = n;
        prenom = p;
        adresse = a;
        num_Tele = numT;
        mail = m;
    }
    
    public String getIdentifiant() {
        return identifiant;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public String getNumTele() {
        return num_Tele;
    }
    
    public String getMail() {
        return mail;
    }
    
    public double getMontantTotalAchats() {
    	double montantTotalAchats=0;
        for (Vente vente : listVentes) {
            montantTotalAchats += vente.getTotal();
        }
        return montantTotalAchats;
    }
    
    @SuppressWarnings("unused")
	public void annuler(Vente vente) {
    	double montantTotalAchats = getMontantTotalAchats();
        listVentes.remove(vente);
        montantTotalAchats -= vente.getTotal();
    }
    
    public static String getProchainIdentifiant() {
        int dernierIdentifiant = 0;
        for (Client client : Magasin.getListClients()) {
            int id = Integer.parseInt(client.getIdentifiant());
            if (id > dernierIdentifiant) {
                dernierIdentifiant = id;
            }
        }
        return String.format("%06d", dernierIdentifiant + 1);
    }
    
    public String toString() {
        return getIdentifiant();
    }

    public void ajouterVente(Vente vente) {
        listVentes.add(vente);
    }
}