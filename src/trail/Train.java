/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trail;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

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
    private JLabel train;
    private final boolean id;
    private Semaphore s;
    //protected boolean a;

    public Train(JLabel t, boolean id, Semaphore s) {
        this.train = t;
        this.x = t.getX();
        this.initX = t.getX();
        this.y = t.getY();
        this.initY = t.getY();
        this.id = id;
        this.s = s;
        //this.a = true;
    }
    
    @Override
    public void run() {
        while (true) {

            //1st train
            if (id) {

                //Train goes right
                while (this.x < initX + 525 && this.y == initY) {
                    try {
                        Thread.sleep(2);
                        this.x++;
                        train.setLocation(this.x, this.y);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                //Train 1 goes up
                traverseTunnel(id);

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

                }

                //Train goes down and completes a loop
                while (this.x == initX - 25 && this.y < initY) {
                    try {
                        Thread.sleep(2);
                        this.y++;
                        train.setLocation(this.x, this.y);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            } else {//2nd train

                //Train goes left
                while (this.x > initX - 525 && this.y == initY) {
                    try {
                        Thread.sleep(2);
                    this.x--;
                    train.setLocation(this.x, this.y);    
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                traverseTunnel(id);

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
                }

                //Train goes down and completes a loop
                while (this.x == initX + 25 && this.y < initY) {
                    try {
                        Thread.sleep(2);
                    this.y++;
                    train.setLocation(this.x, this.y);   
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

    }

    private synchronized void traverseTunnel(boolean id) {
        try {
            s.acquire();
            
            if (id) {
                //Train 1 goes up
                while (this.x == initX + 525 && this.y > initY - 410) {
                    try {
                        Thread.sleep(4);
                        this.y--;
                        train.setLocation(this.x, this.y);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                //Train 2 goes up
                while (this.x == initX - 525 && this.y > initY - 410) {
                    try {
                        Thread.sleep(4);
                        this.y--;
                        train.setLocation(this.x, this.y);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            s.release();
        }

    }

    public void reset() {
        //Set to false so the run method can stop
        //this.a = false;
        //Set the train label to the initial location
        train.setLocation(this.initX, this.initY);
    }

}
