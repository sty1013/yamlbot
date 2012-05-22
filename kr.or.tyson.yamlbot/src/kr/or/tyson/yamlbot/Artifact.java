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

package kr.or.tyson.yamlbot;

public enum Artifact {
    button, view, menu, table, text, tree, shell, console, sleep;

    public enum Action {
        close, show, select, click, dblclick, dummy; // action for just getting
                                                     // argument value

        String arg;

        Action() {
        }

        Action(String arg) {
            this.arg = arg;
        }

        public String getArgument() {
            return arg;
        }

        Action setArgument(String arg) {
            this.arg = arg;
            return this;
        }

        public static Action getAction(String s) {
            // TODO: Consider multi-threaded
            if (s == null)
                return null;
            if (s.matches(Constant.DUMMY_INDICATOR + ".*"
                    + Constant.DUMMY_INDICATOR))
                return dummy.setArgument(s.substring(1, s.length() - 1));
            else {
                try {
                    return Action.valueOf(s);
                } catch (IllegalArgumentException iae) { // No enum const class
                                                         // Test$Action."aa"
                    return dummy.setArgument(s);
                }
            }
        }
    }

    public String[] getTargets(String line) {
        String[] targets = null;

        switch (this) {
            case menu:
            case tree:
                targets = ActionToken.splitWithTrim(line,
                        Constant.MENU_SEPARATOR);
                break;
            case table:
            	line = line.replace("\"", "");
            default:
                targets = new String[] { line };
        }

        return targets;
    }
}
