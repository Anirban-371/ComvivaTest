package com.anirban.program;

import java.util.LinkedList;

public class BarbarCustomerShopSimpleThread {
	static Thread t1 ;
	static Thread t2;
	static Thread t3;
	static Thread t4;
	static Thread t5;
	
	static LinkedList<Integer> seats = new LinkedList<>();
	static Shop shop= new Shop();
    public static void main(String[] args)throws InterruptedException{
        
        // Create producer thread
        t1 = new Thread(new Customer(seats, "1"));
        t2 = new Thread(new Customer(seats, "2"));
        t3 = new Thread(new Customer(seats, "3"));
        t4 = new Thread(new Customer(seats, "4"));
        
        // Create consumer thread
        t5 = new Thread(new Barbar(seats));
  
        // Start both threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        
  
        // one thread finishes before other thread
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    }
    public static class Barbar implements Runnable{
    	
    	LinkedList<Integer> seats = new LinkedList<>();
    	String threadNumber="";
    	Barbar(LinkedList<Integer> seats){
    		this.seats = seats;
    	}
		@Override
		public void run() {
			shop.barberCut(seats);
		}
    	
    }
    public static class Customer implements Runnable{
    	
    	LinkedList<Integer> seats = new LinkedList<>();
    	String threadNumber="";
    	
    	Customer(LinkedList<Integer> seats, String threadNumber){
    		this.seats = seats;
    		this.threadNumber = threadNumber;
    	}
		@Override
		public void run() {
			shop.customerSit(seats,threadNumber);
		}
    	
    }
    // This Shop class has a barbar and seats for customers (allocates customers to seats and barbar works on them one by one).
    public static class Shop{
    	
        // Number of seats are 5.	        
	    int capacity = 5;

		public void customerSit(LinkedList<Integer> list , String threadNumber) { 
			
	        while (true) {
	            synchronized (this)
	            {
	                // customer waits while seats are filled to capacity
	                while (list.size() == capacity) {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	                }
	                System.out.println("Seats allocated to Customer No: "+ threadNumber);
	
	                // Allocating customer to a seat
	                list.add(Integer.parseInt(threadNumber));
	
	                // notifies the barbar now it can start working
	                notify();
	            }
	        }
		}
		
        // Function called by barbar
        public void barberCut(LinkedList<Integer> list){
            while (true) {
                synchronized (this)
                {
                    // barbar thread waits/sleeps while seats are empty
                    while (list.size() == 0) {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
                    }
                    int val = list.removeFirst();
  
                    System.out.println("Customer worked upon :"+ val);
  
                    // Let customers come in
                    notify();
                }
            }
            
        }
    }
}
