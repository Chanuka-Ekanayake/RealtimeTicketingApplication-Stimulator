package org.example.ticketingapplication.model;




import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TicketPool {
    private final Queue<Ticket> tickets = new LinkedList<>();
    private final int maxCapacity;

    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public TicketPool (int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
    }

    //Adding Tickets to the pool
    public void addTicket(Ticket ticket, String vendorName) throws InterruptedException {
        lock.lock();
        try{
            while (tickets.size() >= maxCapacity) {
                System.out.println("Ticket pool is full, Waiting for customers to buy...");
                notFull.await(); //Holds the thread until customers buy few tickets
            }

            tickets.add(ticket);
            System.out.println(ticket.getTicketId() + " added to the pool by " + vendorName);
            notEmpty.signalAll(); //Notify threads which are waiting to buy tickets

        } finally {
            lock.unlock();
        }

    }


    //Removing tickets from the pool
    public void buyTicket(Ticket ticket, String CustomerName) throws InterruptedException {
        lock.lock();
        try {
            while (tickets.isEmpty()) {
                System.out.println("Not enough tickets to buy, waiting for vendors to add tickets");
                notEmpty.await(); //Holds the threads to buy tickets
            }

            tickets.poll();
            System.out.println(ticket.getTicketId() + " bought ticket by " + CustomerName);
            notFull.signalAll(); //Notifies the threads which are waiting to add tickets

        } finally {
            lock.unlock();
        }
    }

    //removing expired tickets from the pool
    public void removeExpiredTickets() {
        lock.lock();
        LocalDateTime now = LocalDateTime.now();
        try{
            for (Ticket ticket : tickets) {
                if(ticket.getExpireDateTime().isBefore(now)){
                    tickets.remove(ticket);
                    System.out.println(ticket.getTicketId() +"was expired, removed from the pool");
                    notFull.signalAll(); //Notify the thread waiting to add tickets
                }
            }

        }finally {
            lock.unlock();
        }
    }

    public int getTicketCount() {
       return tickets.size();
    }



}
