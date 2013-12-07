package de.notepass.KSPModManager;

import de.notepass.KSPModManager.gui.SwingGui;
//TODO: Delete Test-Comments
//TODO: Put in comments (Lang.: English)
//TODO: Add method to set value of labels that will be updated

//TODO: [notepass] !IMPORTANT! Add XML-Lang Files (Working with XPath) -- @saph: I think i will do this ;)


public class KSPModManager {
    /**
     * Main routine
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println("KSP JModManager by notepass and Saphirian");
        System.out.println("");
        System.out.println("==Debug Console==");

        SwingGui.printInstalledLookAndFeels();

        SwingGui.setGUILookAndFeel();

        System.out.println(SwingConfig.CONSOLE_PREFIX + "Initiating GUI");
        SwingGui.initGUIWithProperties();
    }
}