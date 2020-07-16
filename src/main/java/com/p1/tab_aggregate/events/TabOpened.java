package com.p1.tab_aggregate.events;

import com.cqrs.base.Event;
import com.cqrs.util.Guid;

public class TabOpened implements Event {
    public final String Id;
    public final int TableNumber;
    public final String Waiter;

    public TabOpened(String id, int tableNumber, String waiter) {
        Id = id;
        TableNumber = tableNumber;
        Waiter = waiter;
    }
}
