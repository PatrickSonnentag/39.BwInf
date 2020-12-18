package aufgabe3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

class TurnierVarianten {

	public ArrayList<ArrayList<int[]>> gewinnHaeufigkeit(int wiederholungen) {
		InputVerarbeiter inputVerarbeiter = new InputVerarbeiter();
		ArrayList<ArrayList<int[]>> gewinne = new ArrayList<ArrayList<int[]>>();
		ArrayList<Integer[]> spielstaerken=inputVerarbeiter.sortierterInput();
		int[] spielerAnzahl= {spielstaerken.get(0).length,
				spielstaerken.get(1).length,
				spielstaerken.get(2).length,
				spielstaerken.get(3).length};
		for(int i=0; i<spielstaerken.size(); i++) {	
			gewinne.add(new ArrayList<int[]>());
			gewinne.get(i).add(liga(wiederholungen, spielstaerken.get(i), spielerAnzahl[i]));
			for(int j=0; j<3; j++) {	
			gewinne.get(i).add(ko(wiederholungen, spielstaerken.get(i), spielerAnzahl[i], getKoPlan(j, spielerAnzahl[i])));
			gewinne.get(i).add(ko5(wiederholungen,spielstaerken.get(i), spielerAnzahl[i], getKoPlan(j, spielerAnzahl[i])));
			}
		}
		return gewinne;
	}

	private boolean kugelnZiehen(int staerke1, int staerke2) {
		boolean sp1gewinnt;
		int gezogeneZahl=ThreadLocalRandom.current().nextInt(1, staerke1+staerke2+1);
		if(gezogeneZahl<=staerke1) {	
			sp1gewinnt=true;
		}
		else {	
			sp1gewinnt=false;
		}
		return sp1gewinnt;
	}

	private ArrayList<Integer> getKoPlan(int nummer, int spielerAnzahl) {
		ArrayList<Integer> plan=new ArrayList<Integer>();
		plan.clear();
		if(nummer==0) {
			for(int i=0; i<spielerAnzahl/2; i++) {
				plan.add(i);
				plan.add(spielerAnzahl-(i+1));
			}
		}
		if(nummer==1) {
			for(int i=0; i<spielerAnzahl; i++) {
				plan.add(i);
			}
		}
		if(nummer==2) {
			for(int i=0; i<spielerAnzahl; i++) {
				plan.add(i);
			}
			Collections.shuffle(plan);
		}
		return plan;
	}
	
	private int[] liga(int wiederholungen, Integer[] staerken, int spielerAnzahl) {
		ArrayList<Integer> gewinneRunde=new ArrayList<Integer>();
		int[] gewinneGesamt=new int[spielerAnzahl];
		for(int h=0; h<spielerAnzahl; h++) {
			gewinneRunde.add(0);
			gewinneGesamt[h]=0;
		}
		for(int i=0; i<wiederholungen; i++) {
			for(int j=0; j<spielerAnzahl; j++) {
				for(int k=j+1; k<spielerAnzahl; k++) {
					if(kugelnZiehen(staerken[j], staerken[k])) {
						gewinneRunde.set(j, gewinneRunde.get(j)+1);
					}
					else {
						gewinneRunde.set(k, gewinneRunde.get(k)+1);
					}
				}
			}

			ArrayList<Integer> gleicheSiege=new ArrayList<Integer>();
			Integer hoechsterWert=Collections.max(gewinneRunde);
			for (int j=0; j<gewinneRunde.size(); j++) {
			    if (hoechsterWert.equals(gewinneRunde.get(j))) {
			    	gleicheSiege.add(j);
			    }
			}
			Integer kleinsterWert=Collections.min(gleicheSiege);
			gewinneGesamt[kleinsterWert]++;
		}
		return gewinneGesamt;
	}

	private int[] ko(int wiederholungen, Integer[] staerken, int spielerAnzahl, ArrayList<Integer> koPlan) {
		ArrayList<Integer> gewinneRunde=new ArrayList<Integer>();
		int[] gewinneGesamt=new int[spielerAnzahl];
		ArrayList<Integer> rundenPlan;
		for(int h=0; h<spielerAnzahl; h++) {
			gewinneGesamt[h]=0;
		}
		for(int i=0; i<wiederholungen; i++) {
			rundenPlan=new ArrayList<Integer>(koPlan);
			for(int j=0; j<(Math.log10(spielerAnzahl))/Math.log10(2); j++) {
				for(int k=0; k<rundenPlan.size(); k+=2) {
					if(kugelnZiehen(staerken[rundenPlan.get(k)], staerken[rundenPlan.get(k+1)])) {
						gewinneRunde.add(rundenPlan.get(k));
					}
					else {
						gewinneRunde.add(rundenPlan.get(k+1));
					}
				}	
				rundenPlan=new ArrayList<Integer>(gewinneRunde);
				gewinneRunde.clear();
			}
			gewinneGesamt[rundenPlan.get(0)]++;
		}
		return gewinneGesamt;
	}

	private int[] ko5(int wiederholungen, Integer[] staerken, int spielerAnzahl, ArrayList<Integer> koPlan) {		
		ArrayList<Integer> gewinneRunde=new ArrayList<Integer>();
		int[] gewinneGesamt=new int[spielerAnzahl];
		ArrayList<Integer> rundenPlan;
		for(int h=0; h<spielerAnzahl; h++) {
			gewinneGesamt[h]=0;
		}
		for(int i=0; i<wiederholungen; i++) {
			rundenPlan=new ArrayList<Integer>(koPlan);
			for(int j=0; j<(Math.log10(spielerAnzahl))/Math.log10(2); j++) {
				for(int k=0; k<rundenPlan.size(); k+=2) {
					int gewinne=0;
					for(int l=0; l<5; l++) {
						if(kugelnZiehen(staerken[rundenPlan.get(k)], staerken[rundenPlan.get(k+1)])) {
							gewinne++;
						}
					}
					if(gewinne>2) {
						gewinneRunde.add(rundenPlan.get(k));
					}
					else {
						gewinneRunde.add(rundenPlan.get(k+1));
					}
				}	
				rundenPlan=new ArrayList<Integer>(gewinneRunde);
				gewinneRunde.clear();
			}
			gewinneGesamt[rundenPlan.get(0)]++;
		}
		return gewinneGesamt;
	}
}