package com.zombiegame.models;

public class Weapon {
    private String type;
    private int damage;
    private int range;

    // default constructor
    public Weapon(String type, int damage, int range) {
        this.type = type;
        this.damage = damage;
        this.range = range;
    }

    // Copy constructor for Weapon
    public Weapon(Weapon copy) {
        this.type = copy.type;
        this.damage = copy.damage;
        this.range = copy.range;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        if (damage > 0) {
            this.damage = damage;
        }

    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        if (range > 0) {
            this.range = range;
        }
    }

    @Override
    public String toString() {
        return String.format("Weapon[%s, damage=%d, range=%d]",
                type, damage, range);
    }
}
