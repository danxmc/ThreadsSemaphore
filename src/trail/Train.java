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
    private boolean id;
    private Semaphore s;

    public Train(JLabel t, boolean id, Semaphore s) {
        this.train = t;
        this.x = t.getX();
        this.initX = t.getX();
        this.y = t.getY();
        this.initY = t.getY();
        this.id = id;
        this.s = s;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
            }

            //1st train
            if (id) {

                //Train goes right
                if (this.x <= initX + 525 && this.y == initY) {
                    this.x++;
                    train.setLocation(this.x, this.y);
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
                if (this.x >= initX - 25 && this.y == initY - 410) {
                    this.x--;
                    train.setLocation(this.x, this.y);
                }

                //Train goes down and completes a loop
                if (this.x == initX - 25 && this.y <= initY) {
                    this.y++;
                    train.setLocation(this.x, this.y);
                }

            } else {//2nd train

                //Train goes left
                if (this.x >= initX - 525 && this.y == initY) {
                    this.x--;
                    train.setLocation(this.x, this.y);
                }

                traverseTunnel(id);
                
                /*
                //Train goes up
                if (this.x == initX - 525 && this.y >= initY - 410) {
                    this.y--;
                    train.setLocation(this.x, this.y);
                }*/

                //Train goes right
                if (this.x <= initX + 25 && this.y == initY - 410) {
                    this.x++;
                    train.setLocation(this.x, this.y);
                }

                //Train goes down and completes a loop
                if (this.x == initX + 25 && this.y <= initY) {
                    this.y++;
                    train.setLocation(this.x, this.y);
                }
            }

        }

    }

    private synchronized void traverseTunnel(boolean id) {
        try {
            s.acquire();
            if (id) {
                //Train 1 goes up
                if (this.x == initX + 525 && this.y >= initY - 410) {
                    this.y--;
                    train.setLocation(this.x, this.y);
                }
            } else//Train 2 goes up
            {
                if (this.x == initX - 525 && this.y >= initY - 410) {
                    this.y--;
                    train.setLocation(this.x, this.y);
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            s.release();
        }

    }

}
