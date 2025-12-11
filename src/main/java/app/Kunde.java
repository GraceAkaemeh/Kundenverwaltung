package app;

public class Kunde {

    private int id;
    private String vorname;
    private String nachname;
    private String firma;
    private String anschrift;
    private String email;

    // Konstruktor
    public Kunde(int id, String vorname, String nachname, String firma, String anschrift, String email) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.firma = firma;
        this.anschrift = anschrift;
        this.email = email;
    }

    // Getter und Setter für alle Felder
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Kunde [id=" + id + ", vorname=" + vorname + ", nachname=" + nachname + ", firma=" + firma + ", anschrift=" + anschrift + ", email=" + email + "]";
    }
}
