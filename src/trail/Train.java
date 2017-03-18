/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trail;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author danx_
 */
public class Train extends Thread {

    //Attributes
    //Track dimesions 600, 460; track starts in position 50, 50
    //Train dimensions 100, 100; 
    private int x;
    private final int initX;
    private int y;
    private final int initY;
    private final JLabel train;
    private final byte id;
    private final Semaphore s;
    private static JTextArea msg;
    protected boolean a;

    public Train(JLabel t, byte id, Semaphore s, JTextArea msg) {
        this.train = t;
        this.x = t.getX();
        this.initX = t.getX();
        this.y = t.getY();
        this.initY = t.getY();
        this.id = id;
        this.s = s;
        this.msg = msg;
        this.a = true;
    }

    @Override
    public void run() {
        msg.append("Train #" + (this.id == 1 ? "1" : (this.id == 2) ? "2" : "3") + "starts its route\n");
        while (true) {

            //1st train
            switch (this.id) {
                case 1:
                    //Train goes right
                    while (this.x < initX + 525 && this.y == initY) {
                        try {
                            Thread.sleep(2);
                            this.x++;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }   //Train 1 goes up
                    traverseTunnel();
                    /*
                    //Train goes up
                    if (this.x == initX + 525 && this.y >= initY - 410) {
                    this.y--;
                    train.setLocation(this.x, this.y);
                    }*/
                    //Train goes left
                    while (this.x > initX - 25 && this.y == initY - 410) {
                        try {
                            Thread.sleep(2);
                            this.x--;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }   //Train goes down and completes a loop
                    while (this.x == initX - 25 && this.y < initY) {
                        try {
                            Thread.sleep(2);
                            this.y++;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }   fuelRecharge();
                    break;
                case 2:
                    //2nd train
                    
                    //Train goes left
                    while (this.x > initX - 525 && this.y == initY) {
                        try {
                            Thread.sleep(2);
                            this.x--;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }   traverseTunnel();
                    /*
                    //Train goes up
                    if (this.x == initX - 525 && this.y >= initY - 410) {
                    this.y--;
                    train.setLocation(this.x, this.y);
                    }*/
                    //Train goes right
                    while (this.x < initX + 25 && this.y == initY - 410) {
                        try {
                            Thread.sleep(2);
                            this.x++;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }   //Train goes down and completes a loop
                    while (this.x == initX + 25 && this.y < initY) {
                        try {
                            Thread.sleep(2);
                            this.y++;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }   fuelRecharge();
                    break;
                case 3:
                    //3rd train
                    //Train goes a little way upward towards the tunnel
                    while (this.y > 410) {
                        try {
                            Thread.sleep(2);
                            this.y--;
                            train.setLocation(this.initX, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }   //Train goes through tunnel
                    traverseTunnel();
                    while (this.y > - 500) {
                        try {
                            Thread.sleep(2);
                            this.y--;
                            train.setLocation(this.initX, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }   train.setLocation(this.initX, this.initY + 500);
                    this.x = this.initX;
                    this.y = this.initY + 500;
                    break;
            }

        }

    }

    private synchronized void traverseTunnel() {
        try {
            s.acquire();

            msg.append("Train #" + (this.id == 1 ? "1" : (this.id == 2) ? "2" : "3") + " enters tunnel\n");

            //Train goes up
            while (this.y > 0) {
                try {
                    Thread.sleep(4);
                    this.y--;
                    train.setLocation(this.x, this.y);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            msg.append("Train #" + (this.id == 1 ? "1" : (this.id == 2) ? "2" : "3") + " exits tunnel\n");
            s.release();
        }

    }

    public void reset() {
        //Set to false so the run method can stop
        //this.a = false;
        //Set the train label to the initial location
        train.setLocation(this.initX, this.initY);
        msg.setText("");
    }

    private synchronized void fuelRecharge() {
        //Create random varable for wait time
        Random r = new Random();
        int low = 100;
        int high = 501;
        int result = r.nextInt(high - low) + low;

        msg.append("Train #" + (this.id == 1 ? "1" : (this.id == 2) ? "2" : "3") + " recharges fuel at station:" + result + " ms\n");

        try {
            Thread.sleep(result);
        } catch (InterruptedException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
