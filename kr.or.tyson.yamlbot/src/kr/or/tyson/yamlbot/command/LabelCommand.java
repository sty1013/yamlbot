/*******************************************************************************
 * Copyright (c) 2012 Taeyoung Son.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Taeyoung Son <taeyoung2.son@samsung.com>
 *
 *******************************************************************************/


package kr.or.tyson.yamlbot.command;

import org.eclipse.swtbot.swt.finder.SWTBot;

public class LabelCommand extends AbstractCommand {

    String target;
    
    public LabelCommand(String target) {
        this.target = target;
    }
    @Override
    public SWTBot execute(SWTBot bot) throws Exception {
        bot.label(target);
        return bot;
    }

}
