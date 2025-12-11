package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import app.Kunde;

public class KundenService {

    /**
     * Gibt alle Kunden als Liste von String-Arrays zurück.
     * Jede Zeile = ein Kunde, jedes Feld = Spalte aus der DB.
     */
    public static List<String[]> listKundenArray() {
        List<String[]> result = new ArrayList<>();

        String sql = "SELECT * FROM kunden";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] row = new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getString("firma"),
                        rs.getString("anschrift"),
                        rs.getString("email")
                };
                result.add(row);
            }

        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Laden der Kunden: " + e.getMessage());
        }

        return result;
    }

    /**
     * Alternative Version, die dieselben Daten als Liste von CSV-Zeilen liefert.
     * Wird von GUI oder Main verwendet, wenn dort listKundenLines() aufgerufen wird.
     */
    public static List<String> listKundenLines() {
        List<String> lines = new ArrayList<>();
        for (String[] row : listKundenArray()) {
            lines.add(String.join(",", row));
        }
        return lines;
    }

    /**
     * Fügt einen neuen Kunden in die Datenbank ein.
     */
    public static boolean addKunde(String vorname, String nachname, String firma, String anschrift, String email) {
        String sql = "INSERT INTO kunden (vorname, nachname, firma, anschrift, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vorname);
            ps.setString(2, nachname);
            ps.setString(3, firma);
            ps.setString(4, anschrift);
            ps.setString(5, email);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Hinzufügen: " + e.getMessage());
            return false;
        }
    }

    /**
     * Aktualisiert einen vorhandenen Kunden anhand der ID.
     */
    public static boolean updateKunde(int id, String vorname, String nachname, String firma, String anschrift, String email) {
        String sql = "UPDATE kunden SET vorname=?, nachname=?, firma=?, anschrift=?, email=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vorname);
            stmt.setString(2, nachname);
            stmt.setString(3, firma);
            stmt.setString(4, anschrift);
            stmt.setString(5, email);
            stmt.setInt(6, id);

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Aktualisieren: " + e.getMessage());
            return false;
        }
    }

    /**
     * Löscht einen Kunden anhand seiner ID.
     * Wird nur ausgeführt, wenn der Benutzer Admin ist (prüft GUI).
     */
    public static boolean deleteKunde(int id) {
        String sql = "DELETE FROM kunden WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Löschen: " + e.getMessage());
            return false;
        }
    }

    /**
     * Optional: Einen einzelnen Kunden anhand der ID abrufen (z. B. fürs Update-Fenster)
     */
    public static Kunde getKundeById(int id) {
        String sql = "SELECT * FROM kunden WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Kunde(
                        rs.getInt("id"),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getString("firma"),
                        rs.getString("anschrift"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            System.out.println("⚠️ Fehler beim Abrufen des Kunden: " + e.getMessage());
        }

        return null;
    }

} // <- Stelle sicher, dass diese Klammer hier nicht fehlt!
