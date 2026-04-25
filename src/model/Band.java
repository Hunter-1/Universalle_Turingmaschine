package model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Band {
    private final char[] bandSymbols = {'0','1',' ','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private final int[] directions = {-1,1};
    private final TreeMap<Integer, Character> band = new TreeMap<>();
    private int currentPosition = 0;

    private final int printWidth = 15;

    public Band(String initialInput){
        for (int i = 0; i < initialInput.length(); i++) {
            band.put(i,initialInput.charAt(i));
        }
    }

    private Character getCharAt(Integer position){
        return band.getOrDefault(position, bandSymbols[2]);
    }

    public Character read(){
        return getCharAt(currentPosition);
    }

    public int readIndex(){
        Character readChar = getCharAt(currentPosition);
        for (int i = 0; i < bandSymbols.length; i++) {
            if (bandSymbols[i] == readChar){
                return i;
            }
        }
        return -1;
    }

    public void write(int symbolPosition, int directionPosition){
        band.put(currentPosition,bandSymbols[symbolPosition-1]);
        currentPosition += directions[directionPosition-1];
    }

    public String toString(){
        int startPosition = currentPosition-printWidth;
        StringBuilder builder = new StringBuilder();
        builder.repeat(" ", printWidth*2);
        builder.append(" ↓\n");
        for (int i = 0; i < (printWidth*2)+1; i++) {
            builder.append("|");
            builder.append(getCharAt(startPosition+i));
        }
        builder.append("|");
        return builder.toString();
    }

    public String bandContent(){
        StringBuilder builder = new StringBuilder();
        int firstPosition = band.firstKey();
        int lastPosition = band.lastKey();
        for (int i = firstPosition; i <= lastPosition; i++) {
            builder.append(getCharAt(i));
        }
        String output = builder.toString();
        output = output.strip();
        return output;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }
}
