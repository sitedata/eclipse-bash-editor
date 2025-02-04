package de.jcup.basheditor.preferences;

/*
 * Copyright 2017 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */

/**
 * Constant definitions for plug-in preferences
 */
public enum BashEditorPreferenceConstants implements PreferenceIdentifiable {

	P_EDITOR_MATCHING_BRACKETS_ENABLED("matchingBrackets"),
	P_EDITOR_HIGHLIGHT_BRACKET_AT_CARET_LOCATION("highlightBracketAtCaretLocation"),
	P_EDITOR_ENCLOSING_BRACKETS("enclosingBrackets"), P_EDITOR_MATCHING_BRACKETS_COLOR("matchingBracketsColor"),
	P_EDITOR_AUTO_CREATE_END_BRACKETSY("autoCreateEndBrackets"),

	P_LINK_OUTLINE_WITH_EDITOR("linkOutlineWithEditor"),
	
	P_SHOW_VARIABLES_IN_OUTLINE("showVariablesInOutline"),

	P_CODE_ASSIST_ADD_KEYWORDS("codeAssistAddsKeyWords"), 
	
	P_CODE_ASSIST_ADD_SIMPLEWORDS("codeAssistAddsSimpleWords"),
	
	P_CODE_ASSIST_ADD_VARIABLES("codeAssistAddsVariables"),

	P_TOOLTIPS_ENABLED("toolTipsEnabled"),

	P_SAVE_ACTION_EXTERNAL_TOOL_ENABLED("saveActionExternalToolEnabled"),
	P_SAVE_ACTION_EXTERNAL_TOOL_COMMAND("saveActionExternalToolCommand"),
	
	P_LAUNCH_IN_TERMINAL_ENABLED("launchInTerminalEnabled"), 
	
	P_SHOW_META_INFO_IN_DEBUG_CONSOLE("showMetaInfoInDebugConsole"),
	
	P_KEEP_TERMINAL_OPEN_ON_ERRORS("keepLaunchedTerminalOpenOnErrors"),
	
	P_LAUNCH_TERMINAL_COMMAND("launchTerminalCommand2"), 
	P_LAUNCH_STARTER_COMMAND("launchStarterCommand2"), 
	
	P_JUST_OPEN_TERMINAL_COMMAND("openInTerminalCommand"), 
	P_OPEN_PATH_IN_TERMINAL_ENABLED("openPathInTerminalEnabled"), 
	
	P_KEEP_TERMINAL_OPEN_ALWAYS("keepLaunchedTerminalOpenAlways2"), 
	
	P_REPLACE_TAB_BY_SPACES_STRATEGY("replaceTabBySpacesStrategy"),
	
	P_AMOUNT_OF_SPACES_FOR_TAB_REPLACEMENT("replaceTabBySpacesAmount"),
	
	P_SHARED_MODEL_ENABLED("sharedModelEnabled"),
	
	P_LINK_FUNCTIONS_STRATEGY("linkFunctionsStrategy"), 
	
	P_USER_HOME_CUSTOMPATH("userHomeCustomPath"),
	
	P_SORT_OUTLINE_ALPHABETICAL("sortOutlineAlphabetical"),
	
	;
	

	private String id;

	private BashEditorPreferenceConstants(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
