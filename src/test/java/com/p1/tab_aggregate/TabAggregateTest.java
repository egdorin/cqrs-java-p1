package com.p1.tab_aggregate;

import com.cqrs.commands.CommandSubscriberByMap;
import com.cqrs.testing.BddAggregateTestHelper;
import com.p1.tab_aggregate.commands.OpenTab;
import com.p1.tab_aggregate.commands.PlaceOrder;
import com.p1.tab_aggregate.events.DrinksOrdered;
import com.p1.tab_aggregate.events.FoodOrdered;
import com.p1.tab_aggregate.events.OrderedItem;
import com.p1.tab_aggregate.events.TabOpened;
import com.p1.tab_aggregate.exceptions.TabNotOpen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class TabAggregateTest {

    private String testId;
    private int testTable;
    private String testWaiter;
    public List<OrderedItem> drinkAndFood = new ArrayList<OrderedItem>();
    public List<OrderedItem> foodItems = new ArrayList<OrderedItem>();
    public List<OrderedItem> drinkItems = new ArrayList<OrderedItem>();
    private BddAggregateTestHelper helper;

    @BeforeEach
    public void Setup() {
        testId = "3";
        testTable = 42;
        testWaiter = "Derek";
        OrderedItem foodItem = OrderedItem.food(3, "gratar", 5.6);
        OrderedItem drinkItem = OrderedItem.drink(4, "bere", 5.6);
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.Description = "test";
        orderedItem.IsDrink = true;
        orderedItem.MenuNumber = 2;
        orderedItem.Price = 2.0;
        drinkAndFood.add(orderedItem);
        foodItems.add(foodItem);
        drinkItems.add(drinkItem);

        helper = new BddAggregateTestHelper(new CommandSubscriberByMap());
    }

    @Test
    void CanOpenANewTab() throws Exception {

        helper.onAggregate(new TabAggregate())
                .when(new OpenTab(testId, testTable, testWaiter))
                .then(new TabOpened(testId, testTable, testWaiter));
    }

    @Test
    public void CanPlaceDrinksOrder() throws Exception {
        OrderedItem drinkItem1 = OrderedItem.drink(4, "bere", 5.6);
        OrderedItem drinkItem2 = OrderedItem.drink(5, "suc", 54.6);

        helper.onAggregate(new TabAggregate())
                .given(new TabOpened(testId, testTable, testWaiter))
                .when(new PlaceOrder(testId, Arrays.asList(drinkItem1, drinkItem2)))
                .then(new DrinksOrdered(testId, Arrays.asList(drinkItem1, drinkItem2)));
    }

    @Test
    public void CanPlaceFoodOrder() throws Exception {
        OrderedItem foodItem1 = OrderedItem.food(3, "gratar", 5.6);
        OrderedItem foodItem2 = OrderedItem.food(4, "ciorba", 4.6);
        helper.onAggregate(new TabAggregate())
                .given(new TabOpened(testId, testTable, testWaiter))
                .when(new PlaceOrder(testId, Arrays.asList(foodItem1, foodItem2)))
                .then(new FoodOrdered(testId, Arrays.asList(foodItem1, foodItem2)));
    }

    @Test
    public void CanPlaceFoodAndDrinkOrder() throws Exception {
        OrderedItem foodItem1 = OrderedItem.food(3, "gratar", 5.6);
        OrderedItem drinkItem2 = OrderedItem.drink(5, "suc", 54.6);
        helper.onAggregate(new TabAggregate())
                .given(new TabOpened(testId, testTable, testWaiter))
                .when(new PlaceOrder(testId, Arrays.asList(foodItem1, drinkItem2)))
                .then(new FoodOrdered(testId, Collections.singletonList(foodItem1)), new DrinksOrdered(testId, Collections.singletonList(drinkItem2)));
    }

    @Test
    public void CanNotOrderWithUnopenedTab() throws Exception {
        helper.onAggregate(new TabAggregate())
                .when(new PlaceOrder(testId, drinkAndFood))
                .thenThrows(TabNotOpen.class);
    }

}