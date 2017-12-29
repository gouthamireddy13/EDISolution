package com.abc.dashboard.domain;

import java.util.ArrayList;
import java.util.List;

public class SdDataLoadResult {

	int linesProcessed=0;
	public int getLinesProcessed() {
		return linesProcessed;
	}
	public void setLinesProcessed(int linesProcessed) {
		this.linesProcessed = linesProcessed;
	}
	public int getLinesFailed() {
		return linesFailed;
	}
	public void setLinesFailed(int linesFailed) {
		this.linesFailed = linesFailed;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	int linesFailed = 0;
	List<String> messages = new ArrayList<String>();
	
}
