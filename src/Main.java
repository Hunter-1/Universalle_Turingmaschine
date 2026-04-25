import model.UniversaleTuringmaschine;

import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File binaryFile = new File("src/binary/binary.txt");
        File binaryDescriptions = new File("src/binary/binarydescription.txt");
        BufferedReader reader = new BufferedReader(new FileReader(binaryFile));
        BufferedReader descriptionReader = new BufferedReader(new FileReader(binaryDescriptions));

        int pickedLine = getPickedLine();

        for (int i = 0; i < pickedLine; i++) {
            reader.readLine();
            descriptionReader.readLine();
        }

        String binaryCode = reader.readLine();
        System.out.println(descriptionReader.readLine());

        UniversaleTuringmaschine UTM = new UniversaleTuringmaschine(binaryCode);

        String input = getInput();

        UTM.setInput(input);
        int pickedMode = getpickedMode();

        switch (pickedMode) {
            case 1:
                int delay = getDelay();
                while (UTM.getAccepted().isEmpty()) {
                    System.out.println("Aktuelle Zustand:");
                    UTM.printState();
                    System.out.println();
                    UTM.calculateStep();
                    Thread.sleep(delay);
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

    private static int getDelay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Delay in ms:");
        int delay = 0;
        do {
            System.out.print("> ");
            if (scanner.hasNextInt()) {
                delay = scanner.nextInt();
            } else {
                scanner.next();
            }

            if (delay < 10) {
                System.out.println("Delay muss mindestens 10ms sein.");
            }
        } while (delay < 10);
        return delay;
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