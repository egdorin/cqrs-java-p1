package com.p1.tab_aggregate.events;

import com.cqrs.base.Event;

import java.util.List;

public class DrinksOrdered implements Event {
    public final String Id;
    public final List<OrderedItem> Items;

    public DrinksOrdered(String id, List<OrderedItem> items) {
        Id = id;
        Items = items;
    }
}
