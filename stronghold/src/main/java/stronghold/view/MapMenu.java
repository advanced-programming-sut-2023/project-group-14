package stronghold.view;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.controller.MapMenuController;
import stronghold.controller.messages.MapMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.map.Map;
import stronghold.model.map.MapTile;
import stronghold.model.people.Person;
import stronghold.utils.Miscellaneous;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MapMenu {
	private static boolean inGameMode;

	public static void run(Map map) {
		System.out.println("======[Map Menu]======");
		
		MapMenuController.setCurrentMap(map);
		inGameMode = (map == null);

		while (true) {
			String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());
			HashMap<String, String> matcher;

			if (map == null) MapMenuController.updateCurrentMap();

			if ((matcher = CommandParser.getMatcher(input, Command.SHOW_MAP)) != null)
				runShowMap(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_TILE_DETAILS)) != null)
				runShowTileDetails(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.MOVE_MAP)) != null)
				runMoveMap(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.BACK)) != null)
				return;
			else
				System.out.println("Invalid command");
		}
	}

	public static void runShowMap(HashMap<String, String> matcher) {
		MapMenuMessage message = MapMenuController.startShowMap(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y"))
		);
		if (message == MapMenuMessage.SUCCESS)
			displayMap();
		else
			System.out.println(message.getErrorString());
	}

	public static void runMoveMap(HashMap<String, String> matcher) {
		MapMenuMessage message = MapMenuController.moveMap(
			matcher.get("up"),
			matcher.get("down"),
			matcher.get("left"),
			matcher.get("right")
		);
		if (message == MapMenuMessage.SUCCESS)
			displayMap();
		else
			System.out.println(message.getErrorString());
	}

	public static void runShowTileDetails(HashMap<String, String> matcher) {
		showTileDetails(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y"))
		);
	}

	public static void displayMap() {
		int startX = MapMenuController.getCurrentX() - MapMenuController.SHOW_MAP_HEIGHT / 2;
		int startY = MapMenuController.getCurrentY() - MapMenuController.SHOW_MAP_WIDTH / 2;
		Terminal2DPrinter printer = new Terminal2DPrinter();
		for (int i = startX; i - startX < MapMenuController.SHOW_MAP_HEIGHT; i++) {
			printer.addOutput(new String[] {"-".repeat(MapMenuController.SHOW_MAP_WIDTH * 3 + 1)});
			printer.addNewLine();
			for (int j = startY; j - startY < MapMenuController.SHOW_MAP_WIDTH; j++) {
				String[] tileString = MapMenuController.getTileRepresentation(i, j);
				printer.addOutput(new String[] {"|", "|"});
				printer.addOutput(tileString);
			}
			printer.addOutput(new String[] {"|", "|"});
			printer.addNewLine();
		}
		printer.addOutput(new String[] {"-".repeat(MapMenuController.SHOW_MAP_WIDTH * 3 + 1)});
		printer.printOutput();
	}

	private static void showTileDetails(int x, int y) {
		MapMenuMessage errorCheck = MapMenuController.checkCoordinateErrors(x, y);
		if (errorCheck != MapMenuMessage.SUCCESS) {
			System.out.println(errorCheck.getErrorString());
			return;
		}
		MapTile tile = MapMenuController.getCurrentMap().getGrid()[x][y];
		System.out.println("Ground Type: " + tile.getGroundType().getName());
		if (tile.getEnvironmentItem() != null)
			System.out.println("Environment Item: " + tile.getEnvironmentItem());
		if (tile.getBuilding() != null)
			System.out.println("Building: " + tile.getBuilding().getName() + " -> " +
			(inGameMode ? tile.getBuilding().getOwner().getUser().getUserName() : tile.getBuilding().getOwnerIndex()));
		for (int i = 0; i < MapMenuController.getCurrentMap().getGovernmentsCount(); i++) {
			System.out.println("List of people owned by #" + i + " " +
								(inGameMode ? StrongHold.getCurrentGame().getGovernments()[i].getUser().getUserName() : "") +
								": ");
			ArrayList<Person> filteredPeople = Miscellaneous.getPeopleByOwner(tile.getPeople(), i);
			HashMap<String, Integer> peopleCount = Miscellaneous.countPeopleFromArray(filteredPeople);
			for (String personName : peopleCount.keySet())
				System.out.println(personName + ": " + peopleCount.get(personName));
		}
	}
}
