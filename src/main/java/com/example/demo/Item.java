package com.example.demo;

public class Item {
    private static String name;
    private static String quantity;
    private static double rate;
    private static double amount;

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        Item.name = name;
    }

    public static String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        Item.quantity = quantity;
    }

    public static double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        Item.rate = rate;
    }

    public static double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        Item.amount = amount;
    }

    @Override
    public String toString() {
        return "Item [name=" + name + ", quantity=" + quantity + ", rate=" + rate + ", amount=" + amount + "]";
    }



}