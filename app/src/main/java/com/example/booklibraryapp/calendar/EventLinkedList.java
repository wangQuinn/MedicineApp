package com.example.booklibraryapp.calendar;

public class EventLinkedList {
    private Event head;
    private Event tail;
    private int size;

    public EventLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Event first() {
        return head;
    }

    public Event last() {
        return tail;
    }

    public void insertSorted(Event newEvent) {
        if (head == null) { // First insertion
            head = newEvent;
            tail = newEvent;
        } else if (newEvent.getDate().isBefore(head.getDate())) {
            insertHead(newEvent);
        } else if (newEvent.getDate().isAfter(tail.getDate())) {
            insertTail(newEvent);
        } else {
            // Find the correct position
            Event current = head;
            while (current != null && newEvent.getDate().isAfter(current.getDate())) {
                current = current.next;
            }
            insertBefore(newEvent, current);
        }
        size++;
    }

    public void insertHead(Event newEvent) {
        if (isEmpty()) {
            head = tail = newEvent;
        } else {
            newEvent.next = head;
            head.prev = newEvent;
            head = newEvent;
        }
        size++;
    }

    public void insertTail(Event newEvent) {
        if (isEmpty()) {
            head = tail = newEvent;
        } else {
            tail.next = newEvent;
            newEvent.prev = tail;
            tail = newEvent;
        }
        size++;
    }

    public void insertBefore(Event newEvent, Event referenceEvent) {
        if (referenceEvent == head) {
            insertHead(newEvent);
        } else {
            newEvent.next = referenceEvent;
            newEvent.prev = referenceEvent.prev;
            referenceEvent.prev.next = newEvent;
            referenceEvent.prev = newEvent;
            size++;
        }
    }

    public void insertAfter(Event newEvent, Event referenceEvent) {
        if (referenceEvent == tail) {
            insertTail(newEvent);
        } else {
            newEvent.prev = referenceEvent;
            newEvent.next = referenceEvent.next;
            referenceEvent.next.prev = newEvent;
            referenceEvent.next = newEvent;
            size++;
        }
    }

    public void remove(Event event) {
        if (event == null) return;
        if (event == head) {
            head = head.next;
            if (head != null) head.prev = null;
        } else if (event == tail) {
            tail = tail.prev;
            if (tail != null) tail.next = null;
        } else {
            event.prev.next = event.next;
            event.next.prev = event.prev;
        }
        size--;
    }

    public void printList() {
        Event current = head;
        while (current != null) {
            System.out.println(current.getDate() + " - " + current.getName());
            current = current.next;
        }
    }
}
