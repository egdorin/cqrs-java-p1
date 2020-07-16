package com.p1.tab_aggregate.commands;

import com.cqrs.base.Command;
import com.p1.tab_aggregate.events.OrderedItem;

import java.util.List;

public class PlaceOrder implements Command {
    public final String Id;
    public final List<OrderedItem> Items;

    public PlaceOrder(String id, List<OrderedItem> items) {
        Id = id;
        Items = items;
    }

    @Override
    public String getAggregateId() {
        return Id;
    }
}
