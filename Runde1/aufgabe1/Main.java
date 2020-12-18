package aufgabe1;

import java.io.*;
import java.util.ArrayList;

public class Main {

    static String leertext;
    static String worte;

    public static void main(String[] args) throws IOException {
        Logger logger = new Logger("log.txt");
        logger.deleteLog();
        logger.timeStart();

        try {
            File raetsel = new File("raetsel.txt");
            FileInputStream fileInputStream = new FileInputStream(raetsel);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF8"));
            leertext = bufferedReader.readLine();
            worte = bufferedReader.readLine();
            bufferedReader.close();
            logger.log("raetsel.txt gefunden");
        } catch (IOException e) {
            logger.log(e);
        }

        String[] splitLuckenText = format(leertext);
        String[] luckenText = split(leertext);
        String[] splitWorte = format(worte);

        ArrayList<String> wortPool = new ArrayList<>();
        for (String x : splitWorte) {
            wortPool.add(x);
        }

        String[] output = new String[splitLuckenText.length];

        while(!wortPool.isEmpty()) {
            int zuordnungen = 0;
            for (int i = 0; i < splitLuckenText.length; i++) {
                int fitCount = 0;
                int fitIndex = 0;
                String[] fitString = new String[wortPool.size()];
                for (String x : wortPool) {
                    if (fits(splitLuckenText[i], x)) {
                        fitString[fitCount] = x;
                        fitCount++;
                        fitIndex = i;
                    }
                }
                if (fitCount == 1) {//Prüft auf einzigartigkeit
                    wortPool.remove(fitString[0]);
                    output[fitIndex] = fitString[0];
                    splitLuckenText[i] = "";
                    zuordnungen++;
                }
                
                else if (fitCount > 1) {//Prüft auf doppelte Instanzen
                    boolean allEquals = true;
                    for (int k = 0; k < fitCount-1; k++) {
                        if (!(fitString[k].equals(fitString[k+1]))){
                            allEquals = false;
                        }
                    }
                    if (allEquals) {
                        wortPool.remove(fitString[0]);
                        output[fitIndex] = fitString[0];
                        splitLuckenText[i] = "";
                        zuordnungen++;
                    }
                }
            }
            if(zuordnungen==0){
                logger.log(eType.WARNING, "Diese Lösung ist nicht eindeutig!");
                break;
            }
        }

        String outputString = "";
        for (int i = 0; i < luckenText.length; i++) {
            for (int j = 0; j < luckenText[i].length(); j++) {
                try {
                    outputString += output[i].charAt(j);
                } catch (Exception satzzeichen) {
                    outputString += luckenText[i].charAt(j);
                }
            }
            outputString += " ";
        }
        saveToFile(outputString);

        logger.log("Executiontime: " + logger.timeStop() + "s");
    }

    public static String[] format(String input) {
        input = input.replaceAll("[.,!*+?'#:-]", "");
        return split(input);
    }

    public static String[] split(String input) {
        String[] splitinput = input.split(" ");
        return splitinput;
    }

    public static boolean isUnderscoreOnly(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != '_') {
                return false;
            }
        }
        return true;
    }

    public static boolean fits(String luckenWort, String fillWort) {
        if (luckenWort.length() != fillWort.length()) {
            return false;
        }
        for (int i = 0; i < luckenWort.length(); i++) {
            if (luckenWort.charAt(i) == fillWort.charAt(i)) {
                return true;
            }
        }
        return isUnderscoreOnly(luckenWort);
    }

    public static void saveToFile(String input) throws IOException {
        try {
            FileWriter myWriter = new FileWriter("loesung.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(myWriter);
            bufferedWriter.write(input);
            bufferedWriter.close();
        } catch (IOException e) {
            Logger logger = new Logger("log.txt");
            logger.log(e);
        }
    }
}