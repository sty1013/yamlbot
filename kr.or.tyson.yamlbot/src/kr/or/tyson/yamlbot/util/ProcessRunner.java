package kr.or.tyson.yamlbot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessRunner {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$
    public static final String FILE_SEPARATOR = System.getProperty("file.separator"); //$NON-NLS-1$
    private static final String SHELL_COMMAND_LINUX= "/bin/sh"; //$NON-NLS-1$
    private static final String SHELL_COMMAND_WINDOW= "cmd"; //$NON-NLS-1$

    public static String returnExecute(String command, String workingDir) {
        BufferedReader input = null;
        StringBuilder contents = new StringBuilder();
        String line = null;
        
        Process proc = null;
        
        String[] fullCommand = getCommand(command);
        
        try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.command(fullCommand);
                if(workingDir != null) {
                    pb.directory(new File(workingDir));
                }
                proc = pb.start();
                input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                while((line=input.readLine())!=null){
                    contents.append(line);
                    contents.append(LINE_SEPARATOR);
                }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally{
            if(proc!=null)  
                proc.destroy();
            if(input!=null)
                try {
                    input.close();
                } catch (IOException e) {
                }
        }
        
        return contents.toString().trim();
        
    }
    public static String[] getCommand(String command){
        
        if(OSChecker.isWindows()){
            return new String[]{SHELL_COMMAND_WINDOW,"/c",command}; //$NON-NLS-1$
        }else
        {
            return new String[]{SHELL_COMMAND_LINUX,"-c",command}; //$NON-NLS-1$
        }
    }

    public static boolean batchExecute(String command)  
    {
        String[] fullCommand = getCommand(command);

        Runtime run = Runtime.getRuntime();
          Process p = null;
          
          try{
           p = run.exec(fullCommand);
           StreamGobbler gb1 = new StreamGobbler(p.getInputStream());
           StreamGobbler gb2 = new StreamGobbler(p.getErrorStream());
           gb1.start();
           gb2.start();
          }catch(Exception e){
              e.printStackTrace();
              return false;
          }finally{
          } 
        return true;
    }

    /**
     * Don't need for emultor install registration.
     * using java.lang.Runtime.exec(String[] cmdarray, String[] envp, File dir) 
     * @param command  - array containing the command to call and its arguments.
     * @param envp - array of strings, each element of which has environment variable settings in format name=value.
     * @param dir - Emultor path.
     * @return success true, failed false.
     */
    public static boolean batchExecute(String command, String[] envp, File dir) 
    {
        String[] fullCommand = getCommand(command);

        Runtime run = Runtime.getRuntime();
          Process p = null;

          StreamGobbler gb1 = null;
          StreamGobbler gb2 = null;
          try{
           p = run.exec(fullCommand, envp, dir);
           gb1 = new StreamGobbler(p.getInputStream());
           gb2 = new StreamGobbler(p.getErrorStream());
           gb1.start();
           gb2.start();
          }catch(Exception e){
              e.printStackTrace();
              return false;
          }finally{
              System.out.println(gb1.getResult());
              System.out.println(gb2.getResult());
          } 
        return true;
    }

}
