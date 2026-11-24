# How to Play

## Start the game

Open the project in IntelliJ and run `main` in `src/main/java/ch/bbw/zork/Zork2.java`.

## Goal

Conquer all **10 kingdoms**. You win as soon as the last one falls.

## Quick start

```
start alba          choose your starting kingdom (it is yours from the start)
status              look at your army
go wales            travel to a neighbouring country
attack              fight the kingdom you are standing in
```

## Commands

| Command | What it does |
|---|---|
| `start <kingdom>` | Choose your starting kingdom, e.g. `start east-francia` |
| `go <country>` | Travel to a neighbour, e.g. `go england` |
| `back` | Return to the country you came from |
| `map` | Show all countries, their items and who controls them |
| `attack` | Attack the kingdom you are in |
| `status` | Show your location, army size and progress |
| `troops` | Show your troops and unlocked troop types |
| `switch <from> <to>` | Turn one troop type into another unlocked type, e.g. `switch levies huscarls` |
| `pickup <item>` | Pick up an item in the current country |
| `drop <item>` | Drop an item in the current country |
| `inventory` | Show what you carry |
| `equip <item>` | Equip a carried item for battle |
| `help` | Show the help text |
| `quit` | End the game |

Names with spaces are written with a hyphen: `east-francia`, `holy-grail`.