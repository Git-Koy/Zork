# Tasks

How we solved each task of the Zork 2 assignment (`docs/02_Zork2GameProjekt.pdf`).

---

## 2.1 Own rooms

Our world is a medieval map of Europe based on Crusader Kings. It has **11 rooms**:
10 kingdoms and the open ocean.

| Room | Id (`go <id>`) | Neighbours |
|---|---|---|
| Kingdom of Alba | `alba` | England, Wales, Ocean |
| Kingdom of Ireland | `ireland` | Ocean |
| Kingdom of Wales | `wales` | Alba, England, Ocean |
| Kingdom of England | `england` | Alba, Wales, Ocean |
| Kingdom of France | `france` | East Francia, Ocean |
| Kingdom of Denmark | `denmark` | East Francia, Ocean |
| Kingdom of East Francia | `east-francia` | France, Denmark, Bavaria, Croatia, Ocean |
| Kingdom of Croatia | `croatia` | East Francia, Bavaria, Ocean |
| Kingdom of Bavaria | `bavaria` | East Francia, Bohemia, Croatia |
| Kingdom of Bohemia | `bohemia` | Bavaria |
| The open ocean | `ocean` | all coastal kingdoms |

The ocean is not a kingdom and cannot be conquered. It connects the British Isles with the
continent.

- **a) Sketch:** see the map in the [README](../README.md#the-world-map) (`docs/map.png`).
- **b) Class diagram:** see [class-diagram.md](class-diagram.png).

**Change from the template:** instead of the fixed directions north, east, south and west, the
player travels by typing the name of a neighbouring country (`go england`). Our map has
diagonal borders, which four directions cannot show. Every `Room` stores its neighbours in a
`HashMap<String, Room>` with the country id as key. `addNeighbour(Room)` connects two
countries in **both** directions, so each border only has to be written once.

---

## 2.2 Winning

**a) When do you win?** You win when you control all 10 kingdoms.

The player chooses a starting kingdom with `start <kingdom>`. That kingdom counts as conquered.
Each further kingdom is conquered with `attack` while standing in it. `Game` keeps the conquered
kingdoms in a `Set<Kingdom>`. After every won battle it checks whether the set contains all
kingdoms. If it does, the game prints "You have conquered every kingdom. You win!" and ends.

**Difference from the assignment:** the assignment suggests winning by entering a special
"winner room". In a conquest game, reaching a room is not a real goal, so we chose
"conquer everything" as the winning condition instead.

---

## 2.3 Command `back`

**a) How we did it:** `Game` has a field `lastRoom`. Every time the player moves with `go`,
the current room is saved in `lastRoom` **before** `currentRoom` changes.

`back` swaps the two rooms using a temporary variable. This means `back` toggles: using it twice
returns to where the player came from. If the player has not moved yet, `lastRoom` is `null`
and the game prints "There is nowhere to go back to."

We chose one field instead of a `Stack`, because the task only asks to go back one step.

---

## 2.4 Item in a room

**a) How we did it:** we created the class `Item` with the attributes `name` (the short id the
player types, e.g. `brick`) and `description` (a readable text, e.g. "Just a brick"), with a
constructor and getters. `Room` got a method `addItem(Item)` to place an item in a country.
Items are created in the `Game` constructor and placed in their countries there.

**b) How you can ask for the item:** the items are shown automatically when the player enters
a country, because `longDescription()` calls the helper method `itemString()`, the same way it
shows the neighbours with `exitString()`. Example:

```
You are in Kingdom of Wales which has the items brick and burnt-sword.
```

---

## 2.5 Several items

`Room` stores its items in an `ArrayList<Item>` instead of a single field. `addItem` adds to
the list and `removeItem` removes from it.

`itemString()` handles three cases so the output reads like a sentence:

| Items | Output |
|---|---|
| 0 | `which has no item` |
| 1 | `which has brick` |
| 3 | `which has the items holy-grail, skeleton-crown and sun-shield` |

For several items, a loop with an index puts `", "` after each item, `" and "` after the
second to last one and nothing after the last one.

---

## 2.6 Weight

**a) How we did it:** `Item` got the attribute `weight` of type `double` (unit: **kg**), so
weights like 3.5 kg are possible. It is set in the constructor and read with `getWeight()`.
The weight is used by the player's maximum carry weight (see 3.2).

---

## 2.7 Command `map`

`Game` keeps all rooms in a `List<Room>`. The command `map` loops over this list and prints
every country with its items and whether it is controlled or unconquered.

**a) Current room:** inside the loop, each room is compared with `currentRoom`. The current
one gets the marker `(You are here)`. Example:

```
1. Kingdom of Alba which has the items holy-grail, skeleton-crown and sun-shield (controlled) (You are here).
2. Kingdom of Ireland which has no item (unconquered).
```

---

## 3.1 The player can carry an item

**a) How we did it:** we created the class `Player`, which stores the carried items. The
command `pickup <item-id>` looks for the item in the current room. If it is there, it is
removed from the room and added to the player. `inventory` shows what the player carries.

---

## 3.2 Several items with a maximum weight

**b) How we did it:** `Player` stores its items in an `ArrayList<Item>` and keeps track of the
total weight in `currentWeight`. The maximum is **101 kg**.

`addItem(Item)` first checks whether the new weight would go over the maximum. It returns
`true` if the item was added and `false` if it is too heavy. `Game` only removes the item from
the room if `addItem` returned `true`. Otherwise the item stays in the room and the game prints
"You can't pick this up! It's too heavy!".

Example: the Holy Grail weighs 100 kg. With it, the player can still carry the brick (1 kg),
but not the Burnt Sword (5.6 kg).

---

## 3.3 Dropping items

**c) How we did it:** `drop <item-id>` is the reverse of `pickup`. The game looks for the item
in the **player's** inventory. `Player.removeItem(Item)` removes it and subtracts its weight,
so the space is free again. The item is then added to the current room, where it can be picked
up again later. If the dropped item was equipped, it is unequipped.

---

## 3.4 Inheritance (optional)

Not implemented yet.
