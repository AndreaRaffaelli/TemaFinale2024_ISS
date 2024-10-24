package main.resources;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LEDFisico implements ILED {
    private Process p;
    private BufferedWriter writer;

    public LEDFisico() {
        try {
            // Avvia il processo Python che esegue LedDevice.py
            this.p = Runtime.getRuntime().exec("python3 src/main/resources/Led.py");
            this.writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean turnOn() {
        return sendCommand("on");
    }

    @Override
    public boolean turnOff() {
        return sendCommand("off");
    }

    @Override
    public boolean turnBlink() {
        return sendCommand("blink");
    }

    private boolean sendCommand(String command) {
        try {
            // Invia il comando al processo Python tramite stdin
            writer.write(command);
            writer.newLine();  // Aggiunge una nuova riga per inviare il comando correttamente
            writer.flush();    // Assicura che il comando venga inviato subito
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metodo per chiudere correttamente il processo Python e liberare risorse
    public void close() {
        try {
            writer.close();
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
