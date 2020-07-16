package com.p1.tab_aggregate.events;


import com.cqrs.base.Event;

public class OrderedItem implements Event {

    public int MenuNumber;
    public String Description;
    public boolean IsDrink;
    public double Price;

    public static OrderedItem food(int menuNumber,String description, double price){
        OrderedItem item = new OrderedItem();
        item.Price = price;
        item.MenuNumber = menuNumber;
        item.Description = description;
        item.IsDrink = false;
        return  item;
    }
    public static OrderedItem drink(int menuNumber,String description, double price){
        OrderedItem item = new OrderedItem();
        item.Price = price;
        item.MenuNumber = menuNumber;
        item.Description = description;
        item.IsDrink = true;
        return  item;
    }
}
