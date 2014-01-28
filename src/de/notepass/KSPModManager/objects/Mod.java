package de.notepass.KSPModManager.objects;

import de.notepass.KSPModManager.InternalConfig;
import de.notepass.KSPModManager.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Mod {
    //In this object a single mod is saved

    //Variable declaration + Content Fallback in case of no content
    private String name = "N/A";
    private String version = "N/A";
    UUID ident = UUID.randomUUID();
    ArrayList<String> dllFiles = new ArrayList<String>();
    ArrayList<String> partFiles = new ArrayList<String>();


    public Mod(String name, String version) {
        this.setName(name);
        this.setVersion(version);
    }

    public Mod(String name) {
        this.setName(name);
    }

    public Mod() {

    }

    public void setName(String name) {
        //Check if the Name String is not null. So this will never Return a null String
        if (name != null) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void setVersion(String version) {
        //Check if the Version String is not null. So this will never Return a null String
        if (version != null) {
            this.version = version;
        }
    }

    public String getVersion() {
        return version;
    }

    public UUID getIdent() {
        return ident;
    }

    private void setIdent(UUID ident) {
        if (ident != null) {
            this.ident = ident;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ArrayList<String> getDllFiles() {
        return dllFiles;
    }

    public void setDllFiles(ArrayList<String> dllFiles) {
        this.dllFiles = dllFiles;
    }

    public ArrayList<String> getPartFiles() {
        return partFiles;
    }

    public void setPartFiles(ArrayList<String> partFiles) {
        this.partFiles = partFiles;
    }

    public void saveToFile() throws ParserConfigurationException, TransformerException {
        //Create a documentBuilder instance
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        //Create an Document
        Document doc = db.newDocument();

        //Create a root-Element
        Element XMLmod = doc.createElement("mod");
        XMLmod.setAttribute("display",name);
        XMLmod.setAttribute("version",version);


        //Add the main Information to the root-element
        //All Dll-Files
        Element XMLdll = doc.createElement("dllFiles");
        for (int i=0;i<dllFiles.size();i++) {
            Element XMLdllFile = doc.createElement("dll_"+String.valueOf(i));
            XMLdllFile.appendChild(doc.createTextNode(dllFiles.get(i)));
            XMLdll.appendChild(XMLdllFile);
        }
        XMLmod.appendChild(XMLdll);

        //All Part-Files
        Element XMLPart = doc.createElement("partFiles");
        for (int i=0;i<partFiles.size();i++) {
            Element XMLPartFile = doc.createElement("part_"+String.valueOf(i));
            XMLPartFile.appendChild(doc.createTextNode(partFiles.get(i)));
            XMLPart.appendChild(XMLPartFile);
        }
        XMLmod.appendChild(XMLPart);


        //Add the root-element to the document
        doc.appendChild(XMLmod);

        //Save as XML-File
        //Transform from document to FileStream
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        DOMSource ds = new DOMSource(doc);
        StreamResult sr = new StreamResult(new File(giveFileName(ident)));

        //Save File to Disc
        trans.transform(ds,sr);
    }

    public static Mod loadFromFile(UUID ident) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        Mod readMod = new Mod();
        readMod.setName(Util.nodeListToString(Util.executeXPath(giveFileName(ident),InternalConfig.MOD_XPATH_NAME)));
        readMod.setVersion(Util.nodeListToString(Util.executeXPath(giveFileName(ident),InternalConfig.MOD_XPATH_VERSION)));
        ArrayList<String> dllFiles = new ArrayList<String>();
        int i=0;
        while (!Util.nodeListToString(Util.executeXPath(giveFileName(ident), InternalConfig.MOD_XPATH_DLL_REPETITION + String.valueOf(i))).equals("")) {
            dllFiles.add(Util.nodeListToString(Util.executeXPath(giveFileName(ident), InternalConfig.MOD_XPATH_DLL_REPETITION + String.valueOf(i)+"/text()")));
            i++;
        }
        ArrayList<String> partFiles = new ArrayList<String>();
        i=0;
        while (!Util.nodeListToString(Util.executeXPath(giveFileName(ident),InternalConfig.MOD_XPATH_PART_REPETITION +String.valueOf(i))).equals("")) {
            partFiles.add(Util.nodeListToString(Util.executeXPath(giveFileName(ident),InternalConfig.MOD_XPATH_PART_REPETITION +String.valueOf(i)+"/text()")));
            i++;
        }
        readMod.setDllFiles(dllFiles);
        readMod.setPartFiles(partFiles);
        return readMod;
    }

    public static Mod loadFromFile(String ident) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        return loadFromFile(UUID.fromString(ident));
    }

    private static String giveFileName(UUID ident) {
        return InternalConfig.MOD_CONFIG_ROOT_FOLDER +"/"+ident.toString()+".xml";
    }
}
