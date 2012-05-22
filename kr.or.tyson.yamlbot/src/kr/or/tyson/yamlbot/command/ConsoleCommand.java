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

import java.io.File;

import kr.or.tyson.yamlbot.Artifact.Action;
import kr.or.tyson.yamlbot.util.ProcessRunner;

import org.eclipse.swtbot.swt.finder.SWTBot;

public class ConsoleCommand extends AbstractCommand {

    String target;
    Action action;
    
    public ConsoleCommand(String target, Action action) {
        this.target = target;
        this.action = action;
    }
    
    @Override
    public SWTBot execute(SWTBot bot) {
//        returnExecute(target, action.getArgument());
        ProcessRunner.batchExecute(target, null, new File(action.getArgument()));
        return bot;
    }

}
