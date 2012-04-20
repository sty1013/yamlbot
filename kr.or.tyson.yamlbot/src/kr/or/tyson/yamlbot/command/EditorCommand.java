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
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;

public class EditorCommand extends AbstractCommand {
    static Logger logger = Logger.getLogger(EditorCommand.class);

    String target;
    Action action;

    public EditorCommand(String target, Action action) {
        // target : empty.c
        // action :
        this.target = target;
        this.action = action;
    }

    @Override
    public SWTBot execute(SWTBot bot) throws Exception {
        if (!(bot instanceof SWTWorkbenchBot))
            throw new Exception("Not SWTWorkbenchBot type");

        SWTBot swtBot = null;
        // bot.viewByTitle("empty.c").close();
        try {
            SWTBotEditor editor = ((SWTWorkbenchBot) bot)
                    .editorByTitle("empty.c");
            switch (action) {
                case close:
                    editor.close();
                    break;
                case show:
                    editor.show();
                    break;
                case select:
                    swtBot = editor.bot();
                    break;
                default:
                    throw new Exception("Not support \"" + action + "\" action");
            }
            return swtBot;
        } catch (Exception e) {
            logger.error("viewByTitle(\"" + target + "\")", e);
        }
        return swtBot;
    }

}
