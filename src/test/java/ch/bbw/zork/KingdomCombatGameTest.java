package ch.bbw.zork;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * Tests kingdom-combat command flows through the terminal game.
 */
public class KingdomCombatGameTest {

	@Test
	public void welcomeListsEverySelectableKingdom() {
		String output = play("quit\n");

		assertTrue(output.contains("Available kingdoms: Alba, Ireland, Wales, England, France, Denmark, East Francia, Croatia, Bohemia, Bavaria."));
	}

	@Test
	public void everyKingdomCanBeSelectedAsAStartingLocation() {
		for (String kingdom : new String[] { "Alba", "Ireland", "Wales", "England", "France",
				"Denmark", "East Francia", "Croatia", "Bohemia", "Bavaria" }) {
			String output = play("start " + kingdom + "\nquit\n");
			assertTrue(kingdom, output.contains("You are in Kingdom of " + kingdom));
			assertTrue(kingdom, output.contains("Your army is ready."));
		}
	}

	@Test
	public void startAcceptsEnumNameWithUnderscores() {
		String output = play("start EAST FRANCIA\nquit\n");

		assertTrue(output.contains("You are in Kingdom of East Francia"));
	}

	@Test
	public void invalidStartingKingdomCanBeRetried() {
		String output = play("start Atlantis\nstart Alba\nquit\n");

		assertTrue(output.contains("Choose one of:"));
		assertTrue(output.contains("You are in Kingdom of Alba"));
	}

	@Test
	public void missingStartingKingdomIsRejected() {
		String output = play("start\nquit\n");

		assertTrue(output.contains("Choose one of:"));
		assertFalse(output.contains("Your army is ready."));
	}

	@Test
	public void startingKingdomCannotBeChangedAfterGameStarts() {
		String output = play("start Alba\nstart France\nquit\n");

		assertTrue(output.contains("already chosen a starting kingdom"));
		assertFalse(output.contains("You are in Kingdom of France"));
	}

	@Test
	public void initialStatusShowsSelectedKingdomAndArmy() {
		String output = play("start Alba\nstatus\nquit\n");

		assertTrue(output.contains("Location: Alba (controlled)"));
		assertTrue(output.contains("Conquered: 1/10."));
		assertTrue(output.contains("Equipped item: none"));
	}

	@Test
	public void helpExplainsStartingAndAttacking() {
		String output = play("help\nquit\n");

		assertTrue(output.contains("start <kingdom>"));
		assertTrue(output.contains("unconquered kingdom"));
		assertTrue(output.contains("equip <item-id>"));
		assertTrue(output.contains("switch <current_type> <unlocked_type>"));
	}

	@Test
	public void movementBeforeStartingIsRejected() {
		String output = play("go wales\nquit\n");

		assertTrue(output.contains("Choose a starting kingdom first"));
	}

	@Test
	public void movementAcceptsNeighborCountryId() {
		String output = play("start Alba\ngo wales\nquit\n");

		assertTrue(output.contains("You are in Kingdom of Wales"));
		assertTrue(output.contains("unconquered. Use 'attack'"));
	}

	@Test
	public void movementAcceptsMultiwordCountryName() {
		String output = play("start France\ngo east francia\nquit\n");

		assertTrue(output.contains("You are in Kingdom of East Francia"));
	}

	@Test
	public void movementRejectsNonNeighboringCountry() {
		String output = play("start Alba\ngo france\nquit\n");

		assertTrue(output.contains("That is not an option"));
	}

	@Test
	public void oceanHasNoKingdomToConquer() {
		String output = play("start Alba\ngo ocean\nstatus\nattack\nquit\n");

		assertTrue(output.contains("You are at sea. There is no kingdom to conquer here."));
		assertTrue(output.contains("Location: The open ocean"));
		assertTrue(output.contains("There is no kingdom to attack at sea."));
	}

	@Test
	public void backRestoresThePreviousKingdomControlState() {
		String output = play("start Alba\ngo wales\nback\nstatus\nquit\n");

		assertTrue(output.contains("Location: Alba (controlled)"));
	}

	@Test
	public void mapShowsControlStateAfterGameStarts() {
		String output = play("start Alba\nmap\nquit\n");

		assertTrue(output.contains("Kingdom of Alba which has the items"));
		assertTrue(output.contains("(controlled) (You are here)"));
		assertTrue(output.contains("Kingdom of Wales which has the items brick and burnt-sword (unconquered)"));
	}

