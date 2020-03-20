package com.cpersimmon.advanced;

public class Item {
    int id;
    int id1;
    int id2;//id2应该为2位

    public Item(int sId) {
        id = sId;
    }

    public Item(int sId1, int sId2) {
        id1 = sId1;
        id2 = sId2;
    }

    private int getId() {
        return id1 * 100 + id2;
    }

    private int getId1() {
        return (id - id2) / 100;
    }

    private int getId2() {
        return id - id2 * 100;
    }

    private String getName() {
        switch (id1) {
            case 0:
                switch (id2) {
                }
                return "";
        }
        return "";
    }
}
