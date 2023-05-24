package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Magasin;
import model.Produit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GestionProduitGUI extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel model;
    private JTable table;
    private JTextField nomField, modeleField, prixField, nombre_stockField;

    public GestionProduitGUI() {
        setTitle("Gestion produits");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        
        JLabel nomLabel = new JLabel("Nom :");
        JLabel modeleLabel = new JLabel("Modele :");
        JLabel prixLabel = new JLabel("Prix :");
        JLabel nombre_stock = new JLabel("Nombre de stock :");
       
        nomField = new JTextField(20);
        modeleField = new JTextField(20);
        prixField = new JTextField(20);
        nombre_stockField = new JTextField(20);
       
        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String modele = modeleField.getText();
                String prixStr = prixField.getText();
                String nombre_stockStr = nombre_stockField.getText();
                if (nom.isEmpty() || modele.isEmpty() || prixStr.isEmpty() || nombre_stockStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis.");
                    return;
                }
                double prix;
                int nombre_stock;
                try {
                    prix = Double.parseDouble(prixStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Le prix doit être un nombre.");
                    return;
                }
                try {
                    nombre_stock = Integer.parseInt(nombre_stockStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Le nombre en stock doit être un entier.");
                    return;
                }
                if (prix < 0) {
                    JOptionPane.showMessageDialog(null, "Le prix doit être positif.");
                    return;
                }
                if (nombre_stock < 0) {
                    JOptionPane.showMessageDialog(null, "Le nombre en stock doit être positif.");
                    return;
                }
                Produit nouveauProduit = new Produit(nom, modele, prix, nombre_stock);
                Magasin.addProduit(nouveauProduit);
                Vector<Object> ligne = new Vector<Object>();
                ligne.add(nouveauProduit.getNom());
                ligne.add(nouveauProduit.getModele());
                ligne.add(nouveauProduit.getPrix());
                ligne.add(nouveauProduit.getStock());
                model.addRow(ligne);
                reinitialiserField();
            }
        });

        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                int ligneSelectionnee = table.getSelectedRow();
                if (ligneSelectionnee != -1) {
                    String nomProduitASupprimer = (String) model.getValueAt(ligneSelectionnee, 0);
                    Produit produitASupprimer = null;
                    for (Produit produit : Magasin.getListProduits()) {
                        if (produit.getNom().equals(nomProduitASupprimer)) {
                        	produitASupprimer = produit;
                            break;
                        }
                    }
                    if (produitASupprimer != null) {
                        Magasin.deleteProduit(produitASupprimer);
                        model.removeRow(ligneSelectionnee);
                    } else {
                        JOptionPane.showMessageDialog(null, "Le produit à supprimer n'a pas été trouvé.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit à supprimer.");
                }
            }
        });

        model = new DefaultTableModel(new Object[]{"Nom", "Modele", "Prix", "Nombre de stock"}, 0);
        table = new JTable(model);
        chargerTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panel.add(nomLabel, c);

        c.gridx = 1;
        panel.add(nomField, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(modeleLabel, c);

        c.gridx = 1;
        panel.add(modeleField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(prixLabel, c);

        c.gridx = 1;
        panel.add(prixField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(nombre_stock, c);

        c.gridx = 1;
        panel.add(nombre_stockField, c);


        c.gridx = 0;
        c.gridy = 5;
        panel.add(ajouterButton, c);

        c.gridx = 1;
        panel.add(supprimerButton, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        panel.add(scrollPane, c);
        
        scrollPane.setPreferredSize(new Dimension(800, 300));

        add(panel);
        setVisible(true);
    }
    public void reinitialiserField() {
    	nomField.setText("");
        modeleField.setText("");
        prixField.setText("");
        nombre_stockField.setText("");
    }
    public void chargerTable() {
    	Vector<Produit> produits = Magasin.getListProduits();
        Vector<Vector<Object>> lignes = new Vector<Vector<Object>>();
        for (Produit produit : produits) {
            Vector<Object> ligne = new Vector<Object>();
            ligne.add(produit.getNom());
            ligne.add(produit.getModele());
            ligne.add(produit.getPrix());
            ligne.add(produit.getStock());
            lignes.add(ligne);
        }
        for (Vector<Object> ligne : lignes) {
            model.addRow(ligne);
        }
    }
}