package org.example.ticketingapplication.model;




import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    public Queue<Ticket> getTickets() {
        return tickets;
    }

    public int getTotalTicketsSelling() {
        return totalTicketsSelling;
    }

    public void setTotalTicketsSelling(int totalTicketsSelling) {
        this.totalTicketsSelling = totalTicketsSelling;
    }



//    Adding Tickets to the pool
    public void addTicket(Ticket ticket, String vendorName) {
        lock.lock();
        try{
            while (tickets.size() >= maxCapacity) {
                System.out.println("Ticket pool is full, Waiting for customers to buy...");
                notFull.await(); //Holds the thread until customers buy few tickets
            }

            tickets.offer(ticket);

            if (ticket.getEvent() != null && ticket.getEvent().getVendor() != null) {
                ticket.getEvent().setTicketAvailable(ticket.getEvent().getTicketAvailable() - 1);
                ticket.getEvent().getVendor().setTotalTicketsToBeSold(ticket.getEvent().getVendor().getTotalTicketsToBeSold() - 1);
            }

            System.out.println(ticket.getTicketId() + " added to the pool by " + vendorName);
            notEmpty.signalAll(); //Notify threads which are waiting to buy tickets

        }catch (InterruptedException e){
            System.out.println("Vendor thread was interrupted.");
        } finally {
            lock.unlock();
        }

    }




    //Removing tickets from the pool
    public void buyTicket(Customer customer){
        lock.lock();
        try {
            while (tickets.isEmpty()) {
                System.out.println("Not enough tickets to buy, waiting for vendors to add tickets");
                notEmpty.await(); //Holds the threads to buy tickets
            }

            Ticket boughtTicket = tickets.poll();
            System.out.println(boughtTicket.getTicketId() + " bought ticket by " + customer.getCustomerName());

            boughtTicket.setCustomer(customer);
            boughtTicket.getEvent().getVendor().setTotalProfit(boughtTicket.getPrice());

            notFull.signalAll(); //Notifies the threads which are waiting to add tickets

        } catch (InterruptedException e) {
            System.out.println("Vendor thread was interrupted.");
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
