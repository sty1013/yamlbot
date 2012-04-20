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

public class TableCommand extends AbstractCommand {
    static Logger logger = Logger.getLogger(TableCommand.class);

    String[] targets;
    Action action;

    public TableCommand(String[] targets) {
        this(targets, Action.select);
    }

    public TableCommand(String[] targets, Action action) {
        this.targets = targets;
        this.action = action;
    }

    @Override
    public SWTBot execute(SWTBot bot) {
        try {
            switch (action) {
                case select:
                    // FIXME:
                    // hard coding for test.
                    if ("Controlbar Template 1.0".equals(targets[0])) {
                        targets[0] = "  " + targets[0];
                    }
                    bot.table().select(targets);
                    break;
            }
        } catch (Exception e) {
            logger.error("table", e);
        }
        return bot;
    }
}
