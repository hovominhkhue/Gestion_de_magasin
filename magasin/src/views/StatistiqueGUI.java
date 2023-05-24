package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.*;

public class StatistiqueGUI extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private JButton clientButton;
    private JButton produitButton;
    private JTable statsTable;

    public StatistiqueGUI() {
        setTitle("Statistiques de vente");
        setSize(1000, 500);
        setLocationRelativeTo(null);

        clientButton = new JButton("Statistiques par client");
        produitButton = new JButton("Statistiques par produit");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clientButton);
        buttonPanel.add(produitButton);
        add(buttonPanel, BorderLayout.NORTH);

        statsTable = new JTable();
        
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[][] data = new Object[Magasin.getListClients().size()][2];
                for (int i = 0; i < Magasin.getListClients().size(); i++) {
                    Client client = Magasin.getListClients().get(i);
                    data[i][0] = client.getNom();
                    data[i][1] = client.getMontantTotalAchats();
                }
                String[] columnNames = {"Nom du client", "Montant total des achats"};
                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                statsTable.setModel(model);
            }
        });
        
        produitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[][] data = new Object[Magasin.getListProduits().size()][2];
                for (int i = 0; i < Magasin.getListProduits().size(); i++) {
                    Produit produit = Magasin.getListProduits().get(i);
                    data[i][0] = produit.getNom();
                    data[i][1] = produit.getQuantiteVendue();
                }
                String[] columnNames = {"Nom du produit", "Quantité vendue"};
                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                statsTable.setModel(model);
            }
        });
        add(new JScrollPane(statsTable), BorderLayout.CENTER);
        setVisible(true);
    }
}