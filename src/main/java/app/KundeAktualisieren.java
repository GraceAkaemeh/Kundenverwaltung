package app;

import java.sql.*;
import java.util.Scanner;

public class KundeAktualisieren {

    public static void aktualisieren(Connection conn, Scanner sc) {
        try {
            // 🇩🇪 Kunden-ID abfragen, die aktualisiert werden soll
            // 🇬🇧 Ask which customer ID should be updated
            System.out.print("Bitte geben Sie die Kunden-ID ein, die Sie aktualisieren möchten: ");
            int id = Integer.parseInt(sc.nextLine());

            // 🇩🇪 Aktuelle Kundendaten abrufen
            // 🇬🇧 Retrieve current customer data
            String selectSQL = "SELECT * FROM kunden WHERE id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
            selectStmt.setInt(1, id);
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Kunde mit dieser ID wurde nicht gefunden.");
                return;
            }

            // 🇩🇪 Aktuelle Werte anzeigen
            // 🇬🇧 Display current values
            String vornameAlt = rs.getString("vorname");
            String nachnameAlt = rs.getString("nachname");
            String firmaAlt = rs.getString("firma");
            String anschriftAlt = rs.getString("anschrift");
            String emailAlt = rs.getString("email");

            // 🇩🇪 Neue Eingaben anfordern (Enter = behalten)
            // 🇬🇧 Ask for new inputs (Enter = keep old value)
            System.out.println("Aktueller Vorname: " + vornameAlt);
            System.out.print("Neuer Vorname (Enter = behalten): ");
            String vornameNeu = sc.nextLine();
            if (vornameNeu.isEmpty()) vornameNeu = vornameAlt;

            System.out.println("Aktueller Nachname: " + nachnameAlt);
            System.out.print("Neuer Nachname (Enter = behalten): ");
            String nachnameNeu = sc.nextLine();
            if (nachnameNeu.isEmpty()) nachnameNeu = nachnameAlt;

            System.out.println("Aktuelle Firma: " + firmaAlt);
            System.out.print("Neue Firma (Enter = behalten): ");
            String firmaNeu = sc.nextLine();
            if (firmaNeu.isEmpty()) firmaNeu = firmaAlt;

            System.out.println("Aktuelle Anschrift: " + anschriftAlt);
            System.out.print("Neue Anschrift (Enter = behalten): ");
            String anschriftNeu = sc.nextLine();
            if (anschriftNeu.isEmpty()) anschriftNeu = anschriftAlt;

            System.out.println("Aktuelle Email: " + emailAlt);
            System.out.print("Neue Email (Enter = behalten): ");
            String emailNeu = sc.nextLine();
            if (emailNeu.isEmpty()) emailNeu = emailAlt;

            // 🇩🇪 Eingaben prüfen
            // 🇬🇧 Validate inputs
            if (!emailNeu.contains("@")) {
                System.out.println("❌ Ungültige Email-Adresse.");
                return;
            }

            // 🇩🇪 Daten aktualisieren
            // 🇬🇧 Update data in database
            String updateSQL = "UPDATE kunden SET vorname=?, nachname=?, firma=?, anschrift=?, email=? WHERE id=?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
            updateStmt.setString(1, vornameNeu);
            updateStmt.setString(2, nachnameNeu);
            updateStmt.setString(3, firmaNeu);
            updateStmt.setString(4, anschriftNeu);
            updateStmt.setString(5, emailNeu);
            updateStmt.setInt(6, id);

            int rows = updateStmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Kunde wurde erfolgreich aktualisiert!");
            } else {
                System.out.println("❌ Aktualisierung fehlgeschlagen.");
            }

        } catch (Exception e) {
            System.out.println("❌ Fehler: " + e.getMessage());
        }
    }
}
