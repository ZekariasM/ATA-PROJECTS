package com.zombiegame.main;

import com.zombiegame.models.*;

public class ZombieGame {

    public static void main(String[] args) {
        System.out.println("=== ZOMBIE GAME - Copy Constructor Demo ===\n");

        // Demonstrate the problem with reference copying
        demonstrateReferenceProblem();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Demonstrate shallow copy problems
        demonstrateShallowCopyProblem();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Demonstrate deep copy solution
        demonstrateDeepCopySolution();
    }

    private static void demonstrateReferenceProblem() {
        System.out.println("DEMONSTRATION 1: Reference Copy Problem");
        System.out.println("-".repeat(40));

        // Create original zombie
        Zombie bob = new Zombie("Bob", 100);
        bob.setWeapon(new Weapon("Bite", 15, 1));

        // Wrong way to copy (reference copy)
        Zombie karen = bob;  // This doesn't create a new zombie!

        System.out.println("Created Bob: " + bob);
        System.out.println("Attempted to copy Bob to Karen using: karen = bob");

        // Change Karen's name
        karen.setName("Karen");

        System.out.println("\nAfter changing name to 'Karen':");
        System.out.println("Bob: " + bob);
        System.out.println("Karen: " + karen);
        System.out.println("Are they the same object? " + (bob == karen));
        System.out.println("PROBLEM: Bob's name also changed!");
    }

    private static void demonstrateShallowCopyProblem() {
        System.out.println("DEMONSTRATION 2: Shallow Copy Problem");
        System.out.println("-".repeat(40));

        // Create original zombie with weapon and secondary attacks
        Zombie bob = new Zombie("Bob", 100);
        bob.setWeapon(new Weapon("Bite", 15, 1));
        bob.addSecondaryAttack(new Throw("Arm", 20, 5));

        // Shallow copy
        Zombie karen = Zombie.shallowCopy(bob);
        karen.setName("Karen");

        System.out.println("Created Bob with weapon and secondary attack");
        System.out.println("Created Karen using shallow copy");
        System.out.println("\nInitial state:");
        System.out.println("Bob: " + bob);
        System.out.println("Karen: " + karen);

        // Modify Karen's weapon
        System.out.println("\nModifying Karen's weapon damage to 30...");
        karen.getWeapon().setDamage(30);

        System.out.println("Bob's weapon: " + bob.getWeapon());
        System.out.println("Karen's weapon: " + karen.getWeapon());
        System.out.println("PROBLEM: Bob's weapon damage also changed!");

        // Check if weapons are the same object
        System.out.println("Are weapons the same object? " +
                (bob.getWeapon() == karen.getWeapon()));
    }

    private static void demonstrateDeepCopySolution() {
        System.out.println("DEMONSTRATION 3: Deep Copy Solution");
        System.out.println("-".repeat(40));

        // Create original zombie with weapon and secondary attacks
        Zombie bob = new Zombie("Bob", 100);
        bob.setWeapon(new Weapon("Bite", 15, 1));
        bob.addSecondaryAttack(new Throw("Arm", 20, 5));
        bob.addSecondaryAttack(new Throw("Rock", 10, 3));
        bob.move(5, 5);

        // Deep copy using copy constructor
        Zombie karen = new Zombie(bob);  // Using copy constructor
        karen.setName("Karen");

        System.out.println("Created Bob with weapon and secondary attacks");
        System.out.println("Created Karen using deep copy constructor");
        System.out.println("\nInitial state:");
        System.out.println("Bob: " + bob);
        System.out.println("Karen: " + karen);

        // Modify Karen's weapon
        System.out.println("\nModifying Karen's weapon damage to 30...");
        karen.getWeapon().setDamage(30);

        System.out.println("Bob's weapon: " + bob.getWeapon());
        System.out.println("Karen's weapon: " + karen.getWeapon());
        System.out.println("SUCCESS: Bob's weapon remains unchanged!");

        // Check if weapons are different objects
        System.out.println("Are weapons the same object? " +
                (bob.getWeapon() == karen.getWeapon()));

        // Modify Karen's position
        System.out.println("\nMoving Karen...");
        karen.move(10, 10);
        System.out.println("Bob position: (" + bob.getX() + ", " + bob.getY() + ")");
        System.out.println("Karen position: (" + karen.getX() + ", " + karen.getY() + ")");

        // Add new secondary attack to Karen
        System.out.println("\nAdding new secondary attack to Karen...");
        karen.addSecondaryAttack(new Throw("Spit", 5, 2));
        System.out.println("Bob's secondary attacks: " + bob.getSecondaryAttacks().size());
        System.out.println("Karen's secondary attacks: " + karen.getSecondaryAttacks().size());

        // Combat simulation
        System.out.println("\n=== COMBAT SIMULATION ===");
        bob.attack(karen);
        karen.attack(bob);
    }
}