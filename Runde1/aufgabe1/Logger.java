package aufgabe1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    long startTimer;

    File file;
    static final String dateTime = java.time.LocalDate.now() + " " + java.time.LocalTime.now() +"] ";

    public Logger(String filePath) throws IOException {
        this.file = new File(filePath);
    }

    public void log(Exception e) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write("["+ eType.EXCEPTION+": " + dateTime + e.toString()+"\n");
        bufferedWriter.close();
    }

    public void log(eType type, String input) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write("["+ type + ": "+ dateTime + input + "\n");
        bufferedWriter.close();
    }

    public void log(String input) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write("["+ eType.INFORMATION + ": "+ dateTime + input + "\n");
        bufferedWriter.close();
    }

    public void deleteLog() throws IOException {
        file.delete();
        log("Log cleared");
    }

    public void timeStart(){
        startTimer = System.nanoTime();
    }

    public double timeStop(){
        long stopTime = System.nanoTime();
        double time = (stopTime-startTimer)/1000000000d;
        time = Math.round(time*1000.0)/1000.0;
        return time;
    }

}
