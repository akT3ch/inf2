import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Mitarbeiter {
    private String name;
    private int alter;
    private LocalDate einstellungsdatum;
    private BigDecimal gehalt;

    public Mitarbeiter(String name, int alter, LocalDate einstellungsdatum, BigDecimal gehalt) {
        this.name = name;
        this.alter = alter;
        this.einstellungsdatum = einstellungsdatum;
        this.gehalt = gehalt;
    }

    public String getName() { return name; }
    public int getAlter() { return alter; }
    public LocalDate getEinstellungsdatum() { return einstellungsdatum; }
    public BigDecimal getGehalt() { return gehalt; }

    @Override
    public String toString() {
        return name + ", " + alter + ", " + einstellungsdatum + ", " + gehalt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mitarbeiter that = (Mitarbeiter) o;
        return Objects.equals(name, that.name) && Objects.equals(einstellungsdatum, that.einstellungsdatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, einstellungsdatum);
    }

    public String toFileString() {
        return name + ";" + alter + ";" + einstellungsdatum + ";" + gehalt;
    }
}

public class MitarbeiterManager {
    public static void main(String[] args) throws IOException {
        List<Mitarbeiter> mitarbeiterListe = Files.lines(Paths.get("mitarbeiter.txt"))
                .map(line -> {
                    String[] teile = line.split(";");
                    return new Mitarbeiter(
                            teile[0],
                            Integer.parseInt(teile[1]),
                            LocalDate.parse(teile[2]),
                            new BigDecimal(teile[3])
                    );
                })
                .distinct()
                .collect(Collectors.toList());

        // Sortierungen
        System.out.println("--- Sortiert nach Name ---");
        mitarbeiterListe.stream()
                .sorted(Comparator.comparing(Mitarbeiter::getName))
                .forEach(System.out::println);

        System.out.println("\n--- Sortiert nach Einstellungsdatum ---");
        mitarbeiterListe.stream()
                .sorted(Comparator.comparing(Mitarbeiter::getEinstellungsdatum))
                .forEach(System.out::println);

        System.out.println("\n--- Sortiert nach Gehalt (absteigend) ---");
        mitarbeiterListe.stream()
                .sorted(Comparator.comparing(Mitarbeiter::getGehalt).reversed())
                .forEach(System.out::println);

        // Durchschnittsalter
        double avgAge = mitarbeiterListe.stream()
                .mapToInt(Mitarbeiter::getAlter)
                .average()
                .orElse(0);
        System.out.printf("\nDurchschnittsalter: %.2f\n", avgAge);

        // Höchstes Gehalt
        BigDecimal maxGehalt = mitarbeiterListe.stream()
                .map(Mitarbeiter::getGehalt)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        System.out.println("Höchstes Gehalt: " + maxGehalt);

        // Gruppierung nach Einstellungsjahr
        Map<Integer, Long> gruppierung = mitarbeiterListe.stream()
                .collect(Collectors.groupingBy(m -> m.getEinstellungsdatum().getYear(), Collectors.counting()));
        System.out.println("\nAnzahl Mitarbeiter pro Jahr:");
        gruppierung.forEach((jahr, anzahl) -> System.out.println(jahr + ": " + anzahl));

        // Neueingabe
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Neuen Mitarbeiter eingeben ---");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Alter: ");
        int alter = Integer.parseInt(scanner.nextLine());
        System.out.print("Einstellungsdatum (YYYY-MM-DD): ");
        LocalDate datum = LocalDate.parse(scanner.nextLine());
        System.out.print("Gehalt: ");
        BigDecimal gehalt = new BigDecimal(scanner.nextLine());

        mitarbeiterListe.add(new Mitarbeiter(name, alter, datum, gehalt));

        // Ausgabe in neue Datei
        Files.write(Paths.get("mitarbeiter_neu.txt"),
                mitarbeiterListe.stream().map(Mitarbeiter::toFileString).collect(Collectors.toList()));

        System.out.println("\nDaten gespeichert in mitarbeiter_neu.txt");
    }
}
