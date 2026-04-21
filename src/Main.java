import model.UniversaleTuringmaschine;

import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File binaryFile = new File("src/binary.txt");
        BufferedReader reader = new BufferedReader(new FileReader(binaryFile));

        int pickedLine = getPickedLine();

        for (int i = 0; i < pickedLine; i++) {
            reader.readLine();
        }

        String binaryCode = reader.readLine();

        UniversaleTuringmaschine UTM = new UniversaleTuringmaschine(binaryCode);

        String input = getInput();

        UTM.setInput(input);
        int pickedMode = getpickedMode();

        switch (pickedMode) {
            case 1:
                while (UTM.getAccepted().isEmpty()) {
                    System.out.println("Aktuelle Zustand:");
                    UTM.printState();
                    System.out.println();
                    UTM.calculateStep();
                    Thread.sleep(100);
                }
                break;
            case 2:
                UTM.calculateAll();
                break;
        }

        if (UTM.getAccepted().isPresent() && UTM.getAccepted().get()) {
            System.out.println("Input wird Akzeptiert");
            Optional<String> result = UTM.getResultIfAccepted();
            if (result.isPresent()) {
                System.out.println("Resultat: " + result.get());
            } else {
                System.out.println("Kein Resultat vorhanden");
            }
        } else {
            System.out.println("Input wird nicht Akzeptiert");
        }
        System.out.println("Aktuelle Zustand:");
        UTM.printState();
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Schreiben sie ihre Input");
        System.out.print("> ");
        return scanner.next();
    }

    private static int getpickedMode() {
        Scanner scanner = new Scanner(System.in);
        int pickedMode = 0;

        System.out.println("Modus Wählen:");
        System.out.println("1. Step-Modus");
        System.out.println("2. Lauf Modus");

        do {
            System.out.print("> ");
            if (scanner.hasNextInt()) {
                pickedMode = scanner.nextInt();
            } else {
                scanner.next();
            }

            if (pickedMode != 1 && pickedMode != 2) {
                System.out.println("Sie müssen 1 oder 2 eingeben");
            }
        } while (pickedMode != 1 && pickedMode != 2);
        return pickedMode;
    }

    private static int getPickedLine() {
        Scanner scanner = new Scanner(System.in);
        int pickedLine = 0;
        System.out.println("Type the Line Position of the desired Binary Code");
        do {
            System.out.print("> ");
            if (scanner.hasNextInt()) {
                pickedLine = scanner.nextInt();
            } else {
                scanner.next();
            }

            if (pickedLine < 1) {
                System.out.println("Number needs to be 1 or higher.");
            }
        } while (pickedLine < 1);

        pickedLine--;
        return pickedLine;
    }


}