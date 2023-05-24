package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Magasin;
import model.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GestionClientGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel model;
    private JTable table;
    private JTextField nomField, prenomField, adresseField, telephoneField, mailField;
    
    
    public GestionClientGUI() {
        setTitle("Gestion des clients");
        setSize(1000, 500);
        setLocationRelativeTo(null);

        JLabel nomLabel = new JLabel("Nom :");
        JLabel prenomLabel = new JLabel("Prénom :");
        JLabel adresseLabel = new JLabel("Adresse :");
        JLabel telephoneLabel = new JLabel("Téléphone :");
        JLabel mailLabel = new JLabel("Mail :");

        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        adresseField = new JTextField(20);
        telephoneField = new JTextField(20);
        mailField = new JTextField(20);

        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String adresse = adresseField.getText();
                String telephone = telephoneField.getText();
                String mail = mailField.getText();
                if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || telephone.isEmpty() || mail.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis.");
                    return;
                }
                Client nouveauClient = new Client(Client.getProchainIdentifiant(), nom, prenom, adresse, telephone, mail);
                Magasin.addClient(nouveauClient);
                Vector<Object> ligne = new Vector<Object>();
                ligne.add(nouveauClient.getIdentifiant());
                ligne.add(nouveauClient.getNom());
                ligne.add(nouveauClient.getPrenom());
                ligne.add(nouveauClient.getAdresse());
                ligne.add(nouveauClient.getNumTele());
                ligne.add(nouveauClient.getMail());
                model.addRow(ligne);
                reinitialiserField();
            }
        });
        
        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                int ligneSelectionnee = table.getSelectedRow();
                if (ligneSelectionnee != -1) {
                    String identifiantClientASupprimer = (String) model.getValueAt(ligneSelectionnee, 0);
                    Client clientASupprimer = null;
                    for (Client client : Magasin.getListClients()) {
                        if (client.getIdentifiant().equals(identifiantClientASupprimer)) {
                            clientASupprimer = client;
                            break;
                        }
                    }
                    if (clientASupprimer != null) {
                        Magasin.deleteClient(clientASupprimer);
                        model.removeRow(ligneSelectionnee);
                    } else {
                        JOptionPane.showMessageDialog(null, "Le client à supprimer n'a pas été trouvé.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un client à supprimer.");
                }
            }
        });
        
        model = new DefaultTableModel(new Object[]{"Identifiant", "Nom", "Prénom", "Adresse", "Téléphone", "Mail"}, 0);
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
        panel.add(prenomLabel, c);

        c.gridx = 1;
        panel.add(prenomField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(adresseLabel, c);

        c.gridx = 1;
        panel.add(adresseField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(telephoneLabel, c);

        c.gridx = 1;
        panel.add(telephoneField, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(mailLabel, c);

        c.gridx = 1;
        panel.add(mailField, c);

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
        prenomField.setText("");
        adresseField.setText("");
        telephoneField.setText("");
        mailField.setText("");
    }
    public void chargerTable() {
        Vector<Client> clients = Magasin.getListClients();        
        Vector<Vector<Object>> lignes = new Vector<Vector<Object>>();
        for (Client client : clients) {
            Vector<Object> ligne = new Vector<Object>();
            ligne.add(client.getIdentifiant());
            ligne.add(client.getNom());
            ligne.add(client.getPrenom());
            ligne.add(client.getAdresse());
            ligne.add(client.getNumTele());
            ligne.add(client.getMail());
            lignes.add(ligne);
        }
        for (Vector<Object> ligne : lignes) {
            model.addRow(ligne);
        }
    }
}