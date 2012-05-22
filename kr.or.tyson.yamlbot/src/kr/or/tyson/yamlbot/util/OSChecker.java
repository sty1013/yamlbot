/*
*  Common
*
* Copyright (c) 2000 - 2011 Samsung Electronics Co., Ltd. All rights reserved.
*
* Contact: 
* Kangho Kim <kh5325.kim@samsung.com>
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Contributors:
* - S-Core Co., Ltd
*
*/
package kr.or.tyson.yamlbot.util;

public class OSChecker {
    //
    // OS ID constants
    //
    public final static int WINDOWS = 0x0001;
    public final static int MAC = 0x0002;
    public final static int LINUX = 0x0004;
    public final static int UNIX = 0x0008;
    public final static int AIX = 0x0010;
    public final static int SOLARIS = 0x0020;

    //
    // Vendor constants
    //
    public final static int SUN = 0x0001;
    public final static int MICROSOFT = 0x0002;
    public final static int APPLE = 0x0004;
    public final static int IBM = 0x0008;

    static int osID = 0;
    static int vendorID = 0;

    /* static initializer */
    static {
        /*
         * OS                : arch      name         version
         * solaris 7         : sparc     Solaris      2.x
         * redhat 6.2(black) : x86       Linux        2.2.15-2.5.0
         * redhat 6.2(ibm)   : i686      Linux        #1 Sat Feb 5 00:28:02 EST 2000.2.2.15-2.5.0
         * win2000(jdk1.1.8) : x86       Windows NT   5.0
         * win2000(jdk1.2.2) : x86       Windows NT   5.0       
         * win2000(jdk1.3)   : x86       Windows 2000 5.0
         * Mac OS 9          : PowerPC   Mac OS       9
         * AIX 5.1           : ppc       AIX          5.1 (vendor : IBM Corporation)
         */
        osID = 0;
        String osName = System.getProperty("os.name").toUpperCase();

        if (osName.indexOf("WINDOWS") >= 0) {
            osID |= OSChecker.WINDOWS;
        } else if (osName.indexOf("MAC") >= 0) {
            osID |= OSChecker.MAC;
        } else if (osName.indexOf("LINUX") >= 0) {
            osID |= OSChecker.LINUX;
        } else if (osName.indexOf("AIX") >= 0) {
            osID |= OSChecker.AIX;
            osID |= OSChecker.UNIX;
        } else if (osName.indexOf("SOLARIS") >= 0) {
            osID |= OSChecker.SOLARIS;
            osID |= OSChecker.UNIX;
        }

        vendorID = 0;
        String vendorName = System.getProperty("java.vendor").toUpperCase();

        if (vendorName.indexOf("IBM") >= 0) {
            vendorID |= OSChecker.IBM;
        } else if (vendorName.indexOf("MICROSOFT") >= 0) {
            vendorID |= OSChecker.MICROSOFT;
        } else if (vendorName.indexOf("APPLE") >= 0) {
            vendorID |= OSChecker.APPLE;
        } else if (vendorName.indexOf("SUN") >= 0) {
            // should check if the vendor is sun in last place
            // 'cause other vendor can port Sun's VM
            vendorID |= OSChecker.SUN;
        }
    }

    /* querying OS */
    public static int getOSID() {
        return osID;
    }

    public static boolean isWindows() {
        return ((osID & OSChecker.WINDOWS) > 0);
    }

    public static boolean isMAC() {
        return ((osID & OSChecker.MAC) > 0);
    }

    public static boolean isLinux() {
        return ((osID & OSChecker.LINUX) > 0);
    }

    public static boolean isAIX() {
        return ((osID & OSChecker.AIX) > 0);
    }

    public static boolean isSolaris() {
        return ((osID & OSChecker.SOLARIS) > 0);
    }

    public static boolean isUnix() {
        return ((osID & OSChecker.UNIX) > 0);
    }

    public static boolean isUnknownOS() {
        return (osID == 0);
    }

    /* querying Vendor */
    public static int getVendorID() {
        return vendorID;
    }

    public static boolean byMicrosoft() {
        return ((vendorID & OSChecker.MICROSOFT) > 0);
    }

    public static boolean byIBM() {
        return ((vendorID & OSChecker.IBM) > 0);
    }

    public static boolean byApple() {
        return ((vendorID & OSChecker.APPLE) > 0);
    }

    public static boolean bySun() {
        return ((vendorID & OSChecker.SUN) > 0);
    }

    public static boolean byUnknownVendor() {
        return (vendorID == 0);
    }

}
