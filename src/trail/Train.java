/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author danx_
 */
public class Train extends Thread implements ActionListener {

    //Attributes
    //Track dimesions 600, 460; track starts in position 50, 50
    //Train dimensions 100, 100; 
    private int x;
    private int y;
    private final JLabel train;
    private final byte id;
    private final Semaphore s;
    private static JTextArea msg;
    private static JButton button;
    private final CyclicBarrier barrier;
    protected boolean btnPress;
    //Initial coordinates (x, y) of the trains in order train1, train2, train3
    private final int[][] initCoord = {{50, 410}, {1150, 410}, {600, 500}};

    /*
    public Train(JLabel t, byte id, Semaphore s, JTextArea msg) {
        this.train = t;
        this.x = t.getX();
        this.y = t.getY();
        this.id = id;
        this.s = s;
        this.msg = msg;
    }*/

    Train(JLabel t, byte id, Semaphore s, JTextArea msg, JButton buttonStart, CyclicBarrier barrier) {
        this.train = t;
        this.x = t.getX();
        this.y = t.getY();
        this.id = id;
        this.s = s;
        Train.msg = msg;
        Train.button = buttonStart;
        this.btnPress = true;
        this.barrier = barrier;
        
        button.addActionListener(this);

        /*button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evnt) {
                    
                }
            });*/
    }

    @Override
    public void run() {
        msg.append("Train #" + (this.id == 1 ? "1" : (this.id == 2) ? "2" : "3") + " starts its route\n");
        while (btnPress) {

            //1st train
            switch (this.id) {
                case 1:
                    //Train goes right
                    while (this.x < this.initCoord[this.id - 1][0] + 525 && this.y == this.initCoord[this.id - 1][1]) {
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
                    while (this.x > this.initCoord[this.id - 1][0] - 25 && this.y == this.initCoord[this.id - 1][1] - 410) {
                        try {
                            Thread.sleep(2);
                            this.x--;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }   //Train goes down and completes a loop
                    while (this.x == this.initCoord[this.id - 1][0] - 25 && this.y < this.initCoord[this.id - 1][1]) {
                        try {
                            Thread.sleep(2);
                            this.y++;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    fuelRecharge();
                    break;
                    
                case 2:
                    //2nd train

                    //Train goes left
                    while (this.x > this.initCoord[this.id - 1][0] - 525 && this.y == this.initCoord[this.id - 1][1]) {
                        try {
                            Thread.sleep(2);
                            this.x--;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    traverseTunnel();
                    /*
                    //Train goes up
                    if (this.x == initX - 525 && this.y >= initY - 410) {
                    this.y--;
                    train.setLocation(this.x, this.y);
                    }*/
                    //Train goes right
                    while (this.x < this.initCoord[this.id - 1][0] + 25 && this.y == this.initCoord[this.id - 1][1] - 410) {
                        try {
                            Thread.sleep(2);
                            this.x++;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }   //Train goes down and completes a loop
                    while (this.x == this.initCoord[this.id - 1][0] + 25 && this.y < this.initCoord[this.id - 1][1]) {
                        try {
                            Thread.sleep(2);
                            this.y++;
                            train.setLocation(this.x, this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    fuelRecharge();
                    break;
                    
                case 3:
                    //3rd train
                    //Train goes a little way upward towards the tunnel
                    while (this.y > 410) {
                        try {
                            Thread.sleep(2);
                            this.y--;
                            train.setLocation(this.initCoord[this.id - 1][0], this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }   //Train goes through tunnel
                    traverseTunnel();
                    while (this.y > - 500) {
                        try {
                            Thread.sleep(2);
                            this.y--;
                            train.setLocation(this.initCoord[this.id - 1][0], this.y);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    train.setLocation(this.initCoord[this.id - 1][0], this.initCoord[this.id - 1][1] + 500);
                    this.x = this.initCoord[this.id - 1][0];
                    this.y = this.initCoord[this.id - 1][1] + 500;
                    break;
                    
            }

        }
        this.x = this.initCoord[id - 1][0];
        this.y = this.initCoord[id - 1][1];
        train.setLocation(this.initCoord[id - 1][0], this.initCoord[id - 1][1]);
        msg.setText("");
        
        try {
            barrier.await(10, TimeUnit.SECONDS);
            System.out.println(id + " waiting");
        } catch (InterruptedException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Critical section
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

    //Waits a random between 100 and 500 ms at the initial coordinates
    private synchronized void fuelRecharge() {
        //Create random varable for wait time
        Random r = new Random();
        int low = 100;
        int high = 501;
        int result = r.nextInt(high - low) + low;

        msg.append("Train #" + (this.id == 1 ? "1" : (this.id == 2) ? "2" : "3") + " recharges fuel at station: " + result + " ms\n");

        try {
            Thread.sleep(result);
        } catch (InterruptedException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (button == e.getSource()) {
            this.btnPress = false;
        }
    }

}