	@Test
	public void mapBeforeStartingRequestsAStartingKingdom() {
		String output = play("map\nquit\n");

		assertTrue(output.contains("Choose a starting kingdom first."));
	}

	@Test
	public void attackBeforeStartingIsRejected() {
		String output = play("attack\nquit\n");

		assertTrue(output.contains("Choose a starting kingdom first."));
	}

	@Test
	public void controlledKingdomCannotBeAttacked() {
		String output = play("start Alba\nattack\nquit\n");

		assertTrue(output.contains("You already control this kingdom."));
	}

	@Test
	public void attackRejectsUnexpectedArguments() {
		String output = play("start Alba\ngo wales\nattack france\nquit\n");

		assertTrue(output.contains("Use: attack"));
	}

	@Test
	public void strongerArmyConquersDefendingKingdom() {
		String output = play("start France\ngo east-francia\nattack\nquit\n");

		assertTrue(output.contains("East Francia has been conquered."));
		assertTrue(output.contains("Your army strength:"));
		assertTrue(output.contains("East Francia army strength:"));
	}

	@Test
	public void weakerArmyDoesNotConquerAndRemainsIntact() {
		String output = play("start Wales\ngo england\nattack\nstatus\nquit\n");

		assertTrue(output.contains("The defending kingdom wins. Your army remains intact."));
		assertTrue(output.contains("Army: 27 soldiers in 2 troop types."));
		assertTrue(output.contains("Conquered: 1/10."));
	}

	@Test
	public void conqueredKingdomUnlocksItsTroopTypes() {
		String output = play("start France\ngo east-francia\nattack\ntroops\nquit\n");

		assertTrue(output.contains("New troop types unlocked."));
		assertTrue(output.contains("Unlocked types:"));
		assertTrue(output.contains("Archers"));
		assertTrue(output.contains("Armored Footmen"));
	}

	@Test
	public void attackingAtSeaDoesNotStartABattle() {
		String output = play("start Alba\ngo ocean\nattack\nquit\n");

		assertFalse(output.contains("army strength:"));
	}

	@Test
	public void statusBeforeStartingIsRejected() {
		String output = play("status\nquit\n");

		assertTrue(output.contains("Choose a starting kingdom first."));
	}

	@Test
	public void statusTracksAnUnconqueredLocation() {
		String output = play("start Alba\ngo wales\nstatus\nquit\n");

		assertTrue(output.contains("Location: Wales (unconquered)"));
	}

	@Test
	public void statusShowsTheNewConquestCount() {
		String output = play("start France\ngo east-francia\nattack\nstatus\nquit\n");

		assertTrue(output.contains("Conquered: 2/10."));
	}

	@Test
	public void statusShowsAnEquippedItemsCombatEffects() {
		String output = play("start Alba\npickup holy-grail\nequip holy-grail\nstatus\nquit\n");

		assertTrue(output.contains("Equipped item: Holy Grail of Power (-5% cavalry power, +10% levies power)"));
	}

	@Test
	public void troopsBeforeStartingIsRejected() {
		String output = play("troops\nquit\n");

		assertTrue(output.contains("Choose a starting kingdom first."));
	}

	@Test
	public void troopsListsStartingFormationAndUnlockedTypes() {
		String output = play("start Alba\ntroops\nquit\n");

		assertTrue(output.contains("Pikes: 12"));
		assertTrue(output.contains("Levies: 15"));
		assertTrue(output.contains("Unlocked types: Pikes, Levies"));
	}

	@Test
	public void troopsListsEverySelectableKingdomsStartingFormation() {
		for (String kingdom : new String[] { "Alba", "Ireland", "Wales", "England", "France",
				"Denmark", "East Francia", "Croatia", "Bohemia", "Bavaria" }) {
			String output = play("start " + kingdom + "\ntroops\nquit\n");
			assertTrue(kingdom, output.contains("Your troops:"));
			assertTrue(kingdom, output.contains("Unlocked types:"));
		}
	}

	@Test
	public void switchBeforeStartingIsRejected() {
		String output = play("switch pikes archers\nquit\n");

		assertTrue(output.contains("Choose a starting kingdom first."));
	}

	@Test
	public void switchRequiresTwoTroopNames() {
		String output = play("start Alba\nswitch pikes\nquit\n");

		assertTrue(output.contains("Use: switch <current_type> <unlocked_type>."));
	}

