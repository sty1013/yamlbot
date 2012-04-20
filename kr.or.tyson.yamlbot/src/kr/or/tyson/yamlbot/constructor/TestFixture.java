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

import java.util.List;

public class TestFixture {
    private List<Activity> setUp;
    private List<TestSuite> suites;

    public List<Activity> getSetUp() {
        return setUp;
    }

    public void setSetUp(List<Activity> setUp) {
        this.setUp = setUp;
    }

    public List<TestSuite> getSuites() {
        return suites;
    }

    public void setSuites(List<TestSuite> suites) {
        this.suites = suites;
    }

    public TestCase getTestCase(String test_no) {
        for (TestSuite suite : suites) {
            for (TestCase tc : suite.getTest_cases())
                if (test_no.equals(tc.getTest_no()))
                    return tc;
        }
        return null;
    }
}
