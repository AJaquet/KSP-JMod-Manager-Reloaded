package de.notepass.general.logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.notepass.general.internalConfig.GeneralConfig;
import de.notepass.general.util.Util;

//This is the Log Class. It contains every log function
public class Log {

    //With this function the Configuration is read
    private static String readConfig(String part) {
        try {
            //Reading an XML-String via XPath
            return Util.nodeListToString(Util.executeXPath(GeneralConfig.logConfPath, GeneralConfig.logXMLlog_config + part + "/text()"));
        } catch (Exception e) {
            //If there is an Error in the Config, the Programm will give a StackTrace and an Easy to undestand error Message for the End-User
            e.printStackTrace();
            System.out.println("Error: Couldn't read the log-Config. Will now exit...");
            Util.showPureError("Error: Couldn't read the log-Config. Will now exit..."+Util.getLineSeparator()+"Stacktrace:"+Util.getLineSeparator()+Util.getLineSeparator()+Util.exceptionToString(e));
            //And then Terminate
            System.exit(1);
        }
        //This Case will never happen (The programm can just exit or give back the first return)
        return "";
    }

    //This function gives out the Formatted log-Time-Format
    private static String giveLogTimeFormatted() {
        Date logTime = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat();
        //The pattern for output is read from the config-File
        formatDate.applyPattern(readConfig(GeneralConfig.logXMLdateTimeFormat));
        //And there it goes. As String of course
        return readConfig(GeneralConfig.logXMLdateTimePrefix)+formatDate.format(logTime)+readConfig(GeneralConfig.logXMLdateTimeSuffix);
    }

    //This function will bring a logges text into an Textfile
    private static void logTextToFile(String text) {
        //Declare Variables
        FileOutputStream logFileStream = null;
        //reads the path for the output file from the Config
        String filePath = readConfig(GeneralConfig.logXMLpath);
        //Add an break to the Text, so it breaks lines in the textfile
        text = text + "\r\n";
        try {
            //This is a standard output procedure...
            logFileStream = new FileOutputStream(filePath, true);
            for (int i=0; i<text.length(); i++) {
                logFileStream.write((byte) text.charAt(i));
            }
        } catch (FileNotFoundException e) {
            //If the file doesn't exist, we try to create it
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException ex) {
                //And if it doesn't work, we give out an STacktrace and an understandable error Message
                ex.printStackTrace();
                System.out.println("Error: Couldn't open the log-File. Will now exit...");
                Util.showPureError("Error: Couldn't open the log-File. Will now exit..."+Util.getLineSeparator()+"Stacktrace:"+Util.getLineSeparator()+Util.getLineSeparator()+Util.exceptionToString(e));
                System.exit(1);
            }
        } catch (IOException e) {
            //If the file can't be open for another reason, we will give out a StackTrace and an understandable error message
            e.printStackTrace();
            System.out.println("Error: Couldn't open the log-File. Will now exit...");
            Util.showPureError("Error: Couldn't open the log-File. Will now exit..."+Util.getLineSeparator()+"Stacktrace:"+Util.getLineSeparator()+Util.getLineSeparator()+Util.exceptionToString(e));
            System.exit(1);
        } finally {
            try {
                //Whatever happen, close the Stream...
                logFileStream.close();
            } catch (IOException ex) {
                //Except when there is an error... (Jup, this totaly doesn't make any sense...)
                ex.printStackTrace();
                System.out.println("Error: Couldn't open the log-File. Will now exit...");
                Util.showPureError("Error: Couldn't open the log-File. Will now exit..."+Util.getLineSeparator()+"Stacktrace:"+Util.getLineSeparator()+Util.getLineSeparator()+Util.exceptionToString(ex));
                System.exit(1);
            }
        }
    }

    //The function to log an Debug-Message
    public static void logDebug(String logText) {
        //Does the config tell us to save it?
        if (readConfig(GeneralConfig.logXMLlogDebug).toLowerCase().equals("true")) {
            //If yes, we will output it in the Shell and the log-File
            System.out.println(giveLogTimeFormatted() + readConfig(GeneralConfig.logXMLlogDebugText) + logText);
            logTextToFile(giveLogTimeFormatted() + readConfig(GeneralConfig.logXMLlogDebugText) + logText);
        }
    }

    //The function to log an Info-Message
    public static void logInfo(String logText) {
        //Does the config tell us to save it?
        if (readConfig(GeneralConfig.logXMLlogInfo).toLowerCase().equals("true")) {
            //If yes, we will output it in the Shell and the log-File
            System.out.println(giveLogTimeFormatted() + readConfig(GeneralConfig.logXMLlogInfoText) + logText);
            logTextToFile(giveLogTimeFormatted() + readConfig(GeneralConfig.logXMLlogInfoText) + logText);
        }
    }

    //This function logs a warning-message
    public static void logWarning(String logText) {
        //Should we save it?
        if (readConfig(GeneralConfig.logXMLlogWarn).toLowerCase().equals("true")) {
            //If yes, the output goes to the Shell and the File
            System.out.println(giveLogTimeFormatted() + readConfig(GeneralConfig.logXMLlogWarnText) + logText);
            logTextToFile(giveLogTimeFormatted() + readConfig(GeneralConfig.logXMLlogWarnText) + logText);
        }
    }

    public static void logInfo(String [] logText) {
        if (readConfig(GeneralConfig.logXMLlogInfo).toLowerCase().equals("true")) {
            logInfo(logText[0]);
            for (int i=1; i<logText.length; i++) {
                System.out.println("\t" + readConfig(GeneralConfig.logXMLlogInfoText) + logText);
                logTextToFile("\t" + readConfig(GeneralConfig.logXMLlogInfoText) + logText);
            }
        }
    }

    public static void logWarning(String [] logText) {
        if (readConfig(GeneralConfig.logXMLlogWarn).toLowerCase().equals("true")) {
            logWarning(logText[0]);
            for (int i=1; i<logText.length; i++) {
                System.out.println("\t" + readConfig(GeneralConfig.logXMLlogWarnText) + logText);
                logTextToFile("\t" + readConfig(GeneralConfig.logXMLlogWarnText) + logText);
            }
        }
    }

    //This is the logger for the Error-Messages
    public static void logError(String logText) {
        //Should we save them?
        if (readConfig(GeneralConfig.logXMLlogError).toLowerCase().equals("true")) {
            //If yes, put the Text in the Shell and the File
            System.out.println(giveLogTimeFormatted() + readConfig(GeneralConfig.logXMLlogErrorText) + logText);
            logTextToFile(giveLogTimeFormatted() + readConfig(GeneralConfig.logXMLlogErrorText) + logText);
        }
    }

    public static void logError(String [] logText) {
        if (readConfig(GeneralConfig.logXMLlogError).toLowerCase().equals("true")) {
            logError(logText[0]);
            for (int i=1; i<logText.length; i++) {
                System.out.println("\t" + readConfig(GeneralConfig.logXMLlogErrorText) + logText);
                logTextToFile("\t" + readConfig(GeneralConfig.logXMLlogErrorText) + logText);
            }
        }
    }

    public static void logError(Exception ex) {;
        logError(Util.exceptionToString(ex));
    }

}
