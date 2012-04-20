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

import kr.or.tyson.yamlbot.Artifact.Action;

public class ActionToken {
    private Artifact artifact;
    private String[] targets;
    private Artifact.Action action;

    public ActionToken(Artifact artifact, String[] targets, Action action) {
        this.artifact = artifact;
        this.targets = targets;
        this.action = action;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public String[] getTargets() {
        return targets;
    }

    public Artifact.Action getAction() {
        return action;
    }

    public static String[] splitWithTrim(String s, String sep) {
        return s.split("\\s+" + sep + "\\s+");
    }
}
