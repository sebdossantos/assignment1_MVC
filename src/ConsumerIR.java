/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 11/10/15
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
//package com.journaldev.concurrency;

import java.util.concurrent.BlockingQueue;

public class ConsumerIR implements Runnable{

    private BlockingQueue<byte[]> queue;
    private Controller _ctrl;
    private Boolean stateFrame;

    public ConsumerIR(BlockingQueue<byte[]> q, Controller controller){
        this._ctrl = controller;
        this.stateFrame = true;
        this.queue = q;
    }

    @Override
    public synchronized void run() {
        try{
            while(queue.isEmpty() && stateFrame){
                byte[] frame = queue.take();
                this.handleFrame(frame);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * handleframe
     * Permet de renvoyer la commande en fonction des donnée des capteur IR
     * @param data
     * @return
     */
    private String handleFrame(byte[] data){
        int distanceL=0, distanceR =0;
        String cmd;

        System.out.print("Consumed : ");
        for (int i=0; i<21 ; i++)
            System.out.print(data[i] + " ");
        System.out.println();

        if( data[1] == frame.SENSOR_LIR.ID){
            distanceL = (int)data[2];
            distanceL <<= 8;
            distanceL |= data[3];
        }
        else{
            distanceR = (int)data[2];
            distanceR<<= 8;
            distanceR |= data[3];
        }

        cmd = _ctrl.getModel().get_track().get_irTrack().rightOrLeftIR(distanceR,distanceL);
        //_ctrl.getModel().applyOnGUI("LIR", frame);
        //_ctrl.getModel().applyOnGUI("RIR", frame);
        return cmd;
    }
}
