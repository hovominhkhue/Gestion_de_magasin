package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.time.LocalDate;

import model.*;

public class GestionVenteGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComboBox<Client> idClientComboBox;
	private JLabel nomClientLabel;
	private JLabel prenomClientLabel;
	private JLabel adresseLabel;
	private JLabel num_TeleLabel;
	private JLabel mailLabel;
    private JComboBox<Produit> nomProduitComboBox;
    private JLabel prixProduitLabel;
    private JTextField quantiteTextField;
    private JLabel stockLabel;
    private JButton vendreButton;
    private JButton supprimerButton;
    private JTable table;
    private DefaultTableModel model;

    Vector<Client> client;
    Vector<Produit> produit;

    public GestionVenteGUI() {
        setTitle("Gestion de ventes");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        
    	this.client = Magasin.getListClients();
        this.produit = Magasin.getListProduits();
        
        idClientComboBox = new JComboBox<>(client.toArray(new Client[client.size()]));
        idClientComboBox.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Client selectedClient = (Client) idClientComboBox.getSelectedItem();
	        	nomClientLabel.setText("Nom: " + selectedClient.getNom());
	        	prenomClientLabel.setText("Prénom: " + selectedClient.getPrenom());
	        	adresseLabel.setText("Adresse: " + selectedClient.getAdresse());
	        	num_TeleLabel.setText("Numéro de téléphone: " + selectedClient.getNumTele());
	        	mailLabel.setText("Mail: " + selectedClient.getMail());
	        }
    	});  
    	
        nomProduitComboBox = new JComboBox<>(produit.toArray(new Produit[produit.size()]));
        nomProduitComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Produit selectedProduit = (Produit) nomProduitComboBox.getSelectedItem();
                prixProduitLabel.setText("Prix: " + selectedProduit.getPrix() + " €");
                stockLabel.setText("Stock: " + selectedProduit.getStock());
            }
        });  

        vendreButton = new JButton("Vendre");
        vendreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client selectedClient = (Client) idClientComboBox.getSelectedItem();
                Produit selectedProduit = (Produit) nomProduitComboBox.getSelectedItem();
                if (idClientComboBox.getSelectedItem() == null || nomProduitComboBox.getSelectedItem() == null || quantiteTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
                    return;
                }
                int quantite;
                try {
                    quantite = Integer.parseInt(quantiteTextField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La quantité doit être un entier");
                    return;
                }
                if (quantite<0) {
                    JOptionPane.showMessageDialog(null, "La quantité doit être positive");
                    return;
                }
                if (!selectedProduit.verifierStock(quantite)) {
                    JOptionPane.showMessageDialog(null, "Stock insuffisant");
                    return;
                }
                Vente nouvelleVente = new Vente(LocalDate.now(), selectedClient, selectedProduit, selectedProduit, quantite, selectedProduit.getPrix() * quantite);
                selectedClient.ajouterVente(nouvelleVente);
                Vendeur.addVente(nouvelleVente);
                Vector<Object> ligne = new Vector<Object>();
                ligne.add(nouvelleVente.getDate());
                ligne.add(nouvelleVente.identifiant.getIdentifiant());
                ligne.add(nouvelleVente.nom.getNom());
                ligne.add(nouvelleVente.prix.getPrix());
                ligne.add(nouvelleVente.getQuantite());
                ligne.add(nouvelleVente.getTotal());
                model.addRow(ligne);
                selectedProduit.vendre(quantite);
                stockLabel.setText("Stock: " + selectedProduit.getStock());
                selectedClient.getMontantTotalAchats();
                quantiteTextField.setText("");
            }
        });
        
        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ligneSelectionnee = table.getSelectedRow();
                if (ligneSelectionnee != -1) {
                    Vente selectedVente = Vendeur.listVentes.get(ligneSelectionnee);
                    Produit selectedProduit = (Produit) nomProduitComboBox.getSelectedItem();
                    String nomProduitSelectionne = selectedProduit.getNom();
                    String nomProduitLigne = (String) model.getValueAt(ligneSelectionnee, 2); 
                    if (nomProduitSelectionne.equals(nomProduitLigne)) {
                        int quantite = (int) model.getValueAt(ligneSelectionnee, 4);
                        selectedProduit.annuler(quantite);
                        stockLabel.setText("Stock: " + selectedProduit.getStock());
                        model.removeRow(ligneSelectionnee);
                        Client selectedClient = (Client) idClientComboBox.getSelectedItem();
                        selectedClient.annuler(selectedVente);
                        Vendeur.deleteVente(selectedVente);
                    } else {
                        JOptionPane.showMessageDialog(null, "Le produit sélectionné ne correspond pas au produit de la vente sélectionnée.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner une vente à supprimer.");
                }
            }
        });
        
        model = new DefaultTableModel(new Object[]{"Date", "Id_Client", "Nom_Produit", "Prix", "Quantité", "Total"},0);
        table = new JTable(model);
        chargerTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Identifiant de client"), c);
        
        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(idClientComboBox, c);

        c.gridx = 2;
        panel.add(new JLabel("Nom de produit"), c);
        
        c.gridx = 3;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(nomProduitComboBox, c);

        c.gridx = 1;
        c.gridy = 1;
        nomClientLabel = new JLabel("Nom de Client: ");
        panel.add(nomClientLabel,c);
        
        c.gridx = 2;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        prixProduitLabel = new JLabel("Prix: €");
        panel.add(prixProduitLabel,c);
        
        c.gridx = 1;
        c.gridy = 2;
        prenomClientLabel = new JLabel("Prénom de Client: ");
        panel.add(prenomClientLabel,c);

        c.gridx = 2;
        c.gridy = 2;
        panel.add((new JLabel("Quantité: ")),c);
        
        c.gridx = 3;
        quantiteTextField = new JTextField(10);
        panel.add(quantiteTextField,c);
        
        c.gridx = 1;
        c.gridy = 3;
        adresseLabel = new JLabel("Adresse: ");
        panel.add(adresseLabel,c);
        
        c.gridx = 2;
        c.gridy = 3;
        stockLabel = new JLabel("Stock: ");
        panel.add(stockLabel,c);
        
        c.gridx = 1;
        c.gridy = 4;
        num_TeleLabel = new JLabel("Numéro de téléphone: ");
        panel.add(num_TeleLabel,c);
        
        c.gridx = 1;
        c.gridy = 5;
        mailLabel = new JLabel("Mail: ");
        panel.add(mailLabel,c);
        
        c.gridx = 1;
        c.gridy = 6;
        c.weightx = 0;
        panel.add(vendreButton,c);

        c.gridx = 3;
        panel.add(supprimerButton,c);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 4;
        panel.add(scrollPane, c);
        
        scrollPane.setPreferredSize(new Dimension(800, 300));

        add(panel);
        setVisible(true);
	}
    public void chargerTable() {
        Vector<Vente> ventes = Vendeur.getListVentes();
        Vector<Vector<Object>> lignes = new Vector<Vector<Object>>();
        for (Vente vente : ventes) {
            Vector<Object> ligne = new Vector<Object>();
            ligne.add(vente.getDate());
            ligne.add(vente.identifiant.getIdentifiant());
            ligne.add(vente.nom.getNom());
            ligne.add(vente.prix.getPrix());
            ligne.add(vente.getQuantite());
            ligne.add(vente.getTotal());
            lignes.add(ligne);
        }
        for (Vector<Object> ligne : lignes) {
            model.addRow(ligne);
        }
    }
}