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
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;

public class ViewCommand extends AbstractCommand {
    static Logger logger = Logger.getLogger(ViewCommand.class);

    String target;
    Action action;

    public ViewCommand(String target, Action action) {
        this.target = target;
        this.action = action;
    }

    @Override
    public SWTBot execute(SWTBot bot) throws Exception {
        if (!(bot instanceof SWTWorkbenchBot))
            throw new Exception("Not SWTWorkbenchBot type");

        SWTBot swtBot = null;
        // bot.viewByTitle("Welcome").close();
        SWTBotView view = ((SWTWorkbenchBot) bot).viewByTitle(target);
        switch (action) {
            case close:
                view.close();
                break;
            case show:
                view.show();
                break;
            case select:
            default:
                logger.info(target);
                swtBot = view.bot();
                break;
        }
        return swtBot;
    }
}