	@Test
	public void switchRejectsUnknownTroopNames() {
		String output = play("start Alba\nswitch dragons pikes\nquit\n");

		assertTrue(output.contains("That troop switch is not available."));
	}

	@Test
	public void switchRejectsLockedTargetType() {
		String output = play("start Alba\nswitch pikes archers\nquit\n");

		assertTrue(output.contains("That troop switch is not available."));
	}

	@Test
	public void switchRejectsAlreadyActiveTargetType() {
		String output = play("start Alba\nswitch pikes levies\nquit\n");

		assertTrue(output.contains("That troop switch is not available."));
	}

	@Test
	public void switchRejectsMissingSourceStack() {
		String output = play("start France\ngo east-francia\nattack\nswitch archers pikes\nquit\n");

		assertTrue(output.contains("That troop switch is not available."));
	}

	@Test
	public void unlockedTroopCanReplaceAnActiveStack() {
		String output = play("start France\ngo east-francia\nattack\nswitch armored_horsemen archers\ntroops\nquit\n");

		assertTrue(output.contains("Your troops are now using Archers."));
		assertTrue(output.contains("Archers: 22"));
		assertFalse(output.contains("Armored Horsemen:"));
	}

	@Test
	public void equipBeforeStartingIsRejected() {
		String output = play("equip holy-grail\nquit\n");

		assertTrue(output.contains("Choose a starting kingdom first."));
	}

	@Test
	public void equipRequiresAnItemId() {
		String output = play("start Alba\nequip\nquit\n");

		assertTrue(output.contains("Use: equip <item-id>."));
	}

	@Test
	public void itemMustBeCarriedBeforeItCanBeEquipped() {
		String output = play("start Alba\nequip holy-grail\nquit\n");

		assertTrue(output.contains("You can only equip an item in your inventory."));
	}

	@Test
	public void carriedInventoryItemCanBeEquippedWithItsCombatEffect() {
		String output = play("start Alba\npickup holy-grail\nequip holy-grail\nstatus\nquit\n");

		assertTrue(output.contains("Equipped Holy Grail of Power (-5% cavalry power, +10% levies power)."));
		assertTrue(output.contains("Equipped item: Holy Grail of Power"));
	}

	@Test
	public void skeletonCrownShowsItsHeavyInfantryAndArcherEffects() {
		String output = play("start Alba\npickup skeleton-crown\nequip skeleton-crown\nquit\n");

		assertTrue(output.contains("Skeleton Crown of Conjuring (+10% heavy infantry power, -5% archers power)"));
	}

	@Test
	public void sunShieldShowsItsArcherAndHeavyInfantryEffects() {
		String output = play("start Alba\npickup sun-shield\nequip sun-shield\nquit\n");

		assertTrue(output.contains("Sun Shield of Light (-5% heavy infantry power, +10% archers power)"));
	}

	@Test
	public void brickShowsItsPikemenAndLevyEffects() {
		String output = play("start Wales\npickup brick\nequip brick\nquit\n");

		assertTrue(output.contains("Just a brick (+10% pikemen power, -5% levies power)"));
	}

	@Test
	public void burntSwordShowsItsCavalryAndPikemenEffects() {
		String output = play("start Wales\npickup burnt-sword\nequip burnt-sword\nquit\n");

		assertTrue(output.contains("Burnt Sword of Fire (+10% cavalry power, -5% pikemen power)"));
	}

	@Test
	public void droppingEquippedItemUnequipsIt() {
		String output = play("start Alba\npickup holy-grail\nequip holy-grail\ndrop holy-grail\ninventory\nquit\n");

		assertTrue(output.contains("Equipped item: none"));
	}

	@Test
	public void inventoryListsItemIdsForEquipmentCommands() {
		String output = play("start Alba\npickup holy-grail\ninventory\nquit\n");

		assertTrue(output.contains("Item ids: holy-grail."));
	}

	private String play(String input) {
		InputStream previousInput = System.in;
		PrintStream previousOutput = System.out;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream capturedOutput = new PrintStream(output);
		try {
			System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
			System.setOut(capturedOutput);
			new Game().play();
			return output.toString();
		} finally {
			System.setIn(previousInput);
			System.setOut(previousOutput);
			capturedOutput.close();
		}
	}
}
