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

import kr.or.tyson.yamlbot.Artifact.Action;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;

public class MenuCommand extends AbstractCommand {
    String[] targets;
    Action action;

    public MenuCommand(String[] targets) {
        this(targets, Action.click);
    }

    public MenuCommand(String[] targets, Action action) {
        this.targets = targets;
        this.action = action;
    }

    @Override
    public SWTBot execute(SWTBot bot) {
        SWTBotMenu menu = null;
        for (String target : targets) {
            // bot.menu("File").menu("New").menu("Project...").click();
            menu = (menu == null) ? bot.menu(target) : menu.menu(target);
        }
        if (menu != null) {
            switch (action) {
                case click:
                    menu.click();
                    break;
            }
        }
        return bot;
    }
}
