/**
 * 
 */
package klausurvorbereitung;

//import
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Klausur extends JFrame {
	int i = 0;
	Material Material = new Material();
	ArrayList<Material> materialListe = new ArrayList<Material>();
	String pfad = "data/Material.csv";
	/**
	 * Fenster-Id; generieren
	 */
	private static final long serialVersionUID = -618342665788019L;

	// Elemete des Fensters
	JLabel lblTitle;
	JLabel lblId;
	JLabel lblBeschreibung;
	JLabel lblWerkstoff;

	JTextField txtId;
	JTextField txtBeschreibung;
	JTextField txtWerkstoff;

	JButton btnLesen;
	JButton btnNaechstes;
	JButton btnExit;

	public static void main(String[] args) {
		Klausur fenster = new Klausur();
		// Fenstergroesse festlegen
		fenster.setSize(800, 650);
		// Aktion beim Klick auf das Schliessen-Element des Fensters
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Komponenten (Schaltflächen, inputs, etc.) erzeugen.
		fenster.initComponents();
		// Listener für die Schaltflaeche initialisieren
		fenster.initListener();
		// Standardlayout auf null setzen, damit die Werte der Elemente
		// übernommen werden
		fenster.setLayout(null);
		// Fenster sichtbar schalten.
		fenster.setVisible(true);
	}

	private void initComponents() {
		// Titel
		lblTitle = new JLabel("Materialliste");
		lblTitle.setBounds(250, 12, 450, 30);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setVisible(true);
		this.add(lblTitle);

		// Textfelder und Label
		lblId = new JLabel("ID");
		lblId.setBounds(80, 80, 100, 20);
		lblId.setVisible(true);
		txtId = new JTextField("");
		txtId.setBounds(200, 80, 400, 30);
		this.add(lblId);
		this.add(txtId);

		lblBeschreibung = new JLabel("Beschreibung");
		lblBeschreibung.setBounds(80, 140, 100, 20);
		lblBeschreibung.setVisible(true);
		txtBeschreibung = new JTextField("");
		txtBeschreibung.setBounds(200, 140, 400, 30);
		this.add(lblBeschreibung);
		this.add(txtBeschreibung);

		lblWerkstoff = new JLabel("Werkstoff");
		lblWerkstoff.setBounds(80, 200, 100, 20);
		lblWerkstoff.setVisible(true);
		txtWerkstoff = new JTextField("");
		txtWerkstoff.setBounds(200, 200, 400, 30);
		this.add(lblWerkstoff);
		this.add(txtWerkstoff);

		// Buttons
		btnLesen = new JButton("Lesen");
		btnLesen.setBounds(80, 400, 150, 30);
		this.add(btnLesen);

		btnNaechstes = new JButton("Naechstes");
		btnNaechstes.setBounds(280, 400, 150, 30);
		this.add(btnNaechstes);

		btnExit = new JButton("Beenden");
		btnExit.setBounds(480, 400, 150, 30);
		this.add(btnExit);
	}

	private void initListener() {
		btnLesen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				try {
					reset();
					readFile();
					output(i);

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});

		btnNaechstes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				output(++i);
			}
		});

		// Zum Schliessen des Fensters
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				JOptionPane
						.showMessageDialog(null, "Das Programm wird beendet");
				System.exit(0);
			}
		});
	}

	public class Material {
		private String ID;
		private String Beschreibung;
		private String Werkstoff;

		public Material() {}//default-Construktor
		
//Spezialisierter Construktor
		public Material(String iD, String beschreibung, String werkstoff) {
			//super();
			ID = iD;
			Beschreibung = beschreibung;
			Werkstoff = werkstoff;
		}
		
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getBeschreibung() {
			return Beschreibung;
		}
		public void setBeschreibung(String beschreibung) {
			Beschreibung = beschreibung;
		}
		public String getWerkstoff() {
			return Werkstoff;
		}
		public void setWerkstoff(String werkstoff) {
			Werkstoff = werkstoff;
		}
		@Override
		public String toString() {
			return "Material [ID=" + ID + ", Beschreibung=" + Beschreibung
					+ ", Werkstoff=" + Werkstoff + "]";
		}
	}
	// Schreiben in CSV
	/*
	 * public void write(String in) throws IOException { FileWriter fw = new
	 * FileWriter(pfad); BufferedWriter bw = new BufferedWriter(fw);
	 * 
	 * bw.append(in); bw.newLine();
	 * 
	 * bw.close(); }
	 */
	// Lesen von CSV
	public void readFile() throws IOException {
		FileReader fr = new FileReader(pfad);
		BufferedReader br = new BufferedReader(fr);
		
		while ((br.readLine()) != null) {
			String[] parts = br.readLine().split(";");
			Material Material = new Material(parts[0], parts[1], parts[2]);
			materialListe.add(Material);
		}
		System.out.println(materialListe);

		br.close();
	}
	//resetet input-Field 
	public void reset(){
		i = 0;
		materialListe.clear();
	}
	//Ausgaebe in Textfeld
	public void output(int i) {
		if (i < materialListe.size()) {
			btnNaechstes.setEnabled(true);
			txtId.setText(materialListe.get(i).getID());
			txtWerkstoff.setText((materialListe.get(i).getWerkstoff()));
			txtBeschreibung.setText((materialListe.get(i).getBeschreibung()));
		}else if(i == materialListe.size()){
			btnNaechstes.setEnabled(false);
		}
	}

}
