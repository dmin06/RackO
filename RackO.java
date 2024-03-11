import java.util.Arrays;
public class RackO {
    public static void main(String[] args) {
        String info = "12 110"; 
        String rack = "44 35 22 25 79 100 85 69 87 3 56 28"; 
        String pile = "97 10 48 43 42 21 81 47 86 88 80 54 24 50"; 

        String finalRack = playRackO(info, rack, pile);
        System.out.println("Final Rack: " + finalRack);
    }
    public static String playRackO(String info, String rack, String pile) {
        
        String[] pileArray = pile.split(" ");
        String[] rackArray = rack.split(" ");
        int s = Integer.parseInt(info.split(" ")[0]);
        int n = Integer.parseInt(info.split(" ")[1]);
        int[] rackInt = Arrays.stream(rackArray).mapToInt(Integer::parseInt).toArray();
        int[] pileInt = Arrays.stream(pileArray).mapToInt(Integer::parseInt).toArray();
        boolean gameOver = false;
        
        
        int initialHeuristicValue = numberOfStepDowns(rackInt);
        
        while (!gameOver && pileInt.length > 0) {
            
            int drawnCard = pileInt[0];
            
            
            int[] rackAfterStrategyA = Arrays.copyOf(rackInt, rackInt.length);
            rackAfterStrategyA = strategyA(s, n, rackAfterStrategyA, drawnCard);
            int heuristicAfterStrategyA = numberOfStepDowns(rackAfterStrategyA);

            int[] rackAfterStrategyB = Arrays.copyOf(rackInt, rackInt.length);
            rackAfterStrategyB = strategyB(rackAfterStrategyB, drawnCard);
            int heuristicAfterStrategyB = numberOfStepDowns(rackAfterStrategyB);
            
            
            if (heuristicAfterStrategyA <= heuristicAfterStrategyB) {
                
                rackInt = rackAfterStrategyA;
                for (int x : rackInt) {
                    System.out.print(x + " ");
                    
                }
                System.out.print(" strategyA");
                System.out.println();
            } else {
                
                rackInt = rackAfterStrategyB;
                    
                for (int y : rackInt) {
                    System.out.print(y + " ");
                        
                }
                System.out.print(" strategyB");
                System.out.println();
                
                
            }

            if (numberOfStepDowns(rackInt) == 0) {
                gameOver = true;
            }

            pileInt = Arrays.copyOfRange(pileInt, 1, pileInt.length);
                
        }
        
        
        return Arrays.toString(rackInt).replaceAll("\\[|\\]|,", "");
    }
    
    
    private static int numberOfStepDowns(int[] rack) {
        int stepDown = 0;
        for (int i = 1; i < rack.length; i ++) {
            if (rack[i] < rack[i-1]) {
                stepDown++;
            }
        }
        return stepDown; 
    }
    
    private static int[] strategyA(int s, int n, int[] rack, int drawnCard) {
        int interval;
        int[] idealSlots = new int[s];
        if (n%s == 0) {
            interval = n/s;
        } else {
            interval = (n/s) + 1;
        }
        for (int i = 0; i < s; i++) {
            int start = i * interval + 1;
            int end = (i == s - 1) ? n : start + interval - 1;
            idealSlots[i] = end;
        }
        for (int i = 0; i < s; i++) {
            if (drawnCard >= i * interval + 1 && drawnCard <= idealSlots[i]) {
                if (rack[i] < i * interval + 1 || rack[i] > idealSlots[i] ) {
                    rack[i] = drawnCard;
                    break;
                }
            }
        }
        
        return rack;
    }
    private static int[] strategyB (int[] rack, int drawnCard) {
        // for (int i = 0; i < rack.length - 2; i++) {
        //     if (!(rack[i] < rack[i + 1] && rack[i + 1] < rack[i+2])) {
        //         if (drawnCard > rack[i] && drawnCard < rack[i+2]) {
        //             rack[i + 1] = drawnCard;
        //             break;
        //         } else if (drawnCard < rack[i + 1] && rack[i + 1] < rack[i+2]) {
        //             rack[i] = drawnCard;
        //             break;
        //         } 
        //         if (rack[i] < rack[i + 1] && rack[i + 1] < drawnCard) {
        //             rack[i+2] = drawnCard;
        //             break;
        //         }
        //     } 
            
            
        // }
        for (int i = 0; i < rack.length - 2; i++) {
            int[] groupOfThree = {rack[i], rack[i + 1], rack[i + 2]};
            if (!(rack[i] < rack[i + 1] && rack[i + 1] < rack[i + 2])) {
                if (drawnCard > groupOfThree[0] && drawnCard < groupOfThree[2]) {
                    rack[i + 1] = drawnCard;
                    break;
                } else if (drawnCard < groupOfThree[1] && groupOfThree[1] < groupOfThree[2]) {
                    rack[i] = drawnCard;
                    break;
                } 
                if (groupOfThree[0] < groupOfThree[1] && groupOfThree[1] < drawnCard) {
                    rack[i + 2] = drawnCard;
                    break;
                }
            } 
        }
        
         
        
        return rack; 
    }
}