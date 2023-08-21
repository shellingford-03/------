
package view;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private final String title ;
    private final ArrayList<String> options = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in) ;
    
    public Menu(String title){
        this.title = title ;
    }
    
    public void addOption(String newOption){
        options.add(newOption) ;
    }
    
    public void printMenu(){
        System.out.println(title);
        for(String s : options){
            System.out.println(options.indexOf(s)+1 + ". " + s);
        }
    }
    
    public int getChoice(){
        while(true){
            try{
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(sc.nextLine()) ;
                if (choice < 1 || choice > options.size()) throw new Exception() ;
                return choice ;
            }catch(NumberFormatException e){
                System.out.println("Option must be an integer number!");
            }catch(Exception e){
                System.out.println("Option must be in [1,"+options.size()+"]");
            }
        }
    }
    
    public static boolean getYesOrNo(String title){
        Menu sub = new Menu(title); 
        sub.addOption("Yes") ;
        sub.addOption("No") ;
        sub.printMenu();
        return sub.getChoice() == 1;
    }
    
    @Override
    public String toString(){
        String msg = String.format("[Menu] : %s", title) ;
        return msg ;
    }
}
