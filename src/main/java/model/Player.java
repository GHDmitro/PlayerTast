package model;

import exceptions.IllegalNameException;
import worker.Sendler;

/**
 * Created by Dmytro Tymoshenko on 15.05.17.
 *
 */
public class Player extends Thread {

    /**
     * state: field which show is the player ready
     */
    private boolean state;
    /**
     * number: number of player
     */
    private int number;

    /**
     * message: field in which player set his message when send one to another player
     */
    private StringBuilder message;

    /**
     * msgStr: msgStr of current player, uses for setting into message like text of message
     */
    private String msgStr;

    /**
     * counter: filed increments on each iteration for checking to finishing
     */
    private int counter;

    /**
     * player2: href to another player for realization client-server model vie threads
     */
    private Player player2;

    /**
     * @param player2 set another player
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * @param msgStr : msgStr of current player
     * @param number : number of current player
     * @throws IllegalNameException
     */
    public Player(String msgStr, int number) {
        message = new StringBuilder();
        this.msgStr = msgStr;
        this.number = number;
    }


    /**
     * @return state of player, show is player ready for working
     */
    public boolean isState() {
        return state;
    }

    /**
     * @param msgStr set msg to the msg field, it is the message which sends to another player
     */
    public void setMsgStr(String msgStr) {
        this.msgStr = msgStr;
    }

    @Override
    public void run() {
        if (player2.msgStr.equals(msgStr) || player2.number == number) {
            try {
                throw new IllegalNameException("Unexpected msgStr");
            } catch (IllegalNameException e) {
                e.printStackTrace();
            }
        }

        state = true;
        System.out.println("Thread "+number+" is ready!");
        if (number == 1) {
            Sendler.semaphore1.release();

            try {
                Sendler.semaphore2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (number == 2){
            Sendler.semaphore2.release();
            try {
                Sendler.semaphore1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        String bb = new String();
        while (counter < 11) {

            if (!state){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                if (number == 1) {

                    counter++;
                    sendMessage(bb);
                    Sendler.semaphore1.release();
                    Sendler.semaphore2.acquire();
                    String s = player2.getMessage();
                    bb = s;
                    sleep(500);

                } else if (number == 2) {

                    Sendler.semaphore1.acquire();
                    String s = player2.getMessage();
                    Sendler.semaphore2.release();
                    sendMessage(s);
                    counter++;
                    sleep(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread "+number+" finished!");
    }

    /**
     * @param ss : last part of string
     * @throws InterruptedException
     */
    public synchronized void sendMessage(String ss) throws InterruptedException {
        message.append(ss).append(msgStr);
        System.out.println(number + " -> " + message.toString());
    }

    /**
     * Method remove all message and put it to the buffer
     * and then return buffer from the method
     * @return all message from current player
     * @throws InterruptedException
     */
    public synchronized String getMessage() throws InterruptedException {

        String buff = message.toString();
        message.delete(0, message.length());
        return buff;
    }


}














