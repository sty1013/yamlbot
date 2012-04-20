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

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.SWTBot;

public class TextCommand extends AbstractCommand {
    static Logger logger = Logger.getLogger(TextCommand.class);

    String target;
    Action action;

    public TextCommand(String target, Action action) {
        this.target = target;
        this.action = action;
    }

    @Override
    public SWTBot execute(SWTBot bot) {
        switch (action) {
            case dummy:
                bot.textWithLabel(target).setText(action.getArgument());
                break;
        }
        return bot;
    }
}
