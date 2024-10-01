package uz.pdp;


import java.io.IOException;

public class Main {
    static {
        try {
            DB.generate();
        } catch (IOException e) {
            System.out.println("endi");
        } catch (InterruptedException e) {
            System.out.println("Yanayam endi");
        }
    }
    public static void main(String[] args) {
  BotController botController=new BotController();
  botController.start();
    }
}