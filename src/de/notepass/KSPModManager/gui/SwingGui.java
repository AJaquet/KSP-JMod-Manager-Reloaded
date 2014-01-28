package de.notepass.KSPModManager.gui;

import de.notepass.KSPModManager.ButtonListener;
import de.notepass.KSPModManager.SwingConfig;
import de.notepass.KSPModManager.KSPModManager;
import de.notepass.KSPModManager.InternalConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SwingGui {
    static JFrame window;
    static JTabbedPane paneMain;
    static JPanel tabMain;
    static JPanel tabManage;
    static JPanel tabSavegameManager;
    static JPanel tabProfiles;
    static JPanel tabConfig;
    static JPanel tabInfo;
    static JList<String> listMods;
    static JLabel lblListMods;
    static JList<String> listModifiedDLLS;
    static JList<String> listModifiedParts;
    static JSeparator tabMainSeparatorVertical;
    static JSeparator tabMainSeparatorHorizontal;
    static JLabel lblListModifiedDLLS;
    static JLabel lblListModifiedParts;
    static JButton btnReloadTabMain;
    static JTextPane paneTextTabInfo;
    static JLabel lblModVersion;
    static JLabel lblKSPVersion;
    static JLabel lblInstalled;
    static JButton btnInstallTabMain;
    static JButton btnRemoveTabMain;

    /**
     * Sets the visual theme of the GUI components.
     */
    public static void setGUILookAndFeel()
    {
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Windows".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    System.out.println(SwingConfig.CONSOLE_PREFIX + "Using Style " + info.getName() + " (" +info.getClassName()+ ")");
                    break;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Prints a list of installed themes to the console.
     * TODO: Merge with setGUILookAndFeel()
     */
    public static void printInstalledLookAndFeels()
    {
        System.out.println(SwingConfig.CONSOLE_PREFIX + "======Installed Styles======");
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                System.out.print(SwingConfig.CONSOLE_PREFIX + info.getName());
                System.out.println(" ("+info.getClassName()+")");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(SwingConfig.CONSOLE_PREFIX + "============================");
    }

    /**
     * Initiates the GUI and sets the properties of every component
     */
    public static void initGUIWithProperties()
    {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(700, 550);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setAlwaysOnTop(false);
        window.setTitle("KSP Mod-Manager: V. " + ""/*TODO: Add Version*/);
        window.setLayout(null);
        window.setIconImage(loadImageFromJar("icon.png"));

        paneMain = new JTabbedPane();
        paneMain.setBounds(0, 0, window.getWidth(), window.getHeight());

        // - Tab: Modliste - //

        tabMain = new JPanel(null);

        listMods = new JList<String>(); // TODO: Add array
        listMods.setBounds(20, 30, 300, 400);
        listMods.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        listMods.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabMain.add(listMods);

        listModifiedDLLS = new JList<String>(); // TODO: Add array
        listModifiedDLLS.setBounds(345, 30, 160, 200);
        listModifiedDLLS.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        listModifiedDLLS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabMain.add(listModifiedDLLS);

        listModifiedParts = new JList<String>(); // TODO: Add array
        listModifiedParts.setBounds(520, 30, 160, 200);
        listModifiedParts.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        listModifiedParts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabMain.add(listModifiedParts);

        btnReloadTabMain = new JButton("Reload");
        btnReloadTabMain.setBounds(20, 450, 300, 25);
        btnReloadTabMain.setToolTipText(SwingConfig.TOOLTIP_BTN_RELOAD);
        btnReloadTabMain.addActionListener(new ButtonListener(0));
        btnReloadTabMain.setIcon(new ImageIcon(loadImageFromJar("iconRefresh.png")));
        tabMain.add(btnReloadTabMain);

        btnInstallTabMain = new JButton(SwingConfig.BTN_INSTALL);
        btnInstallTabMain.setBounds(345, 360, 335, 25);
        btnInstallTabMain.setToolTipText(SwingConfig.TOOLTIP_BTN_INSTALL);
        btnInstallTabMain.addActionListener(new ButtonListener(1));
        tabMain.add(btnInstallTabMain);

        btnRemoveTabMain = new JButton(SwingConfig.BTN_REMOVE);
        btnRemoveTabMain.setBounds(345, 390, 335, 25);
        btnRemoveTabMain.setToolTipText(SwingConfig.TOOLTIP_BTN_REMOVE);
        btnRemoveTabMain.addActionListener(new ButtonListener(2));
        tabMain.add(btnRemoveTabMain);

        lblListMods = new JLabel(SwingConfig.LBL_LISTMODS);
        lblListMods.setBounds(20, 15, (int) lblListMods.getPreferredSize().getWidth(), (int) lblListMods.getPreferredSize().getHeight());
        tabMain.add(lblListMods);

        lblListModifiedDLLS = new JLabel(SwingConfig.LBL_LISTMODIFIEDDLLS);
        lblListModifiedDLLS.setBounds(345, 15, (int) lblListModifiedDLLS.getPreferredSize().getWidth(), (int) lblListModifiedDLLS.getPreferredSize().getHeight());
        tabMain.add(lblListModifiedDLLS);

        lblListModifiedParts = new JLabel(SwingConfig.LBL_LISTMODIFIEDPARTS);
        lblListModifiedParts.setBounds(520, 15, (int) lblListModifiedParts.getPreferredSize().getWidth(), (int) lblListModifiedParts.getPreferredSize().getHeight());
        tabMain.add(lblListModifiedParts);

        lblModVersion = new JLabel(SwingConfig.LBL_MODVERSION.replace(SwingConfig.PLACEHOLDER_VERSION, SwingConfig.LBL_MODVERSION_DEFAULT));
        lblModVersion.setBounds(345, 250, (int) lblModVersion.getPreferredSize().getWidth(), (int) lblModVersion.getPreferredSize().getHeight());
        tabMain.add(lblModVersion);

        lblKSPVersion = new JLabel(SwingConfig.LBL_KSPVERSION.replace(SwingConfig.PLACEHOLDER_VERSION, SwingConfig.LBL_KSPVERSION_DEFAULT));
        lblKSPVersion.setBounds(345, 280, (int) lblKSPVersion.getPreferredSize().getWidth(), (int) lblKSPVersion.getPreferredSize().getHeight());
        tabMain.add(lblKSPVersion);

        lblInstalled = new JLabel(SwingConfig.LBL_INSTALLED.replace(SwingConfig.PLACEHOLDER_FLAG, SwingConfig.LBL_INSTALLED_DEFAULT));
        lblInstalled.setBounds(345, 310, (int) lblInstalled.getPreferredSize().getWidth(), (int) lblInstalled.getPreferredSize().getHeight());
        tabMain.add(lblInstalled);

        tabMainSeparatorVertical = new JSeparator(SwingConstants.VERTICAL);
        tabMainSeparatorVertical.setBounds(332, 0, 2, window.getHeight());
        tabMain.add(tabMainSeparatorVertical);

        tabMainSeparatorHorizontal = new JSeparator(SwingConstants.HORIZONTAL);
        tabMainSeparatorHorizontal.setBounds(332, 340, window.getWidth() - 341, 2);
        tabMain.add(tabMainSeparatorHorizontal);

        paneMain.addTab(SwingConfig.TAB_MAIN, tabMain);
        paneMain.setToolTipTextAt(0, SwingConfig.TOOLTIP_TAB_MAIN);

        // - Tab: Verwalten - //

        tabManage = new JPanel(null);
        paneMain.addTab(SwingConfig.TAB_MANAGE, tabManage);
        paneMain.setToolTipTextAt(1, SwingConfig.TOOLTIP_TAB_MANAGE);

        // - Tab: Savegame Manager - //

        tabSavegameManager = new JPanel(null);
        paneMain.addTab(SwingConfig.TAB_SAVEGAMEMANAGER, tabSavegameManager);
        paneMain.setToolTipTextAt(2, SwingConfig.TOOLTIP_TAB_SAVEGAMEMANAGER);

        // - Tab: Profile - //

        tabProfiles = new JPanel(null);
        paneMain.addTab(SwingConfig.TAB_PROFILES, tabProfiles);
        paneMain.setToolTipTextAt(3, SwingConfig.TOOLTIP_TAB_PROFILES);

        // - Tab: Konfigurieren - //

        tabConfig = new JPanel(null);
        paneMain.addTab(SwingConfig.TAB_CONFIG, tabConfig);
        paneMain.setToolTipTextAt(4, SwingConfig.TOOLTIP_TAB_CONFIG);

        // - Tab: Information - //

        tabInfo = new JPanel(null);

        paneTextTabInfo = new JTextPane();
        paneTextTabInfo.setBounds(20, 20, 650, 455);
        paneTextTabInfo.setEditable(false);
        paneTextTabInfo.setBackground(null);
        paneTextTabInfo.setText(String.format(loadTextFileFromJar("information.txt"), ""/*TODO: Add Version*/, "0000-00-00" /*TODO: Add date*/));
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (int i = 0; i < fonts.length; i++)
        {
            if (fonts[i].equals("Verdana"))
            {
                paneTextTabInfo.setFont(new Font("Verdana", Font.PLAIN, 9));
                break;
            }
        }
        tabInfo.add(paneTextTabInfo);

        paneMain.addTab(SwingConfig.TAB_INFO, tabInfo);
        paneMain.setToolTipTextAt(5, SwingConfig.TOOLTIP_TAB_INFO);

        // - - - //

        window.add(paneMain);

        window.setVisible(true);
    }

    /**
     * Reads a textfile from the jarfile.
     * @param filename
     * @return Content of the textfile as String
     */
    private static String loadTextFileFromJar(String filename)
    {
        InputStream fileStream = KSPModManager.class.getClassLoader().getResourceAsStream(filename);
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(fileStream));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null)
            {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            br.close();
            String fileLoaded = sb.toString();
            return fileLoaded;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                fileStream.close();
                br.close();
            } catch (Exception e_)
            {
                e_.printStackTrace();
            }
        }
        return SwingConfig.ERROR_TEXTFILE;
    }

    /**
     * Reads an image (preferably .png) from the jarfile.
     * @param filename
     * @return Image
     */
    public static Image loadImageFromJar(String filename)
    {
        Image icon = window.getIconImage(); // Placeholder image
        try
        {
            icon = ImageIO.read(KSPModManager.class.getClassLoader().getResource(filename));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return icon;
    }
}
