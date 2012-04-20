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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import kr.or.tyson.yamlbot.constructor.TestCase;
import kr.or.tyson.yamlbot.constructor.TestFixture;
import kr.or.tyson.yamlbot.constructor.TestSuite;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class TestSpy {
    static Logger logger = Logger.getLogger(TestSpy.class);

    private static TestFixture _fixture = null;

    static {
        BasicConfigurator.configure(); // http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/BasicConfigurator.html

        String[] args = Platform.getCommandLineArgs();
        logger.info("args - " + Arrays.toString(args));
        int i = 0;
        while (i < args.length) {
            if ("-playback-delay".equals(args[i])) {
                // slow down tests
                SWTBotPreferences.PLAYBACK_DELAY = Integer.parseInt(args[++i]);
            }
            /*
             * if ("-timeout".equals(args[i])) { SWTBotPreferences.TIMEOUT =
             * Integer.parseInt(args[++i]); }
             */
            i++;
        }

        logger.info("SWTBotPreferences.PLAYBACK_DELAY = "
                + SWTBotPreferences.PLAYBACK_DELAY);
        logger.info("SWTBotPreferences.TIMEOUT = " + SWTBotPreferences.TIMEOUT);

        InputStream is = null;
        try {
            ClassLoader cl = TestSpy.class.getClassLoader();
            is = cl.getResourceAsStream(Constant.SCRIPT_FILE); // read script
                                                               // file.

            // Set test fixture
            Constructor constructor = new Constructor(TestFixture.class);
            TypeDescription description = new TypeDescription(TestFixture.class);
            // description.putListPropertyType("setUp", Activity.class);
            // description.putListPropertyType("suites", TestSuite.class);
            constructor.addTypeDescription(description);
            Yaml yaml = new Yaml(constructor);
            _fixture = (TestFixture) yaml.load(is);

            // Write @Test methods from test_cases in script
            ClassPool pool = ClassPool.getDefault(); // The ClassPool returned
                                                     // by
                                                     // ClassPool.getDefault()
                                                     // only adds the system
                                                     // classpath
            pool.appendClassPath(new LoaderClassPath(cl));
            CtClass cc = pool.get("org.tizen.ide.swtbot.test.AllTests"); // must
                                                                         // be
                                                                         // string?!,
                                                                         // CtClass(compile-time
                                                                         // class)
            ClassFile ccFile = cc.getClassFile();
            ConstPool constpool = ccFile.getConstPool();
            AnnotationsAttribute attr = new AnnotationsAttribute(constpool,
                    AnnotationsAttribute.visibleTag);
            Annotation annot = new Annotation("org.junit.Test", constpool);
            attr.addAnnotation(annot);

            List<TestSuite> suites = _fixture.getSuites();
            for (TestSuite suite : suites) {
                List<TestCase> test_cases = suite.getTest_cases();
                for (TestCase tc : test_cases) {
                    String test_no = tc.getTest_no();
                    CtMethod mthd = CtNewMethod
                            .make("public void test"
                                    + toFirstCharUpper(test_no)
                                    + "() {"
                                    + " kr.or.tyson.yamlbot.constructor.TestCase tc = kr.or.tyson.yamlbot.TestSpy.getTestFixture().getTestCase(\""
                                    + test_no
                                    + "\");"
                                    + " if (tc != null) runCommand(tc.getStep());"
                                    + " else throw new NoSuchMethodException(\""
                                    + test_no + "\");" + " }", cc);
                    cc.addMethod(mthd);

                    mthd.getMethodInfo().addAttribute(attr); // insert test
                                                             // annotion for
                                                             // junit test
                }
            }

            for (CtMethod m : cc.getDeclaredMethods())
                logger.info("Added @Test method - "
                        + Arrays.toString(m.getAnnotations()) + " / "
                        + m.getLongName());

            // cc.writeFile(); // update the class file
            logger.info("cc.toClass() to load modified class... (loader - "
                    + cl.getClass().getCanonicalName() + ")");
            cc.toClass(cl);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                }
        }
    }

    public static TestFixture getTestFixture() {
        return _fixture;
    }

    private static String toFirstCharUpper(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
