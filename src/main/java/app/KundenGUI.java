package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class KundenGUI {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private String role;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KundenGUI().showLogin());
    }

    public void showLogin() {
        frame = new JFrame("Kundenverwaltung Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLayout(new GridLayout(3, 2));


        frame.add(new JLabel("Username:"));
        usernameField = new JTextField();
        frame.add(usernameField);

        frame.add(new JLabel("Passwort:"));
        passwordField = new JPasswordField();
        frame.add(passwordField);

        JButton loginBtn = new JButton("Login");
        frame.add(loginBtn);



        JLabel messageLabel = new JLabel();
        frame.add(messageLabel);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            role = AuthenticationService.login(user, pass);
            System.out.println("Logged in role: " + role);

            if (role == null) {
                messageLabel.setText("❌ Anmeldung fehlgeschlagen");
            } else {
                frame.dispose();
                showMainMenu();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showMainMenu() {
        frame = new JFrame("Kundenverwaltung - Hauptmenü");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setLayout(new BorderLayout());

        // Tabelle
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Vorname", "Nachname", "Firma", "Anschrift", "Email"});
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel panel = new JPanel();
        JButton showBtn = new JButton("Kunden anzeigen");
        JButton addBtn = new JButton("Kunde hinzufügen");
        JButton updateBtn = new JButton("Kunde aktualisieren");
        panel.add(showBtn);
        panel.add(addBtn);
        panel.add(updateBtn);

        if ("admin".equals(role)) {
            JButton deleteBtn = new JButton("Kunde löschen");
            panel.add(deleteBtn);

            deleteBtn.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(frame, "ID des Kunden zum Löschen:");
                if (input != null) {
                    try {
                        int id = Integer.parseInt(input);
                        boolean deleted = KundenService.deleteKunde(id);
                        JOptionPane.showMessageDialog(frame, deleted ? "✅ Kunde gelöscht" : "❌ Löschen fehlgeschlagen");
                        showKundenInTable(model);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "❌ Ungültige ID");
                    }
                }
            });
        }

        frame.add(panel, BorderLayout.SOUTH);

        // Button-Logik
        showBtn.addActionListener(e -> showKundenInTable(model));

        addBtn.addActionListener(e -> {
            JTextField vField = new JTextField();
            JTextField nField = new JTextField();
            JTextField fField = new JTextField();
            JTextField aField = new JTextField();
            JTextField eField = new JTextField();

            Object[] fields = {
                    "Vorname:", vField,
                    "Nachname:", nField,
                    "Firma:", fField,
                    "Anschrift:", aField,
                    "Email:", eField
            };

            int option = JOptionPane.showConfirmDialog(frame, fields, "Neuen Kunden hinzufügen", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                boolean added = KundenService.addKunde(
                        vField.getText(), nField.getText(), fField.getText(), aField.getText(), eField.getText()
                );
                JOptionPane.showMessageDialog(frame, added ? "✅ Kunde hinzugefügt" : "❌ Hinzufügen fehlgeschlagen");
                showKundenInTable(model);
            }
        });

        updateBtn.addActionListener(e -> {
            String idInput = JOptionPane.showInputDialog(frame, "ID des Kunden zum Aktualisieren:");
            if (idInput != null) {
                try {
                    int id = Integer.parseInt(idInput);
                    JTextField vField = new JTextField();
                    JTextField nField = new JTextField();
                    JTextField fField = new JTextField();
                    JTextField aField = new JTextField();
                    JTextField eField = new JTextField();

                    Object[] fields = {
                            "Vorname:", vField,
                            "Nachname:", nField,
                            "Firma:", fField,
                            "Anschrift:", aField,
                            "Email:", eField
                    };

                    int option = JOptionPane.showConfirmDialog(frame, fields, "Kunden aktualisieren", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        boolean updated = KundenService.updateKunde(
                                id, vField.getText(), nField.getText(), fField.getText(), aField.getText(), eField.getText()
                        );
                        JOptionPane.showMessageDialog(frame, updated ? "✅ Kunde aktualisiert" : "❌ Aktualisierung fehlgeschlagen");
                        showKundenInTable(model);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "❌ Ungültige ID");
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showKundenInTable(DefaultTableModel model) {
        model.setRowCount(0);
        List<String> lines = KundenService.listKundenLines();
        for (String line : lines) {
            String[] parts = line.split(",");
            model.addRow(parts);
        }

    }



    // Was dieser Code macht:
        //
        //Login-Fenster mit Username + Passwort
        //
        //Hauptfenster mit Tabelle, Buttons für CRUD
        //
        //Lösch-Button nur sichtbar für Admin
        //
        //Eingaben erfolgen über Popup-Fenster (JOptionPane)
        //
        //Ruft direkt deine bestehenden Services auf (KundenService, AuthenticationService)
    }

