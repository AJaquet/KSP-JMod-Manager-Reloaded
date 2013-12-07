package de.notepass.general.util;

import de.notepass.general.logger.Log;
import de.notepass.general.objects.gui.GroupBox;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.notepass.general.internalConfig.InternalConfigDummy;

//The Util-Class of this project. You will find random stuff here...
public class Util implements Serializable {

    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    //This function can read the Config-File of the program
    public static String readConfig(String node) {
        try {
            return Util.nodeListToString(Util.executeXPath(InternalConfigDummy.configRoot+"/conf.xml","/config/"+node+"/text()"));
        } catch (Exception e) {
            Log.logError(e);
            Log.logError("Couldn't read the main Config file... Stopping...");
            System.exit(1);
        }
        return "";
    }

    //This function executes a XPath-Statement (From a file)
    public static NodeList executeXPath(String filepath, String xpath_exp) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        //This Function will execute an XPath Expression
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        //This Loads the File...
        Document doc = builder.parse(filepath);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        //... and this will execute the XPath
        XPathExpression expr = xpath.compile(xpath_exp);
        Object result = expr.evaluate(doc, XPathConstants.NODESET);

        //The Output is a NoteList
        return (NodeList) result;
    }

    public static String [] executeXPath(String [] filepath, String xpath_exp) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        String [] worker = new String[filepath.length];
        for (int i=0;i<filepath.length;i++) {
            worker[i]=nodeListToString(executeXPath(filepath[i], xpath_exp));
        }
        return worker;
    }

    //This function executes a XPath-Statement (From a NodeList)
    public static NodeList executeXPath(NodeList nl, String xpath_exp) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        //... and this will execute the XPath
        XPathExpression expr = xpath.compile(xpath_exp);
        Object result = expr.evaluate(nl, XPathConstants.NODESET);

        //The Output is a NoteList
        return (NodeList) result;
    }

    //This function converts an NodeList to a String
    public static String nodeListToString(NodeList nodeList) {
        String result = new String();
        //Read every Node and put it into the String
        for (int i = 0; i < nodeList.getLength(); i++) {
            result = result + nodeList.item(i).getNodeValue();
        }
        return result;
    }

    //Checks if a file exists
    public static boolean exist(String file) {
        File f = new File(file);
        return f.exists();
    }

    //List all Files in a given folder
    public static String[] listFiles(String path)
    {
        String files;
        File dir = new File(path);
        File[] files1 = dir.listFiles();

        String [] filesList = new String[files1.length];

        for (int i = 0; i < files1.length; i++) {
            if (files1[i].isFile()) {
                filesList[i] = files1[i].getName();
            }
        }

        return filesList;
    }

    //Deletes the File-Extension of a Filename
    public static String deleteFileExtension(String str) {
        int lastIndex = str.toLowerCase().lastIndexOf(".");
        char [] charArray = new char[str.length()];
        charArray = str.toCharArray();
        String ret = "";
        for (int i=0;i<lastIndex;i++) {
            ret = ret + charArray[i];
        }
        return ret;
    }

    //The raw translate routine. This is more an "abstract" routine. There are specialised methods in some classes (Mostly for replacing local variables)
    //The installer uses an other translation routine! Its in the Installer class!
    public static String translate(String id) {
        String langFile = readConfig("langFile");
        try {
            //Reads the Translation
            String worker = Util.nodeListToString(Util.executeXPath(InternalConfigDummy.langRoot+"/"+langFile,"/lang/"+id+"/text()"));
            worker = worker.replaceAll(Pattern.quote("\\r"),"\r");
            worker = worker.replaceAll(Pattern.quote("\\n"),"\n");
            return worker;
        } catch (Exception e) {
            Log.logError(e);
            Log.logWarning("Couldn't translate text with ID "+id+"... This is normaly an error in the language-file...");
        }
        return "Couldn't translate Text. See debug-console or log-file for details";
    }

    //Saves an Object to a file (Should just be used in Cases were you can't save a File as XML [Reason for Deprecated status])
    public static void saveObject(Object saveObject, String path) throws IOException {
        @Deprecated
  	    ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(path,false));
        o.writeObject(saveObject);
        o.close();
    }

    //Loads an Object from a file (Should just be used in Cases were you can't save a File as XML [Reason for Deprecated status])
    public static Object loadObject(String path) throws IOException, ClassNotFoundException {
        @Deprecated
        ObjectInputStream o = new ObjectInputStream(new FileInputStream(path));
        Object result = o.readObject();
        o.close();
        return result;
    }

    //Function to execute RegEx, gives back the first String that matches the RegEx
    public static String execRegEx(String source, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(source);

        if (m.find()) {
            return source.substring(m.start(), m.end());
        } else {
            return null;
        }
    }

    //Reads a Text-File and converts it to a String
    public static String textFileToString(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    //Executes a Shell-Command
    public static void shellExec(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    //Saves a String as Textfile
    public static void stringToTextfile(String file, String string) throws FileNotFoundException {
        PrintStream out = null;
        out = new PrintStream(new FileOutputStream(file));
        out.print(string);
        out.close();
    }

    //opens a file with it's associated application
    public static void openWithStandartApp(String file) throws IOException {
        Desktop.getDesktop().open(new File(file));
    }

    //Shows an Error Message
    public static void showError(String text, String title) {
        JOptionPane.showMessageDialog(new Frame(),translate("stdErrorPrefix")+text+translate("stdErrorSuffix"), title,JOptionPane.ERROR_MESSAGE);
    }

    //Shows an Error Message
    public static void showError(String text) {
        showError(text, translate("stdErrorTitle"));
    }

    public static void showError(Exception e, String title) {
        showError(exceptionToString(e),title);
    }

    public static void showError(Exception e) {
        showError(e,translate("stdErrorTitle"));
    }

    //This method shows only the given message (Only used when there is an initialisation error)
    public static void showPureError(String text, String title) {
        JOptionPane.showMessageDialog(new Frame(),text, title,JOptionPane.ERROR_MESSAGE);
    }

    public static void showPureError(String text) {
        showPureError(text,"Fatal Error");
    }

    public static String exceptionToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static void showWarning(String text, String title) {
        JOptionPane.showMessageDialog(new Frame(),translate("stdWarnPrefix")+text+translate("stdWarnSuffix"), title,JOptionPane.WARNING_MESSAGE);
    }

    public static void showInfo(String text, String title) {
        JOptionPane.showMessageDialog(new Frame(),translate("stdInfoPrefix")+text+translate("stdInfoSuffix"), title,JOptionPane.INFORMATION_MESSAGE);
    }


    //Executes an Method form an external Java-Application
    public static Object execMethod(File AjarFile, String sourceClass, String method, Class<?> [] parameterTypes, Object [] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //Load .jar Files
        String jarFilePath=AjarFile.getAbsolutePath();

        JarFile jarFile = new JarFile( jarFilePath/*path*/);
        Enumeration e = jarFile.entries();

        URL[] urls = {new URL("jar:file:"+jarFilePath+"!/")};
        URLClassLoader c1 = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace("/",".");
            Class<?> endClass = c1.loadClass(className);
            if (className.equals(sourceClass)) {
                Method m1 = endClass.getDeclaredMethod(method,parameterTypes);
                return m1.invoke(endClass.newInstance(),args);
            }
        }
        return null;
    }

    public static String readTextFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder();
        Scanner scanner = new Scanner(file);
        String lineSeparator = Util.getLineSeparator();

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    public static void writeTextFile(String content, File target, boolean append) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter( new FileWriter(target.getAbsolutePath(),append));
            writer.write(content);
        }
        finally
        {
            if ( writer != null)
                writer.close( );
        }
    }

    //Increase the Build-Count if it's "my" Developer Version
    public static void increaseBuild() {
        if (InternalConfigDummy.versionType.equals(InternalConfigDummy.versionTypeUnreleased)) {
            int build = 0;
            String builds = new String();
            try {
                builds = Util.readTextFile(InternalConfigDummy.rootFolder+"/build").trim();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            build = Integer.valueOf(builds);
            build++;
            try {
                Util.writeTextFile(String.valueOf(build),new File(InternalConfigDummy.rootFolder+"/build"),false);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            Log.logDebug("Neue Build-Nummer: "+String.valueOf(build));
        }
    }

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    //Resolves an XPath value to a File (eg.: Value: EUR, XPath: /currency/ISO4217, Search Folder: data/currnecy) would resolve to euro.xml
    public static String xmlFileToXpathExpression(String xpath, String value, String searchFolder) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        String [] searchFiles = Util.listFiles(searchFolder);
        for (int i=0; i<searchFiles.length; i++) {
            if (Util.nodeListToString(Util.executeXPath(searchFolder+"/"+searchFiles[i],xpath)).equals(value)) {
                return searchFiles[i];
            }
        }
        return "";
    }

    //Formats a load String so it is valid
    public static String createLoadString(String path) {
        URL url = GroupBox.class.getResource(path);
        return url.toExternalForm();
    }

    //Get the REAL screen Size
    public static Dimension getScreenSize() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = ge.getMaximumWindowBounds();
        return bounds.getSize();
    }

}
