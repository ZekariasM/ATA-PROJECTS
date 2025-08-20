package com.zombiegame.models;

public class Throw {
    private String projectile;
    private int damage;
    private int cooldown;

    // Default constructor
    public Throw(String projectile, int damage, int cooldown) {
        this.projectile = projectile;
        this.damage = damage;
        this.cooldown = cooldown;
    }

    // Copy constructor
    public Throw(Throw copy) {
        this.projectile = copy.projectile;
        this.damage = copy.damage;
        this.cooldown = copy.cooldown;
    }

    // Getters and Setters
    public String getProjectile() {
        return projectile;
    }

    public void setProjectile(String projectile) {
        this.projectile = projectile;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        if (damage > 0) {
            this.damage = damage;
        }
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        if (cooldown >= 0) {
            this.cooldown = cooldown;
        }
    }

    @Override
    public String toString() {
        return String.format("Throw[%s, damage=%d, cooldown=%ds]",
                projectile, damage, cooldown);
    }
}
