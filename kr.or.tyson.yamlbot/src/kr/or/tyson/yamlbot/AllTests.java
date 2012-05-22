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

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.or.tyson.yamlbot.Artifact.Action;
import kr.or.tyson.yamlbot.command.Command;
import kr.or.tyson.yamlbot.command.CommandFactory;
import kr.or.tyson.yamlbot.command.MenuCommand;
import kr.or.tyson.yamlbot.constructor.Activity;
import kr.or.tyson.yamlbot.constructor.TestCase;
import kr.or.tyson.yamlbot.constructor.TestSuite;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class AllTests {
    static final int TIMEOUT = 400;
    static Logger logger = Logger.getLogger(AllTests.class);
    static {
        logger.info("Loading AllTests...");
    }

    static Pattern p = Pattern.compile("((?:[(]([^)]+)[)])|(.+))*"); // (?:X) X,
                                                                     // as a
                                                                     // non-capturing
                                                                     // group

    private static SWTWorkbenchBot workbenchBot = new SWTWorkbenchBot();

    public static ActionToken parse(String line) {
        // 1: (view) Welcome ---> close
        Artifact artifact = null;
        String target_action = null;
        Matcher m = p.matcher(line);
        if (m.find()) {
            artifact = Artifact.valueOf(m.group(2));
            target_action = m.group(1).trim();
        }

        String[] arr = ActionToken.splitWithTrim(target_action,
                Constant.ACTION_INDICATOR);
        Action action = (arr.length > 1) ? Action.getAction(arr[1]) : null;
        return new ActionToken(artifact, artifact.getTargets(arr[0]), action);
    }

    public static void runCommand(Map<String, String> step) throws Exception {
        CommandFactory commandFactory = new CommandFactory(workbenchBot);

        SWTBot bot = null;
        for (String key : step.keySet()) {
            String line = step.get(key);
//
            logger.info("----------------------------" + key + " - " + line
                    + "-----------------------------------");
            Command command = commandFactory.createCommand(parse(line));
            if (command != null) {
                // view.bot().tree()
                bot = command
                        .execute(((bot == null) || (command instanceof MenuCommand)) ? workbenchBot
                                : bot);
                Thread.sleep(TIMEOUT);
            }

        }
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        workbenchBot = new SWTWorkbenchBot();

        for (Activity activity : TestSpy.getTestFixture().getSetUp()) {
            runCommand(activity.getStep());
        }
    }

    @AfterClass
    public static void sleep() {
        workbenchBot.sleep(SWTBotPreferences.TIMEOUT);
    }
    
    @Test
    public void executeTestMethods() throws NoSuchMethodException {
        List<TestSuite> suites = TestSpy.getTestFixture().getSuites();
        for (TestSuite suite : suites) {
            List<TestCase> test_cases = suite.getTest_cases();
            for (TestCase tc : test_cases) {
                if (tc != null)
                    try {
                        logger.info("=====================================================");
                        logger.info("Execute test case");
                        logger.info("test_no : " + tc.getTest_no());
                        logger.info("description : " + tc.getDescription());
                        logger.info("=====================================================");
                        runCommand(tc.getStep());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        logger.error(e);
                    }
            }
        }
    }
}
