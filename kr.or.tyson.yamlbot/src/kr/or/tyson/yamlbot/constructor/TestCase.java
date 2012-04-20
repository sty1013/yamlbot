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

package kr.or.tyson.yamlbot.constructor;

import java.util.Map;

public class TestCase {
    private String test_no;
    private String description;
    private Map<String, String> step;

    public TestCase() {
    }

    public String getTest_no() {
        return test_no;
    }

    public void setTest_no(String test_no) {
        this.test_no = test_no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getStep() {
        return step;
    }

    public void setStep(Map<String, String> step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "TestCase [test_no=" + test_no + ", description=" + description
                + "]";
    }
}
