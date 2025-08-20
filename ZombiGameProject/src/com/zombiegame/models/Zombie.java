package com.zombiegame.models;

import java.util.ArrayList;
import java.util.List;

public class Zombie {
    private String name;
    private int hitPoints;
    private int maxHitPoints;
    private Weapon weapon;
    private List<Throw> secondaryAttacks;
    private int x, y; // Position

    // Default constructor
    public Zombie(String name, int hitPoints) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        this.weapon = new Weapon("Bite", 10, 1);
        this.secondaryAttacks = new ArrayList<>();
        this.x = 0;
        this.y = 0;
    }

    // Full constructor
    public Zombie(String name, int hitPoints, Weapon weapon) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        this.weapon = weapon;
        this.secondaryAttacks = new ArrayList<>();
        this.x = 0;
        this.y = 0;
    }

    // SHALLOW COPY CONSTRUCTOR (WRONG WAY - for demonstration)
    public static Zombie shallowCopy(Zombie copy) {
        Zombie newZombie = new Zombie(copy.name, copy.hitPoints);
        newZombie.weapon = copy.weapon; // PROBLEM: shares same weapon
        newZombie.secondaryAttacks = copy.secondaryAttacks; // PROBLEM: shares same list
        return newZombie;
    }

    // DEEP COPY CONSTRUCTOR (CORRECT WAY)
    public Zombie(Zombie copy) {
        // Copy primitive values
        this.name = copy.name;  // String is immutable, so this is safe
        this.hitPoints = copy.hitPoints;
        this.maxHitPoints = copy.maxHitPoints;
        this.x = copy.x;
        this.y = copy.y;

        // Deep copy the weapon
        this.weapon = new Weapon(copy.weapon);

        // Deep copy the secondary attacks list
        this.secondaryAttacks = new ArrayList<>();
        for (Throw throwAttack : copy.secondaryAttacks) {
            this.secondaryAttacks.add(new Throw(throwAttack));
        }
    }

    // Method to add secondary attack
    public void addSecondaryAttack(Throw attack) {
        this.secondaryAttacks.add(attack);
    }

    // Method to attack
    public void attack(Zombie target) {
        System.out.println(name + " attacks " + target.getName() +
                " with " + weapon.getType());
        target.takeDamage(weapon.getDamage());
    }

    // Method to take damage
    public void takeDamage(int damage) {
        this.hitPoints -= damage;
        if (this.hitPoints < 0) {
            this.hitPoints = 0;
        }
        System.out.println(name + " takes " + damage +
                " damage. HP: " + hitPoints + "/" + maxHitPoints);
    }

    // Method to move
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        System.out.println(name + " moves to position (" + x + ", " + y + ")");
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        if (hitPoints >= 0 && hitPoints <= maxHitPoints) {
            this.hitPoints = hitPoints;
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public List<Throw> getSecondaryAttacks() {
        return secondaryAttacks;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Zombie[name=").append(name);
        sb.append(", HP=").append(hitPoints).append("/").append(maxHitPoints);
        sb.append(", position=(").append(x).append(",").append(y).append(")");
        sb.append(", weapon=").append(weapon);
        sb.append(", secondaryAttacks=").append(secondaryAttacks.size());
        sb.append("]");
        return sb.toString();
    }
}
