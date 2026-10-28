# Crusader Kings
## made by Ježek Kay and Eggenberger Yann

DODO (Task / Responsibility )

Update map with Ireland and Wales / Yann





Where you Start: You will be able to Pick where you start, the Options are Wales, Scotland (Alba), Ireland.
According to your Startingpoint you get Slightly Modified troops.


Reason you win: You Conquer Every Available Room (except the ocean) 

How you conquer: Every Room Starts with a Fixed Number of troops and certained troop Classes specified on the room you are in.
At the start and during the Game you will be given a Decision to choose between different troop Classes. When you Conquer a Room you get additional Troop Max size and acess to the Troops Specified 
for that room. You will have a max Cap of Different Troops you can use to Fight. Also Considered is your Troops overall Strengh, when you change out one trooptype for another you get a slight penalty for switching acording to the troops strengh so if you switch from a weaker unit to a overall more powerful one you get less of them. on top of that there is a 5% Loss everytime you switch that you loose on your overall troopcap certain Troops counter others with the historical logic

Items in Rooms: The Rooms will have "Items" that you will be able to access and Pick up after you have defeated the Troops in this room. There will be a Selection of 3 Different types of Items 
all of them buffing some of your fighting stats and giving a penalty to other stats. You will have a cap on how many Items you can carry around increasing with every room beaten. After you have beaten a room you will always have access to its items and you can go back to switch out to be given the decision on what Item to take with you again. this interaction needs to be facultative you dont need to choose again in every room you pass. You may need different skills or combinations of skills to defeat the enemys.

![CK3 Worldmap](docs/Map%20CK%203%20Layout.png)
The map is a work in progress.

The Troops for Starting and For the Different rooms, in their quantity and Quality and their Countermeasures and by how much stats they get countered:

Starting Units Alba: 
  Pikes
  Levies

Starting Units Ireland:
  Armored Footmen
  Levies

Staring Units Wales:
  Archers
  Levies

Units England:
  Levies
  Longbows
  Huscarls

  Units France:
  Armored Horsemen
  Levies
  Footknights

  Units Danemark:
  Huscarls
  Levies
  Archers

  Units East Francia:
  Armored Footmen
  Footknights
  levies
  Archers

  Croatia:
  Armored Horsemen
  Light Horsemen

  Bohemia:
  Armored Footmen
  Pikes
  Winged Hussars
  
  Rock Paper scissors Mecanic:

  Horsetroops: Armored Horsemen, Light Horsemen
  Pikean Troops: Pikes
  Armored Foottroops: Armored Footmen, Footknights, Huscarls
  Archertypes: Archers, Longbows
  levies

Advantage: When a Unit is countered by another, they might get a debuff while Fighting them, Great numbers can still beat troops countering others, items may grant less debuffs while fighting certain types of troops or greater Troop size

Who beats who: 

Horsetroops:
  Beat: Archertypes
  get beaten by: Pikean Troops
  
Pikean Troops:
  Beat: Horsetroops
  get beaten by: Archertypes

Armored Foottroops: 
  Beat: Pikean Troops
  get beaten by: Horsetroops

  Archertypes: 
    Beat: Pikean Troops
    get beaten By Horsetroops

levies: 
slight damage reduction against every troop but no great counters

Damage Calculation:

Damage gets calculated in 3 Ways:

  Flat dmg
  Counter buff / debuff
  Armour
  Health

  When applied Armor, it gives each troop a percentage to not take once when dmg gets calculated, after that 1 Armourstat is removed until the fight is over.
  Flat dmg: You take the DMG Stat and the Counter Buff/Debuff that calculated is effective dmg you deal now you try the Armour % of the enemy to look at if they take dmg or not.
  Dmg is Deducted from overall health of the Troop type, for each troop type this gets calculated individually so everytime they take according dmg. Armour gets removed when under 50% of original    troop size or Troopsize <100.

  



