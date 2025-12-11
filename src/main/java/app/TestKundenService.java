package app;

import java.util.List;

public class TestKundenService {
    public static void main(String[] args) {
        // Add a customer
        boolean added = KundenService.addKunde("Anna", "Schmidt", "Schmidt GmbH", "Beispielstraße 5, Berlin", "anna@schmidt.de");
        System.out.println("Added customer: " + added);

        // List all customers
        List<String[]> allKunden = KundenService.listKundenArray();
        System.out.println("All customers:");
        for (String[] k : allKunden) {
            System.out.println(String.join(", ", k));
        }

        // Update a customer (first one)
        if (!allKunden.isEmpty()) {
            int id = Integer.parseInt(allKunden.get(0)[0]);
            boolean updated = KundenService.updateKunde(id, "Maximilian", "Mustermann", "Musterfirma", "Musterstraße 1, 12345 Berlin", "max@mustermann.de");
            System.out.println("Updated first customer: " + updated);
        }

        // Delete the last customer
        if (allKunden.size() > 1) {
            int lastId = Integer.parseInt(allKunden.get(allKunden.size() - 1)[0]);
            boolean deleted = KundenService.deleteKunde(lastId);
            System.out.println("Deleted last customer: " + deleted);
        }
    }
}
