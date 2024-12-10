package org.example.ticketingapplication.model;




import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Component
public class TicketPool {
    private final ConcurrentLinkedQueue<Ticket> tickets = new ConcurrentLinkedQueue<>();
    private int maxCapacity;

    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    private int totalTicketsSelling;

    public TicketPool (int maxCapacity, int totalTicketsSelling) {
        this.maxCapacity = maxCapacity;
        this.totalTicketsSelling = totalTicketsSelling;
    }

    public TicketPool(){

    }


    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public ConcurrentLinkedQueue<Ticket> getTickets() {
        return tickets;
    }

    public int getTotalTicketsSelling() {
        return totalTicketsSelling;
    }

    public void setTotalTicketsSelling(int totalTicketsSelling) {
        this.totalTicketsSelling = totalTicketsSelling;
    }



    //Adding Tickets to the pool
    public void addTicket(Ticket ticket, String vendorName) throws InterruptedException {
        lock.lock();
        try{
            while (tickets.size() >= maxCapacity) {
                System.out.println("Ticket pool is full, Waiting for customers to buy...");
                notFull.await(); //Holds the thread until customers buy few tickets
            }

            tickets.offer(ticket);
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
