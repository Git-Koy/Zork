# Crusader Kings
*made by Ježek Kay and Eggenberger Yann*

## Starting
You pick your starting country at the beginning of the game. Each country has a different starting army.

## How to win
You win by conquering every country.

## Conquering
Every country starts with a fixed number of troops from specific troop classes.
While not in a battle, you will be able to choose between different troop classes. When you conquer a country, your maximum army size increases, and you gain access to the troops of that country. You will have a cap on the number of different troop types you can use to fight. When you change out one troop type for another, you get a slight penalty for switching based on the troops' strength, meaning if you switch from a weaker unit to an overall more powerful one, you get fewer of the new troops than you had of the old. On top of that, you lose 5% of the troops you are switching away from.

## Items
The countries will have items that you will be able to access and pick up after defeating that country's troops. There will be a selection of different categories of items, all of them buffing some of your stats and giving a penalty to others. You will have a cap on how many items you can carry, which increases with every victory. After you have beaten a country, you will have access to its items, and you can go back to other countries that you have already conquered to switch these items, letting you decide which items you want to use in later battles. This interaction is optional; you don't need to choose again after every battle. You may need different skills or combinations of skills to defeat the enemies. An explanation of skills will be added at a later time.

## The world map
![CK3 Worldmap](docs/Map%20CK%203%20Layout.png)

> [!NOTE]
> The map is a work in progress.

## Troops
These are the troops for the different countries:

1. Units of Alba:
     - Pikes
     - Levies

1. Units of Ireland:
     - Armored Footmen
     - Levies

1. Units of Wales:
     - Archers
     - Levies

1. Units of England:
     - Levies
     - Longbows
     - Huscarls

1. Units of France:
     - Armored Horsemen
     - Levies
     - Footknights

1. Units of Denmark:
     - Huscarls
     - Levies
     - Archers

1. Units of East Francia:
     - Armored Footmen
     - Footknights
     - Levies
     - Archers

1. Units of Croatia:
     - Armored Horsemen
     - Light Horsemen

1. Units of Bohemia:
     - Armored Footmen
     - Pikes

Their quantity and quality, their counters, and by how much they get countered will be added at a later time.

## Counter System (Rock Paper Scissors Mechanic)
- Cavalry: Armored Horsemen, Light Horsemen
- Pikemen: Pikes
- Heavy Infantry: Armored Footmen, Footknights, Huscarls
- Archers: Archers, Longbows
- Levies: Levies

### Counter effects
- When a unit is countered by another troop in that battle, it gets a debuff for that fight.
- Great numbers can still beat troops countering others.
- Items may grant fewer debuffs while fighting certain types of troops.
- Items may grant fewer debuffs for bigger army sizes.
- Items may increase your maximum army size.

### Who beats whom
1. Cavalry:
    - _Beat_: Archers, Heavy Infantry
    - _Get beaten by_: Pikemen
  
1. Pikemen:
    - _Beat_: Cavalry
    - _Get beaten by_: Archers, Heavy Infantry

1. Heavy Infantry: 
    - _Beat_: Pikemen
    - _Get beaten by_: Cavalry

1. Archers: 
    - _Beat_: Pikemen
    - _Get beaten by_: Cavalry

1. Levies: 
    - The Levies don't counter any specific troop type, but take reduced damage from all troop types.

## Damage Calculation
Damage is calculated with four numbers:
 - Flat Damage
 - Counter buff/debuff
   - Damage is calculated by taking the flat damage number and adding or subtracting the buff/debuff number.
 - Armor
   - When armor is applied, it gives each troop a percentage chance to not take damage; after that, one point of armor is disabled for the rest of the fight.
 - Health
   - Damage is subtracted from each troop category individually.
