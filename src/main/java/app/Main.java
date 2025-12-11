package app;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Kundenverwaltung ===");
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Passwort: ");
        String pass = sc.nextLine();

        String role = AuthenticationService.login(user, pass);
        if (role == null) {
            System.out.println("Anmeldung fehlgeschlagen. Beenden.");
            return;
        }
        System.out.println("Login OK. Role = " + role);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1) Kunden anzeigen");
            System.out.println("2) Kunde hinzufügen");
            System.out.println("3) Kunde aktualisieren");

            if(role.equals("admin")) {
                System.out.println("4) Kunde löschen (admin only)");
            }

            System.out.println("0) Beenden");
            System.out.print("Auswahl: ");
            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1":
                        List<String[]> kunden = KundenService.listKundenArray();

                        System.out.println("=== Kundenliste ===");
                        for (String[] k : kunden) {
                            System.out.println(String.join(", ", k));
                        }
                        break;

                    case "2": // Kunde hinzufügen
                        String v;
                        do {
                            System.out.print("Vorname: ");
                            v = sc.nextLine().trim();
                            if(v.isEmpty()) System.out.println("❌ Vorname darf nicht leer sein.");
                        } while(v.isEmpty());

                        String n;
                        do {
                            System.out.print("Nachname: ");
                            n = sc.nextLine().trim();
                            if(n.isEmpty()) System.out.println("❌ Nachname darf nicht leer sein.");
                        } while(n.isEmpty());

                        System.out.print("Firma: ");
                        String f = sc.nextLine().trim();
                        System.out.print("Anschrift: ");
                        String a = sc.nextLine().trim();

                        String e;
                        do {
                            System.out.print("Email: ");
                            e = sc.nextLine().trim();
                            if(e.isEmpty() || !e.contains("@"))
                                System.out.println("❌ Email darf nicht leer sein und muss ein '@' enthalten.");
                        } while(e.isEmpty() || !e.contains("@"));

                        boolean added = KundenService.addKunde(v,n,f,a,e);
                        System.out.println(added ? "✅ Kunde hinzugefügt." : "❌ Hinzufügen fehlgeschlagen.");
                        break;

                    case "3": // Kunde aktualisieren
                        System.out.print("ID to update: ");
                        int uid = Integer.parseInt(sc.nextLine());

                        String uv;
                        do {
                            System.out.print("Vorname: ");
                            uv = sc.nextLine().trim();
                            if(uv.isEmpty()) System.out.println("❌ Vorname darf nicht leer sein.");
                        } while(uv.isEmpty());

                        String un;
                        do {
                            System.out.print("Nachname: ");
                            un = sc.nextLine().trim();
                            if(un.isEmpty()) System.out.println("❌ Nachname darf nicht leer sein.");
                        } while(un.isEmpty());

                        System.out.print("Firma: ");
                        String uf = sc.nextLine().trim();
                        System.out.print("Anschrift: ");
                        String ua = sc.nextLine().trim();

                        String ue;
                        do {
                            System.out.print("Email: ");
                            ue = sc.nextLine().trim();
                            if(ue.isEmpty() || !ue.contains("@"))
                                System.out.println("❌ Email darf nicht leer sein und muss ein '@' enthalten.");
                        } while(ue.isEmpty() || !ue.contains("@"));

                        boolean updated = KundenService.updateKunde(uid, uv, un, uf, ua, ue);
                        System.out.println(updated ? "✅ Kunde aktualisiert." : "❌ Aktualisierung fehlgeschlagen.");
                        break;

                    case "4":
                        if (!"admin".equals(role)) {
                            System.out.println("❌ Nur Administratoren können Kunden löschen.");
                            break;
                        }
                        System.out.print("ID des Kunden, der gelöscht werden soll: ");
                        int did = Integer.parseInt(sc.nextLine());

                        System.out.print("Sind Sie sicher, dass Sie diesen Kunden löschen möchten? (j/n): ");
                        String confirm = sc.nextLine().trim().toLowerCase();

                        if (!confirm.equals("j")) {
                            System.out.println("Abgebrochen. Der Kunde wurde nicht gelöscht.");
                            break;
                        }

                        boolean deleted = KundenService.deleteKunde(did);
                        System.out.println(deleted ? "✅ Kunde erfolgreich gelöscht." : "❌ Löschen fehlgeschlagen.");
                        break;

                    case "0":
                        System.out.println("Tschüss.");
                        return;
                    default:
                        System.out.println("Unbekannte Wahl.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Ungültige Zahleneingabe.");
            }
        }
    }
}
