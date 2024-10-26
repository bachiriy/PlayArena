package com.playarena.app.view;

import java.util.Scanner;

import com.playarena.app.util.InputValidator;
import org.springframework.context.ApplicationContext;

public class Entry {
    private ApplicationContext context;
    private InputValidator input;

     public Entry(ApplicationContext context){
         this.input = new InputValidator();
         this.context = context;
     }

     public void display(){
         System.out.println(menu());
         int choice = input.getNum("Enter your choice: ");
         switch (choice){
             case 1:
                 PlayerView.display(context);
                 display();
             case 2:
                 TeamView.display(context);
                 display();
             case 3:
                 GameView.display(context);
                 display();
             case 4:
                 TournamentView.display(context);
                 display();
             case 5:
                 break;
             default:
                 System.out.println("Invalid choice");
                 display();
                 break;
         }
     }
    

     private String menu(){
         return 
         "\t\t-- Principal Menu --\n"+
                 "\t1. Manage Players\n"+
                 "\t2. Manage Teams\n"+
                 "\t3. Manage Games\n"+
                 "\t4. Manage Tournament\n" +
                 "\t5. Exit\n";
     }
}
