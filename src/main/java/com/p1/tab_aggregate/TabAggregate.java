package com.p1.tab_aggregate;

import com.cqrs.annotations.CommandHandler;
import com.cqrs.base.Aggregate;
import com.p1.tab_aggregate.commands.OpenTab;
import com.p1.tab_aggregate.commands.PlaceOrder;
import com.p1.tab_aggregate.events.DrinksOrdered;
import com.p1.tab_aggregate.events.OrderedItem;
import com.p1.tab_aggregate.events.TabOpened;
import com.p1.tab_aggregate.exceptions.TabNotOpen;

import java.util.List;
import java.util.stream.Collectors;

public class TabAggregate extends Aggregate {
    private boolean open = false;

    @CommandHandler
    void handle(OpenTab c){
        emit(new TabOpened(c.Id,c.TableNumber,c.Waiter));
    }
    @CommandHandler
    void handlePlaceOrder(PlaceOrder c) throws TabNotOpen {
        if (!open){
            throw new TabNotOpen("Error");
        }
        List<OrderedItem> drinks = c.Items.stream().filter(orderedItem -> orderedItem.IsDrink).collect(Collectors.toList());
        if(!drinks.isEmpty()){
            emit(new DrinksOrdered(c.Id,drinks));
        }

    }


    public void apply(TabOpened e)
    {
        open = true;
    }

}
