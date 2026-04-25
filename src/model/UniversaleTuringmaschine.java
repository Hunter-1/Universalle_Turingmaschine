package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UniversaleTuringmaschine {

    private int currentState = 1;
    private final int endState = 2;
    private final Map<Observation, Instruction> map = new HashMap<>();
    private Band band;
    private Optional<Boolean> accepted = Optional.empty();

    private int calculationCounter = 0;

    public UniversaleTuringmaschine(String binaryCode){
        String[] transitionFunctions = binaryCode.split("11");
        for (String function : transitionFunctions){
            String[] functionZeros = function.split("1");
            int[] functionValues = new int[functionZeros.length];
            for (int i = 0; i < functionZeros.length; i++) {
                functionValues[i] = getZeroCount(functionZeros[i]);
            }
            storeFunction(functionValues);
        }
    }

    public void calculateStep(){
        if (accepted.isPresent()){
            return;
        }
        calculation();
    }

    public void calculateAll(){
        while(accepted.isEmpty()){
            calculation();
        }
    }

    private void calculation() {
        int readSymbol = band.readIndex() + 1;
        Observation observation = new Observation(currentState, readSymbol);
        Instruction instruction = map.get(observation);
        if (instruction != null){
            currentState = instruction.state();
            band.write(instruction.symbol(), instruction.direction());
            calculationCounter++;
        } else {
            setAccepted(currentState == endState);
        }
    }

    public void setInput(String input){
        calculationCounter = 0;
        currentState = 1;
        band = new Band(input);
    }

    public void printState(){
        System.out.println(band.toString());
        System.out.println("Current State: " + currentState);
        System.out.println("Current Position in Band: " + band.getCurrentPosition());
        System.out.println("Calculations Counter: " + calculationCounter);
    }

    private int getZeroCount(String zeroString){
        int count = 0;
        for (int i = 0; i < zeroString.length(); i++) {
            if (zeroString.charAt(i) == '0'){
                count++;
            }
        }
        return count;
    }

    private void storeFunction(int[] functionValues){
        Observation observation = new Observation(functionValues[0], functionValues[1]);
        Instruction instruction = new Instruction(functionValues[2], functionValues[3], functionValues[4]);
        map.put(observation,instruction);
    }

    private void setAccepted(boolean accepted){
        this.accepted = Optional.of(accepted);
    }

    public Optional<Boolean> getAccepted(){
        return accepted;
    }

    public Optional<String> getResultIfAccepted(){
        if (accepted.isPresent() && accepted.get()) {
            return Optional.of(band.bandContent());
        } else {
            return Optional.empty();
        }
    }

}
