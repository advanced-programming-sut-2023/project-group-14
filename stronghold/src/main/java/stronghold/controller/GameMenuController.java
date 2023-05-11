package stronghold.controller;

import stronghold.controller.messages.GameMenuMessage;
import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.Game;
import stronghold.model.ResourceType;
import stronghold.model.environment.Wall;
import stronghold.view.GameMenu;

public class GameMenuController {
	private static Game game;
	public static void setGame(Game game) {
		GameMenuController.game = game;
	}

	public static GameMenuMessage dropWall(int x1, int y1, int x2, int y2) {
		int neededStone = Wall.REQUIRED_STONE * (x2 - x1 + 1) * (y2 - y1 + 1);
		if (neededStone > game.getCurrentPlayer().getResourceCount(ResourceType.ROCK))
			return GameMenuMessage.NOT_ENOUGH_RESOURCES;
		MapEditorMenuController.setMap(game.getMap());
		MapEditorMenuController.setSelectedGovernment(game.getCurrentPlayerIndex());
		MapEditorMenuMessage message = MapEditorMenuController.dropWall(x1, y1, x2, y2);
		if (message != MapEditorMenuMessage.SUCCESS) {
			GameMenu.showMapEditorError(message);
			return GameMenuMessage.CONSTRUCTION_FAILED;
		}
		game.getCurrentPlayer().decreaseResource(ResourceType.ROCK, neededStone);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage dropBuilding(int x, int y, String type) {
		
	}
}
