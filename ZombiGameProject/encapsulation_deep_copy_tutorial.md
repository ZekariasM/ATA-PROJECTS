# Encapsulation and Deep Copy: A Complete Guide

## Table of Contents
1. [Introduction to Copy Constructors](#introduction)
2. [Understanding Object References](#object-references)
3. [Shallow Copy vs Deep Copy](#shallow-vs-deep)
4. [Complete Project Tutorial](#project-tutorial)
5. [Coding Exercises](#exercises)
6. [Best Practices](#best-practices)

---

## 1. Introduction to Copy Constructors {#introduction}

### What is a Copy Constructor?

A **copy constructor** is a special constructor that creates a new object by copying another object of the same class. It's essential when you need to create an independent duplicate of an existing object.

### Why Do We Need Copy Constructors?

Consider this scenario:
- You have a perfectly configured object
- You need another object with the same properties
- But you want them to be **independent** - changing one shouldn't affect the other

**Common Use Cases:**
- Creating backup copies of objects
- Passing objects to methods without affecting the original
- Implementing design patterns like Prototype
- Game development (spawning similar enemies)

### The Problem with Simple Assignment

```java
Zombie bob = new Zombie("Bob", 100);
Zombie karen = bob;  // This doesn't create a new zombie!
```

This creates two variables pointing to the **same object** in memory:

```
bob -----> [Zombie Object in Memory]
           ^
karen ----/
```

---

## 2. Understanding Object References {#object-references}

### Primitive Types vs Reference Types

**Primitive Types** (int, double, boolean, char, etc.):
- Store actual values
- Copying creates independent values
- Immutable by nature

```java
int x = 5;
int y = x;  // y gets value 5
x = 10;     // y is still 5
```

**Reference Types** (Objects, Arrays):
- Store memory addresses
- Copying the reference doesn't copy the object
- Can be mutable

```java
Weapon sword = new Weapon("Sword");
Weapon anotherSword = sword;  // Both point to same weapon
sword.damage = 50;  // anotherSword.damage is also 50!
```

### The String Exception

Strings are objects but behave like primitives because they're **immutable**:
```java
String name1 = "Bob";
String name2 = name1;  // Both point to same String
name1 = "Robert";      // Creates new String, name2 still "Bob"
```

---

## 3. Shallow Copy vs Deep Copy {#shallow-vs-deep}

### Shallow Copy

A **shallow copy** copies primitive values and references, but not the objects that references point to.

```java
public Zombie(Zombie copy) {
    // Shallow copy - PROBLEMATIC!
    this.name = copy.name;           // OK - String is immutable
    this.hitPoints = copy.hitPoints; // OK - primitive
    this.weapon = copy.weapon;       // PROBLEM - shares same weapon!
    this.secondary = copy.secondary; // PROBLEM - shares same array!
}
```

**Problem Visualization:**
```
Original Zombie          Copied Zombie
├── name: "Bob"          ├── name: "Bob"
├── hitPoints: 100       ├── hitPoints: 100
├── weapon ─────┐        ├── weapon ─────┐
└── secondary ───┼────┐   └── secondary ───┼────┐
                 ↓    ↓                    ↓    ↓
            [Weapon]  [Array]         (same objects!)
```

### Deep Copy

A **deep copy** creates new instances of all referenced objects.

```java
public Zombie(Zombie copy) {
    // Deep copy - CORRECT!
    this.name = copy.name;
    this.hitPoints = copy.hitPoints;
    this.weapon = new Weapon(copy.weapon);  // Create new weapon
    
    // Create new array and copy each element
    this.secondary = new Throw[copy.secondary.length];
    for (int i = 0; i < copy.secondary.length; i++) {
        this.secondary[i] = new Throw(copy.secondary[i]);
    }
}
```

**Result Visualization:**
```
Original Zombie          Copied Zombie
├── name: "Bob"          ├── name: "Bob"
├── hitPoints: 100       ├── hitPoints: 100
├── weapon → [Weapon1]   ├── weapon → [Weapon2] (new!)
└── secondary → [Array1] └── secondary → [Array2] (new!)
```

---

## 4. Complete Project Tutorial {#project-tutorial}

### Step 1: Setting Up IntelliJ IDEA

1. **Open IntelliJ IDEA**
2. **Create New Project:**
   - Click `File` → `New` → `Project`
   - Select `Java` from the left panel
   - Choose your Project SDK (Java 8 or higher)
   - Click `Next` → `Next`
   - Project name: `ZombieGameProject`
   - Project location: Choose your directory
   - Click `Finish`

### Step 2: Create Package Structure

1. **Right-click on `src` folder**
2. Select `New` → `Package`
3. Name it: `com.zombiegame`
4. Create sub-packages:
   - `com.zombiegame.models`
   - `com.zombiegame.main`

### Step 3: Create the Weapon Class

**File:** `src/com/zombiegame/models/Weapon.java`

```java
package com.zombiegame.models;

public class Weapon {
    private String type;
    private int damage;
    private int range;
    
    // Default constructor
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
    
    // Getters and Setters
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
```

### Step 4: Create the Throw Class

**File:** `src/com/zombiegame/models/Throw.java`

```java
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
```

### Step 5: Create the Zombie Class with Copy Constructor

**File:** `src/com/zombiegame/models/Zombie.java`

```java
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
```

### Step 6: Create the Main Class to Test

**File:** `src/com/zombiegame/main/ZombieGame.java`

```java
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
```

### Step 7: Running the Project

1. **In IntelliJ IDEA:**
   - Right-click on `ZombieGame.java`
   - Select `Run 'ZombieGame.main()'`

2. **Or use the keyboard shortcut:**
   - Place cursor in the main method
   - Press `Ctrl+Shift+F10` (Windows/Linux) or `Cmd+Shift+R` (Mac)

3. **Expected Output:**
```
=== ZOMBIE GAME - Copy Constructor Demo ===

DEMONSTRATION 1: Reference Copy Problem
----------------------------------------
Created Bob: Zombie[name=Bob, HP=100/100, position=(0,0), weapon=Weapon[Bite, damage=15, range=1], secondaryAttacks=0]
Attempted to copy Bob to Karen using: karen = bob

After changing name to 'Karen':
Bob: Zombie[name=Karen, HP=100/100, position=(0,0), weapon=Weapon[Bite, damage=15, range=1], secondaryAttacks=0]
Karen: Zombie[name=Karen, HP=100/100, position=(0,0), weapon=Weapon[Bite, damage=15, range=1], secondaryAttacks=0]
Are they the same object? true
PROBLEM: Bob's name also changed!

[Additional output continues...]
```

---

## 5. Coding Exercises {#exercises}

### Exercise 1: Basic Copy Constructor

**Task:** Create a `Player` class with a copy constructor.

```java
public class Player {
    private String username;
    private int level;
    private int experience;
    private Inventory inventory;  // You need to create this class
    
    // TODO: Implement constructors including copy constructor
    // TODO: Implement getters and setters
}
```

**Solution Hints:**
- Remember to deep copy the `Inventory` object
- Primitive types can be directly copied
- String is immutable, so direct assignment is safe

### Exercise 2: Complex Deep Copy

**Task:** Extend the Zombie class to include a list of items it drops when defeated.

```java
public class Item {
    private String name;
    private int value;
    // Implement copy constructor
}

// In Zombie class, add:
private List<Item> drops;
```

**Requirements:**
- Implement copy constructor for `Item`
- Update Zombie's copy constructor to deep copy the drops list
- Test that modifying one zombie's drops doesn't affect the copy

### Exercise 3: Zombie Horde Manager

**Task:** Create a `ZombieHorde` class that manages multiple zombies.

```java
public class ZombieHorde {
    private List<Zombie> zombies;
    private String hordeName;
    
    // Create a method to spawn N copies of a template zombie
    public void spawnCopies(Zombie template, int count) {
        // TODO: Use copy constructor to create independent zombies
    }
    
    // Create a copy constructor for the entire horde
    public ZombieHorde(ZombieHorde copy) {
        // TODO: Deep copy all zombies in the horde
    }
}
```

### Exercise 4: Testing Deep Copy

**Task:** Write a test method that verifies deep copy works correctly.

```java
public static boolean testDeepCopy() {
    // Create original zombie
    Zombie original = new Zombie("Original", 100);
    original.setWeapon(new Weapon("Claw", 20, 2));
    
    // Create copy
    Zombie copy = new Zombie(original);
    
    // Test 1: Modify copy's weapon
    copy.getWeapon().setDamage(50);
    
    // Check if original is unchanged
    if (original.getWeapon().getDamage() == 20) {
        System.out.println("✓ Test 1 passed: Weapons are independent");
    } else {
        System.out.println("✗ Test 1 failed: Weapons are shared");
        return false;
    }
    
    // TODO: Add more tests for other properties
    
    return true;
}
```

---

## 6. Best Practices {#best-practices}

### When to Use Copy Constructors

✅ **DO use copy constructors when:**
- You need independent copies of objects
- Implementing the Prototype pattern
- Creating backup/snapshot functionality
- Spawning similar game entities
- Passing objects to methods that might modify them

❌ **DON'T use copy constructors when:**
- You want objects to share state
- Copying would be expensive (very large objects)
- The object represents a unique resource (like a file handle)

### Implementation Guidelines

1. **Always Deep Copy Mutable Objects**
   ```java
   // Good
   this.weapon = new Weapon(copy.weapon);
   
   // Bad
   this.weapon = copy.weapon;
   ```

2. **Handle Null Values**
   ```java
   public Zombie(Zombie copy) {
       if (copy == null) {
           throw new IllegalArgumentException("Cannot copy null zombie");
       }
       // ... rest of copy logic
   }
   ```

3. **Copy Collections Properly**
   ```java
   // For Lists
   this.items = new ArrayList<>();
   for (Item item : copy.items) {
       this.items.add(new Item(item));
   }
   
   // For Arrays
   this.array = new Type[copy.array.length];
   for (int i = 0; i < copy.array.length; i++) {
       this.array[i] = new Type(copy.array[i]);
   }
   ```

4. **Document Copy Behavior**
   ```java
   /**
    * Creates a deep copy of the given Zombie.
    * All mutable fields are independently copied.
    * @param copy the Zombie to copy
    * @throws IllegalArgumentException if copy is null
    */
   public Zombie(Zombie copy) {
       // Implementation
   }
   ```

5. **Consider Using Clone() Alternative**
   - Copy constructors are often preferred over `clone()`
   - They're type-safe and don't require casting
   - They're clearer about what they do

### Common Pitfalls to Avoid

1. **Forgetting to Copy Nested Objects**
   - If A contains B, and B contains C, all need copy constructors

2. **Circular References**
   - Be careful with objects that reference each other

3. **Mixing Shallow and Deep Copy**
   - Be consistent: either shallow copy everything or deep copy everything

4. **Not Testing the Copy**
   - Always verify that modifications to the copy don't affect the original

### Performance Considerations

- Deep copying can be expensive for large object graphs
- Consider lazy copying for rarely-modified fields
- For immutable objects, sharing references is safe and efficient

---

## Summary

Copy constructors are essential for creating independent copies of objects in Java. The key distinction between shallow and deep copying determines whether your objects truly independent or share references to the same data.

**Key Takeaways:**
1. Simple assignment (`=`) copies references, not objects
2. Shallow copy duplicates the object but shares referenced objects
3. Deep copy creates completely independent copies
4. Always deep copy mutable objects in copy constructors
5. Test your copy constructors to ensure independence

By mastering copy constructors and understanding the difference between shallow and deep copying, you can write more robust and predictable object-oriented code, especially important in scenarios like game development where you need many similar but independent entities.

---

## Additional Resources

- [Oracle Java Documentation on Constructors](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html)
- [Effective Java by Joshua Bloch - Item 13: Override clone judiciously](https://www.oreilly.com/library/view/effective-java-3rd/9780134686097/)
- [Design Patterns: Prototype Pattern](https://refactoring.guru/design-patterns/prototype)

---

**Practice Project Ideas:**
1. Extend the zombie game with different zombie types
2. Implement a save/load system using copy constructors
3. Create a card game where cards need to be copied from a deck
4. Build an inventory system with item duplication features