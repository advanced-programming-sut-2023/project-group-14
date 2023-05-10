package stronghold.controller.messages;

public enum TradeMenuMessage {
	EMPTY_FIELD ("you have emoty field"),
	INVALID_AMOUNT("invalid amount"),
	INVALID_PRICE("invalid price"),
	NOT_HAVING_ENOUGH_MONEY("you don't have enough money"),
	THERE_IS_NO_TRADE_WITH_THIS_ID("there is no trade with this id"),
	SUCCESSFUL_REQUEST("your request has been create successfully"),
	NOT_HAVING_ENOUGH_RESOURCE("you don't have enough resource"),
	SUCCESSFUL_ACCEPT("you accept this trade successfully"),

	;
	private String errorMessage;

	private TradeMenuMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}