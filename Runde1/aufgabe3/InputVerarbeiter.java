package aufgabe3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

class InputVerarbeiter {

	public ArrayList<Integer[]> sortierterInput() {
		String jarPfad="";	
		try {
			jarPfad = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			jarPfad=jarPfad.replace("Aufgabe3.jar", "");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}		
		ArrayList<Integer[]> spielstaerken=new ArrayList<Integer[]>();
		File[] file=new File[4];
		Scanner[] scan=new Scanner[4];
		
		for(int i=0; i<4; i++) {
			int j=i+1;	
			file[i]=new File(jarPfad+"a3-Tobis-Turnier_beispieldaten_spielstaerken"+j+".txt");
			try {
				scan[i]=new Scanner(new FileInputStream(file[i]), "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			j=0;	
			ArrayList<Integer> tempList=new ArrayList<Integer>();	
			while(scan[i].hasNextLine()) {
				if(j!=0) {	
					tempList.add(Integer.parseInt(scan[i].nextLine()));
				}
				else {
					scan[i].nextLine();	
				}
				j++;
			}
			spielstaerken.add(tempList.toArray(new Integer[tempList.size()]));
		}
		return spielstaerken;
	}
}