/*******************************************************************************
 * Copyright (c) 2012 Kangho Kim and Taeyoung Son.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Kangho Kim <kh5325@samsung.com>
 * Taeyoung Son <taeyoung2.son@samsung.com>
 *
 *******************************************************************************/

package kr.or.tyson.yamlbot.command;

import kr.or.tyson.yamlbot.ActionToken;
import kr.or.tyson.yamlbot.Artifact.Action;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

public class CommandFactory {
    SWTWorkbenchBot bot;

    public CommandFactory(SWTWorkbenchBot bot) {
        this.bot = bot;
    }

    private Action getAction(Action action, Action def) {
        return (action == null) ? def : action;
    }

    public Command createCommand(ActionToken token) {
        Command command = null;
        Action action = token.getAction();

        switch (token.getArtifact()) {
            case button:
                command = new ButtonCommand(token.getTargets()[0], getAction(
                        action, Action.click));
                break;
            case view:
                command = new ViewCommand(token.getTargets()[0], getAction(
                        action, Action.select));
                break;
            case menu:
                command = new MenuCommand(token.getTargets());
                break;
            case table:
                command = new TableCommand(token.getTargets());
                break;
            case text:
                command = new TextCommand(token.getTargets()[0], action);
                break;
            case tree:
                command = new TreeCommand(token.getTargets(), getAction(action,
                        Action.select));
                break;
            case shell:
                command = new ShellCommand(token.getTargets()[0]);
                break;
        }

        return command;
    }
}
