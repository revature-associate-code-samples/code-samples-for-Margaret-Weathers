package com.ex.api.opentdb;

import java.util.Arrays;

/**
 * Represents the object constructed directly from the JSON retrieved from
 *  Open Trivia Database (https://opentdb.com).
 */
public class OpenTDBAPIJson {
	
	private String response_code;
	private QuestionJson[] results;
	
	public String getResponse_code() {
		return response_code;
	}
	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}
	public QuestionJson[] getResults() {
		return results;
	}
	public void setResults(QuestionJson[] results) {
		this.results = results;
	}
	@Override
	public String toString() {
		return "ObjectJSON [response_code=" + response_code + ", question=" + Arrays.toString(results) + "]";
	}
}
