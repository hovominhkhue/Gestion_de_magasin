package views;

import java.awt.*;
import javax.swing.*;

import controller.ControllerClick;

public class GUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JMenu gestionClient;
    private JMenu gestionProduit;
    private JMenu gestionVente;
    private JMenu statistique;

    public GUI() {
        setTitle("Gestion de magasin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        
        gestionClient = new JMenu("Clients");
        gestionProduit = new JMenu("Produits");
        gestionVente = new JMenu("Ventes");
        statistique = new JMenu("Statistique");
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(gestionClient);
        menuBar.add(gestionProduit);
        menuBar.add(gestionVente);
        menuBar.add(statistique);
        setJMenuBar(menuBar);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Bienvenue");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        setContentPane(panel);
        setVisible(true);

        // Instancier la classe ControllerClick et appeler sa méthode ajouterMenuItems
        ControllerClick controller = new ControllerClick(gestionClient, gestionProduit, gestionVente, statistique);
        controller.ajouterMenuItems();
    }
}