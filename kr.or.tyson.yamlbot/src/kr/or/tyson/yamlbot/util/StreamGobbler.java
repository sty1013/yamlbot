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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamGobbler extends Thread
{
	InputStream is;
	OutputStream os;
	String result;
	private static Object synchronizer = new Object();
	
	public StreamGobbler(InputStream is) {
		this.is = is;
		this.os = null;
	}

	public StreamGobbler(InputStream is, OutputStream os) {
		this.is = is;
		this.os = os;
	}

	public void run()
	{
		StringBuffer buffer = new StringBuffer();
		try {		
			synchronized (synchronizer) {
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line;
				while ( (line = br.readLine()) != null) {
					buffer.append(line);
					buffer.append("\n");
					
					if ( os != null )
						os.write( (line + "\n").getBytes());
				}
				br.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			result = buffer.toString();
		}
	}
	public String getResult() {
		return result;
	}
}
