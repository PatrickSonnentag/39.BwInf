package aufgabe3;

import java.util.ArrayList;
import java.util.Arrays;

class VariantenErmittler {

	private ArrayList<double[]> getOptVerh() {
		InputVerarbeiter inputVerarbeiter=new InputVerarbeiter();
		ArrayList<Integer[]> spielstaerken=inputVerarbeiter.sortierterInput();
		ArrayList<double[]> optVerh=new ArrayList<double[]>();
		for(int i=0; i<spielstaerken.size(); i++) {    //Zaehlt Dateien durch
			double[] verh = new double[spielstaerken.get(i).length];
			for(int j=0; j<spielstaerken.get(i).length; j++) {	//Zaehlt Spieler durch
				int staerkenGes=0;
				for(int k=0; k<spielstaerken.get(i).length; k++) {
					staerkenGes+=spielstaerken.get(i)[k];
				}
				verh[j]=(double)(spielstaerken.get(i)[j])/(double)(staerkenGes);
			}
			optVerh.add(verh);
		}
		return optVerh;
	}

	private ArrayList<ArrayList<double[]>> getPraktVerh(int wiederholungen) {
		InputVerarbeiter inputVerarbeiter = new InputVerarbeiter();
		ArrayList<Integer[]> spielstaerken=inputVerarbeiter.sortierterInput();
		TurnierVarianten turnierVarianten=new TurnierVarianten();
		ArrayList<ArrayList<int[]>> gewinne=turnierVarianten.gewinnHaeufigkeit(wiederholungen);
		ArrayList<ArrayList<double[]>> praktVerh=new ArrayList<ArrayList<double[]>>();
		for(int i=0; i<spielstaerken.size(); i++) {    
			ArrayList<double[]> dateiListe=new ArrayList<double[]>();	
			praktVerh.add(dateiListe);
			for(int j=0; j<spielstaerken.get(i).length; j++) {	
				double[] spielerVerh=new double[gewinne.get(i).size()];
				praktVerh.get(i).add(spielerVerh);
			}
		}
		for(int i=0; i<praktVerh.size(); i++) {    
			for(int j=0; j<praktVerh.get(i).size(); j++) {	
				for(int k=0; k<praktVerh.get(i).get(j).length; k++) {	
					praktVerh.get(i).get(j)[k]=(double)(gewinne.get(i).get(k)[j])/(double)(wiederholungen);
				}
			}
		}
		return praktVerh;
	}

	int[] getFairsteVariante(int wiederholungen) {
		ArrayList<ArrayList<double[]>> praktVerh=getPraktVerh(wiederholungen);
		ArrayList<double[]> optVerh=getOptVerh();
		ArrayList<ArrayList<double[]>> fairness=new ArrayList<ArrayList<double[]>>();
		for(int i=0; i<praktVerh.size(); i++) {    
			ArrayList<double[]> dateiListe=new ArrayList<double[]>();	
			fairness.add(dateiListe);
			for(int j=0; j<optVerh.get(i).length; j++) {	
				double[] spielerVerh=new double[praktVerh.get(i).get(j).length];	
				fairness.get(i).add(spielerVerh);
			}
		}
		for(int i=0; i<fairness.size(); i++) {	
			for(int j=0; j<fairness.get(i).size(); j++) {			
				for(int k=0; k<fairness.get(i).get(j).length; k++) {	
					fairness.get(i).get(j)[k]=optVerh.get(i)[j]-praktVerh.get(i).get(j)[k];
				}
			}
		}
		int[] besteVarianten=new int[7];	
		for(int i=0; i<fairness.size(); i++) {	
			for(int j=0; j<fairness.get(i).size(); j++) {	
				double[] temp=fairness.get(i).get(j);
				double naechsterWert=getNaechsteZuNull(temp);
				for(int k=0; k<fairness.get(i).get(j).length; k++) {
					if(fairness.get(i).get(j)[k]==naechsterWert) {
						besteVarianten[k]++;
					}
				}
			}
		}
		return besteVarianten;
	}

	private double getNaechsteZuNull(double[] arr) {
		 double momentanerWert=0;
	     double closestVal=arr[0];
	     Arrays.sort(arr);
	     for (int i=0; i<arr.length; i++) {
	    	 momentanerWert=arr[i]*arr[i];
	         if (momentanerWert<=(closestVal*closestVal)) {
	             closestVal=arr[i];
	         }
	     }
	     return closestVal;
	}
}