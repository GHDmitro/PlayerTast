package worker;

import exceptions.IllegalNameException;
import model.Player;

import java.util.concurrent.Semaphore;

/**
 * Created by Dmytro Tymoshenko on 15.05.17.
 * Semaphore : mechanism which allows to control synchronization between 2 threads
 */
public class Sendler {

    public static Semaphore semaphore1 = new Semaphore(0);
    public static Semaphore semaphore2 = new Semaphore(0);

    public static void main(String[] args) throws IllegalNameException, InterruptedException {


        System.out.println("Main thread has started!");

        Player player1 = new Player("Player1", 1);
        Player player2 = new Player("Player2", 2);

        player1.setPlayer2(player2);
        player2.setPlayer2(player1);

        player1.start();
        player2.start();

        try {
            player1.join();
            player2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread has finished!");
    }
}
