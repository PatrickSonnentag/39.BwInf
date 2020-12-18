package aufgabe3;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
    	String jarPfad="";	
		try {
			jarPfad = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			jarPfad=jarPfad.replace("Aufgabe3.jar", "");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    	try {
    		FileWriter writer = new FileWriter(jarPfad+"BWINF Aufgabe 3 Ausgabe.txt");
    		VariantenErmittler variantenErmittler=new VariantenErmittler();
        	int[] fairness=variantenErmittler.getFairsteVariante(25000);	
			writer.write("Liga war fuer "+fairness[0]+" Spieler die fairste Variante\n");
			System.out.println("Liga war fuer "+fairness[0]+" Spieler die fairste Variante");
			writer.write("KO mit dem Turnierplan (0 vs. n; 1 vs. n-1...) war fuer "+fairness[1]+" Spieler die fairste Variante\n");
			System.out.println("KO mit dem Turnierplan (0 vs. n; 1 vs. n-1...) war fuer "+fairness[1]+" Spieler die fairste Variante");
			writer.write("KO mit dem Turnierplan (0 vs. 1; 2 vs. 3...) war fuer "+fairness[2]+" Spieler die fairste Variante\n");
			System.out.println("KO mit dem Turnierplan (0 vs. 1; 2 vs. 3...) war fuer "+fairness[2]+" Spieler die fairste Variante");
			writer.write("KO mit dem zufaelligen Turnierplan war fuer "+fairness[3]+" Spieler die fairste Variante\n");
			System.out.println("KO mit dem zufaelligen Turnierplan war fuer "+fairness[3]+" Spieler die fairste Variante");
			writer.write("KO5 mit dem Turnierplan (0 vs. n; 1 vs. n-1...) war fuer "+fairness[4]+" Spieler die fairste Variante\n");
			System.out.println("KO5 mit dem Turnierplan (0 vs. n; 1 vs. n-1...) war fuer "+fairness[4]+" Spieler die fairste Variante");
			writer.write("KO5 mit dem Turnierplan (0 vs. 1; 2 vs. 3...) war fuer "+fairness[5]+" Spieler die fairste Variante\n");
			System.out.println("KO5 mit dem Turnierplan (0 vs. 1; 2 vs. 3...) war fuer "+fairness[5]+" Spieler die fairste Variante");
			writer.write("KO5 mit dem zufaelligen Turnierplan war fuer "+fairness[6]+" Spieler die fairste Variante\n");
			System.out.println("KO5 mit dem zufaelligen Turnierplan war fuer "+fairness[6]+" Spieler die fairste Variante");
			int maxIndex = 0;
	    	for (int i=1; i<fairness.length; i++) {
	    	    if (fairness[i]>fairness[maxIndex])maxIndex=i;
	    	}
	    	switch (maxIndex) {
	    	case 0:
	    		writer.write("Damit ist die Liga am fairsten!");
	    		System.out.println("Damit ist die Liga am fairsten!");
	    		break;
	    	case 1:
	    		writer.write("Damit ist das KO mit dem Turnierplan (0 vs. n; 1 vs. n-1...) am fairsten!");
	    		System.out.println("Damit ist das KO mit dem Turnierplan (0 vs. n; 1 vs. n-1...) am fairsten!");
	    		break;
	    	case 2:
	    		writer.write("Damit ist das KO mit dem Turnierplan (0 vs. 1; 2 vs. 3...) am fairsten!");
	    		System.out.println("Damit ist das KO mit dem Turnierplan (0 vs. 1; 2 vs. 3...) am fairsten!");
	    		break;
	    	case 3:
	    		writer.write("Damit ist das KO mit dem zufaelligen Turnierplan am fairsten!");
	    		System.out.println("Damit ist das KO mit dem zufaelligen Turnierplan am fairsten!");
	    		break;
	    	case 4:
	    		writer.write("Damit ist das KO5 mit dem Turnierplan (0 vs. n; 1 vs. n-1...) am fairsten!");
	    		System.out.println("Damit ist das KO5 mit dem Turnierplan (0 vs. n; 1 vs. n-1...) am fairsten!");
	    		break;
	    	case 5:
	    		writer.write("Damit ist das KO5 mit dem Turnierplan (0 vs. 1; 2 vs. 3...) am fairsten!");
	    		System.out.println("Damit ist das KO5 mit dem Turnierplan (0 vs. 1; 2 vs. 3...) am fairsten!");
	    		break;
	    	case 6:
	    		writer.write("Damit ist das KO5 mit dem zufaelligen Turnierplan am fairsten!");
	    		System.out.println("Damit ist das KO5 mit dem zufaelligen Turnierplan am fairsten!");
	    		break;
	    	}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}