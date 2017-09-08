
package browser;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class BrowserClass{
 
    // declaration
    JFrame fMain;
    JTextField addressBar;
    JPanel btnPanel;
    JEditorPane displayPage;
    JButton backBtn, forwardBtn, homeBtn, refreshBtn, historyBtn, favoriteBtn, searchBtn, goBtn, settingBtn;
    
    Dimension btnDim, addressBarDim, displayPageDim;
    
    // stack for forward and back Butttons
    Stack<String> s_back, s_forward;
    
    // menus declaration
    
    JPopupMenu homeBtnPopup;
    JPopupMenu historyParentMenu;
    JPopupMenu favoriteParentMenu;
    JPopupMenu settingsParentMenu;
    
    JMenuItem homeBtnItem1 = null;
    JMenuItem homeBtnItem2 = null;
    
    // array for display of list elements
    String homePage;
    ArrayList<JMenuItem> historyArray = new ArrayList<>();
    ArrayList<JMenuItem> favoriteArray = new ArrayList<>();
    ArrayList<String> firewallArray = new ArrayList<>();
    
    DateFormat df;
    Date dateObj;
    
    boolean firewall = true;
    boolean firewalToggle = false;
    
    public BrowserClass(){
        //super("Browser");
        initProcess();
    }
    
    public void initProcess(){
        GUI();
        Functions();
        
    }
    
    private void GUI(){
        setdesign();
        declarationOfComponents();
        setSizeAndPositionOfComponents();
        // combining all components in one component
        addComponentsToContainer();
        
        fMain.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fMain.setVisible(true);
        fMain.setResizable(false);
        fMain.setSize(1280, 650);
    }
    
    private void Functions() {
        // function for displaying text when i place mouse over it
        
        mouseListenerForBtnHover();
        hyperLinkListenerForPage();
    }
    
    private void declarationOfComponents(){
        
        fMain = new JFrame("Browser");
        btnDim = new Dimension(35, 35);
        addressBarDim = new Dimension(900, 35);
        displayPageDim = new Dimension(1270, 563);
        
        addressBar = new JTextField(null);
        displayPage = new JEditorPane();
        displayPage.setText(null);
        btnPanel = new JPanel();
        
        s_back = new Stack<String>();
        s_forward = new Stack<String>();
    
        df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateObj = new Date();
        
        try {
            Image BackImg = ImageIO.read(getClass().getResource("icons//BackBtn.png"));
            ImageIcon icon = new ImageIcon(BackImg);
            backBtn = new JButton(icon);
        } 
        catch (IOException ex) {
        }
        
        try {
            Image forwardImg = ImageIO.read(getClass().getResource("icons//ForwardBtn.png"));
            ImageIcon icon = new ImageIcon(forwardImg);
            forwardBtn = new JButton(icon);
        } 
        catch (IOException ex) {
        }
        
        try {
            Image homeImg = ImageIO.read(this.getClass().getResource("icons//HomeBtn.png"));
            ImageIcon icon = new ImageIcon(homeImg);
            homeBtn = new JButton(icon);
        } 
        catch (IOException ex) {
        }
                
        try {
            Image refreshImg = ImageIO.read(getClass().getResource("icons//ReloadBtn.png"));
            ImageIcon icon = new ImageIcon(refreshImg);
            refreshBtn = new JButton(icon);
        } 
        catch (IOException ex) {
        }
        
        try {
            Image historyImg = ImageIO.read(getClass().getResource("icons//HistoryBtn.png"));
            ImageIcon icon = new ImageIcon(historyImg);
            historyBtn = new JButton(icon);
        } 
        catch (IOException ex) {
        }
        
        try {
            Image favoriteImg = ImageIO.read(getClass().getResource("icons//FavoriteBtn.png"));
            ImageIcon icon = new ImageIcon(favoriteImg);
            favoriteBtn = new JButton(icon);
        } 
        catch (IOException ex) {
        }
        
        try {
            Image searchImg = ImageIO.read(getClass().getResource("icons//SearchBtn.png"));
            ImageIcon icon= new ImageIcon(searchImg);
            searchBtn = new JButton(icon);
        } 
        catch (IOException ex) {
        }
        
        try {
            Image goImg = ImageIO.read(getClass().getResource("icons//GoBtn.png"));
            ImageIcon icon = new ImageIcon(goImg);
            goBtn = new JButton(icon);
        } 
        catch (IOException ex) {
        }
        
        
//        settingBtn = new JButton();
        try {
            Image settingImg = ImageIO.read(getClass().getResource("icons//SettingBtn.png"));
            ImageIcon icon = new ImageIcon(settingImg);
            settingBtn = new JButton(icon);
        } 
        catch (IOException ex) {
        }
        
    }
    
    private void setSizeAndPositionOfComponents(){
        // address bar
        addressBar.setPreferredSize(addressBarDim);
        
        // buttons
        backBtn.setPreferredSize(btnDim);
        if(s_back.empty()){
                backBtn.setEnabled(false);
        }
        
        forwardBtn.setPreferredSize(btnDim);
        if(s_back.empty()){
                forwardBtn.setEnabled(false);
        }
        
        refreshBtn.setPreferredSize(btnDim);
        homeBtn.setPreferredSize(btnDim);
        historyBtn.setPreferredSize(btnDim);
        favoriteBtn.setPreferredSize(btnDim);
        searchBtn.setPreferredSize(btnDim);
        goBtn.setPreferredSize(btnDim);
        settingBtn.setPreferredSize(btnDim);
        // page
        displayPage.setPreferredSize(displayPageDim);
        displayPage.setEditable(false);
         
    }
    
    // function for displaying text when i place mouse over it
    private void mouseListenerForBtnHover() {
        MouseHandler mha = new MouseHandler(this);
        backBtn.addMouseListener(mha);
        forwardBtn.addMouseListener(mha);
        refreshBtn.addMouseListener(mha);
        addressBar.addMouseListener(mha);
        goBtn.addMouseListener(mha);
        homeBtn.addMouseListener(mha);
        historyBtn.addMouseListener(mha);
        favoriteBtn.addMouseListener(mha);
        searchBtn.addMouseListener(mha);
        settingBtn.addMouseListener(mha);
        //list.addMouseListener(mha);
    }
    
    private void hyperLinkListenerForPage() {
        
        //System.out.println("hyperLinkListenerForPage called");
        HyperlinkHandler hh = new HyperlinkHandler(this);
        displayPage.addHyperlinkListener(hh);
        //System.out.println("hyperLinkListenerForPage called end");
    }
    
    // combining all components in one component
    private void addComponentsToContainer(){
        // adding components
        
        Container container = fMain.getContentPane();
        container.setLayout(new FlowLayout());

        btnPanel.add(backBtn);
        btnPanel.add(forwardBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(addressBar);
        btnPanel.add(goBtn);
        btnPanel.add(homeBtn);
        btnPanel.add(historyBtn);
        btnPanel.add(favoriteBtn);
        btnPanel.add(searchBtn);
        btnPanel.add(settingBtn);
        
        container.add(btnPanel);
        container.add(displayPage);
        container.add(new JScrollPane(displayPage), BorderLayout.CENTER);
    }

    private void setdesign(){
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
//                if("Windows".equals(info.getName())){
//                    UIManager.setLookAndFeel(info.getClassName());
//                }
//            }
        } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BrowserClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public void updateBtnSimulation() {
        if(s_forward.isEmpty()){   // check if stack is empty afer poping out URL
            forwardBtn.setEnabled(false);
        }
        else {
            forwardBtn.setEnabled(true);
        }
        if(s_back.isEmpty()){
            backBtn.setEnabled(false);
        }
        else {
            backBtn.setEnabled(true);
        }
    }
}