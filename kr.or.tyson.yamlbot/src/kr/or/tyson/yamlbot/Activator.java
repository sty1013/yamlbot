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

import org.apache.log4j.Logger;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
    static Logger logger = Logger.getLogger(Activator.class);

    // The plug-in ID
    public static final String PLUGIN_ID = "kr.or.tyson.yamlbot"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        /*
         * AllTests is loaded earlier by EclipseTestRunner/junit than Activator.
         * So, the following LinkageError occurs. [java]
         * javassist.CannotCompileException: by java.lang.LinkageError: loader
         * (instance of
         * org/eclipse/osgi/internal/baseadaptor/DefaultClassLoader): attempted
         * duplicate class definition for name: "org/tizen/ide/test/AllTests"
         */
        // new TestSpy(); // to call static initializer
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

}
