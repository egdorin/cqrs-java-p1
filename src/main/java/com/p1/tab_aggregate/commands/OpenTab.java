package com.p1.tab_aggregate.commands;

import com.cqrs.base.Command;
import com.cqrs.util.Guid;

public class OpenTab implements Command {
    public final String Id;
    public final int TableNumber;
    public final String Waiter;

    public OpenTab(String id, int tableNumber, String waiter) {
        Id = id;
        TableNumber = tableNumber;
        Waiter = waiter;
    }

    @Override
    public String getAggregateId() {
        return Id.toString();
    }
}
