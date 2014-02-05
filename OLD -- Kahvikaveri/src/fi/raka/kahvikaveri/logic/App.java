package fi.raka.kahvikaveri.logic;

import java.util.Scanner;

public class App {
    
    private static CListView list;
    
    public static void main( String[] args ) {
        list = new CListView();
        mainMenu();
    }
    
    private static void mainMenu() {
        Scanner s = new Scanner(System.in);
        int command = -1;

        do {
            System.out.println("\nKomennot:");
            System.out.println("0: Poistu");
            System.out.println("1: Lisää resepti");
            System.out.println("2: Näytä reseptilista");
            
            command = s.nextInt();
            switch(command) {
                case 1: addReceipt(); break;
                case 2: listReceipts(); break;
            }
        } while(command != 0);
        
        System.out.println("Heippa!");
    }
    
    private static void addReceipt() {
        Scanner s = new Scanner(System.in);
        CoffeeReceipt receipt = new CoffeeReceipt();
        
        System.out.print("\nNimi: ");
        receipt.setTitle( s.nextLine() );
        System.out.print("Vettä (dl): ");
        receipt.setWaterAmount( s.nextDouble() );
        System.out.print("Veden lämpötila (C): ");
        receipt.setWaterTemperature(s.nextDouble() );
        System.out.print("Kahvia (g): ");
        receipt.setCoffeeAmount(s.nextDouble() );
                
        list.addItem( receipt );
    }
    
    private static void listReceipts() {
        System.out.println("Reseptit:");
        System.out.print( list.toString() );
    }
}
