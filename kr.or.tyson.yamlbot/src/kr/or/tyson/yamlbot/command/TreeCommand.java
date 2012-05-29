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
import kr.or.tyson.yamlbot.Constant;
import kr.or.tyson.yamlbot.Artifact.Action;
import kr.or.tyson.yamlbot.helper.ContextMenuHelper;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

public class TreeCommand extends AbstractCommand {
    static Logger logger = Logger.getLogger(TreeCommand.class);

    String[] targets;
    Action action;

    public TreeCommand(String[] targets, Action action) {
        this.targets = targets;
        this.action = action;
    }

    @Override
    public SWTBot execute(SWTBot bot) {
        // bot.tree().expandNode("Java").select("Java Project");
        SWTBotTree treeBot = bot.tree();
        SWTBotTreeItem expandNode = null;
        SWTBotTreeItem selectedItem = null; // Returns the current node.

        // [Tizen, Tizen Native Project]
        if (targets.length > 1) {
            String[] expand = new String[targets.length - 1];
            System.arraycopy(targets, 0, expand, 0, expand.length);
            expandNode = treeBot.expandNode(expand);
        }

        String target = targets[targets.length - 1];

        if (expandNode == null) {
            selectedItem = treeBot.getTreeItem(target);
        } else {
            selectedItem = expandNode.getNode(target);
        }

        switch (action) {
            case dblclick:
                selectedItem.doubleClick();
                break;
            case select:
                selectedItem.select();
                break;
            case dummy:
                if (action.getArgument() == null)
                    break;
                ContextMenuHelper.clickContextMenu(treeBot.select(targets),
                        ActionToken.splitWithTrim(action.getArgument(),
                                Constant.MENU_SEPARATOR));
                break;
        }
        return bot;
    }
}