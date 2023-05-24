package controller;

import java.awt.event.*;
import javax.swing.*;

import views.*;

public class ControllerClick implements ActionListener {
    private JMenu gestionClient;
    private JMenu gestionProduit;
    private JMenu gestionVente;
    private JMenu statistique;

    private GestionClientGUI gestionClientGUI;
    private GestionProduitGUI gestionProduitGUI;
    private GestionVenteGUI gestionVenteGUI;
    private StatistiqueGUI statistiqueGUI;

    public ControllerClick(JMenu gestionClient, JMenu gestionProduit, JMenu gestionVente, JMenu statistique) {
        this.gestionClient = gestionClient;
        this.gestionProduit = gestionProduit;
        this.gestionVente = gestionVente;
        this.statistique = statistique;
    }

    public void ajouterMenuItems() {
        JMenuItem gestionClientItem = new JMenuItem("Gestion des clients");
        JMenuItem gestionProduitItem = new JMenuItem("Gestion des produits");
        JMenuItem gestionVenteItem = new JMenuItem("Gestion des ventes");
        JMenuItem statistiqueItem = new JMenuItem("Statistique de vente");

        gestionClientItem.addActionListener(this);
        gestionProduitItem.addActionListener(this);
        gestionVenteItem.addActionListener(this);
        statistiqueItem.addActionListener(this);

        gestionClient.add(gestionClientItem);
        gestionProduit.add(gestionProduitItem);
        gestionVente.add(gestionVenteItem);
        statistique.add(statistiqueItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Gestion des clients")) {
            if (gestionClientGUI != null) {
                gestionClientGUI.dispose();
            }
            gestionClientGUI = new GestionClientGUI();
            gestionClientGUI.setVisible(true);
        } else if (e.getActionCommand().equals("Gestion des produits")) {
            if (gestionProduitGUI != null) {
                gestionProduitGUI.dispose();
            }
            gestionProduitGUI = new GestionProduitGUI();
            gestionProduitGUI.setVisible(true);
        } else if (e.getActionCommand().equals("Gestion des ventes")) {
            if (gestionVenteGUI != null) {
                gestionVenteGUI.dispose();
            }
            gestionVenteGUI = new GestionVenteGUI();
            gestionVenteGUI.setVisible(true);
        } else if (e.getActionCommand().equals("Statistique de vente")) {
            if (statistiqueGUI != null) {
                statistiqueGUI.dispose();
            }
            statistiqueGUI = new StatistiqueGUI();
            statistiqueGUI.setVisible(true);
        }
    }
}