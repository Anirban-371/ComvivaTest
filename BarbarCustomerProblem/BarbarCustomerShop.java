package com.anirban.program;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*Barbar customer working using blocking queue*/
public class BarbarCustomerShop {

    public static void main(String args[]){

	     BlockingQueue<Integer> seats = new LinkedBlockingQueue<Integer>();
	
	     Thread customer1 = new Thread(new Customer(seats,1));
	     Thread customer2 = new Thread(new Customer(seats,2));
	     
	     Thread barbar = new Thread(new Barbar(seats));
	
	     barbar.start();
	     customer1.start();
	     customer2.start();
    }

}

class Customer implements Runnable {

    private final BlockingQueue<Integer> seats;
    private int customerNo;

    public Customer(BlockingQueue<Integer> seats,int customerNo) {
        this.customerNo = customerNo;
        this.seats = seats;
    }
	
    /*Customers to come in when seats are available and notify the barbar to start working*/
    @Override
    public void run() {
        try {
            int number = customerNo;
            System.out.println("Customer No:"+ customerNo+ "came in.");
            seats.put(number);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}

class Barbar implements Runnable{

    private final BlockingQueue<Integer> seats;
    public Barbar (BlockingQueue<Integer> seats) {
        this.seats = seats;
    }

    /*Barbar works on the customers,as customers have occupied seats*/
    @Override
    public void run() {
        while(true){
            try {
                int num = seats.take();
                System.out.println("Customer Number worked upon: "+ num+ " by barbar");
            } catch (Exception err) {
               err.printStackTrace();
            }
        }
    }   
}
