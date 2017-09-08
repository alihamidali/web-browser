
package browser;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.MOUSE_ENTERED;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class MouseHandler extends MouseAdapter{

    BrowserClass browser;
    
    MouseHandler(BrowserClass _browser){
        browser = _browser;
    }
    @Override
    public void mouseEntered(MouseEvent me){
        if(me.getID() == MOUSE_ENTERED){
            browser.backBtn.setToolTipText("Back");
            browser.forwardBtn.setToolTipText("Forward");
            browser.refreshBtn.setToolTipText("Refresh");
            browser.addressBar.setToolTipText("Address Bar");
            browser.goBtn.setToolTipText("Go");
            browser.homeBtn.setToolTipText("Home");
            browser.historyBtn.setToolTipText("History");
            browser.favoriteBtn.setToolTipText("Favorite");
            browser.searchBtn.setToolTipText("Search");
            browser.settingBtn.setToolTipText("Show Settings");
        }
    }
    
    @Override
    public void mousePressed(MouseEvent me){
        if(me.getSource().equals(browser.backBtn)){
                backBtnAction();
        }
        else if(me.getSource().equals(browser.forwardBtn)){
                forwardBtnAction();
        }
        else if(me.getSource().equals(browser.refreshBtn)){
            refreshBtnAction();
        }
        else if(me.getSource().equals(browser.goBtn)){
            goBtnAction();
        }
        else if(me.getSource().equals(browser.homeBtn)){
            homeBtnAction();
            browser.homeBtnPopup.show(me.getComponent(), 0, 35);
        }
        else if(me.getSource().equals(browser.favoriteBtn)){
            favoriteBtnAction();
            browser.favoriteParentMenu.show(me.getComponent(), 0, 35);
        }
        else if(me.getSource().equals(browser.historyBtn)){
            historyBtnAction();
            browser.historyParentMenu.show(me.getComponent(), 0, 35);
        }
        else if(me.getSource().equals(browser.searchBtn)){
            searchBtnAction();
        }
        else if(me.getSource().equals(browser.settingBtn)){
            settingsBtnAction();
            browser.settingsParentMenu.show(me.getComponent(), 0, 35);
        }
    }
    
    public void homeBtnAction(){
        
        // home menu
        browser.homeBtnPopup = new JPopupMenu();
        
        // items for home menu and their actions
        browser.homeBtnItem1 = new JMenuItem(new AbstractAction("Set Current page as Home Page") {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                savePage("home");
            }
        });
        browser.homeBtnItem2 = new JMenuItem(new AbstractAction("Show Home Page") {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                String currURL = browser.addressBar.getText();
                readFile("homePage");
                loadPage(currURL, "home");
            }
        });

        // add items to home menu
        browser.homeBtnPopup.add(browser.homeBtnItem1);
        browser.homeBtnPopup.add(browser.homeBtnItem2);
    }
    
    // history button menu
    public void historyBtnAction(){
        browser.historyParentMenu = new JPopupMenu();
        
        //  sub menu
        JMenu historyListMenu = new JMenu("Show History");
        readFile("history");
        if(!browser.historyArray.isEmpty()){
            
            for(JMenuItem n : browser.historyArray){
                String temp = n.getText();
                n.setAction(new AbstractAction(temp) {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        String listURL = n.getText();
                        listURL = listURL.substring(18);
                        loadPage(listURL, "listURL");
                    }
                });
                historyListMenu.add(n);
            }
            
        }
        else {
            historyListMenu.add("No history");
        }
        browser.historyParentMenu.add(historyListMenu);
        browser.historyParentMenu.addSeparator();
        
        // item 1
        JMenuItem historyBtnItem1 = new JMenuItem(new AbstractAction("Clear History") {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    clearFile("history");        
                }
            }
        });
        browser.historyParentMenu.add(historyBtnItem1);
    }
    
    // favorite button menu
    public void favoriteBtnAction(){
        browser.favoriteParentMenu = new JPopupMenu();
        
        // item 1
        JMenuItem favoriteItem1 = new JMenuItem(new AbstractAction("Set Current URL as Favorite") {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                savePage("favorite");
            }
        });
        browser.favoriteParentMenu.add(favoriteItem1);
        
        //  item 2 | sub menu
        JMenu favoriteListMenu = new JMenu("Show Favorite");
        readFile("favorite");
        if(!browser.favoriteArray.isEmpty()){
            for(JMenuItem n : browser.favoriteArray){
                String temp = n.getText();
                n.setAction(new AbstractAction(temp) {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        String listURL = n.getText();
                        listURL = listURL.substring(18);
                        loadPage(listURL, "listURL");
                    }
                });
                favoriteListMenu.add(n);
            }
        }
        else{
            favoriteListMenu.add("No favorite saved");
        }
        browser.favoriteParentMenu.add(favoriteListMenu);
        browser.favoriteParentMenu.addSeparator();
        
        JMenuItem favoriteItem3 = new JMenuItem(new AbstractAction("Clear Favorite"){
                    
            @Override
            public void actionPerformed(ActionEvent ae) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    clearFile("favorite");
                }
            }
        });
        browser.favoriteParentMenu.add(favoriteItem3);
    }
    
    private void backBtnAction() {
        System.out.println("back button called");
        String currURL = browser.displayPage.getPage().toString();
        System.out.println("current URL is " + currURL);
        loadPage(currURL, "back");
        System.out.println("back button called end");
    }

    private void forwardBtnAction() {
        System.out.println("forward button called");
        String currURL = browser.displayPage.getPage().toString();
        System.out.println("current URL is " + currURL);
        loadPage(currURL, "forward");
        System.out.println("forward button called end");
    }
    
    private void refreshBtnAction() {
        System.out.println("refresh button called");
        String currURL = browser.addressBar.getText();
        loadPage(currURL, "refresh");
        System.out.println("refresh button called end");
    }
    
    private void goBtnAction() {
        String currURL = browser.addressBar.getText();
        if(browser.firewall){
            System.out.println("firewall exeption");
            if(!browser.firewallArray.contains(currURL)){
                loadPage(currURL, "go");
            }
            else {
                showError("Firewall Exception");
            }
        }
//        if(browser.firewall){
//            for(String n: browser.firewallArray){
//                if(_URL.contains(n)){
//                    browser.firewalToggle = true;
//                    break;
//                }
//            }
//        }
        else{
            System.out.println("firewall disable");
            loadPage(currURL, "go");
        }
    }
    
    private void savePage(String str) {
        String time = browser.df.format(browser.dateObj);
       
        String URL = browser.addressBar.getText();
        if(!URL.isEmpty() && URL.startsWith("https://")){
            File fWrite = null;
            BufferedWriter fileOut;
            // for saving home page 
            if(str.equals("home")){
                try {
                    fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\homePage.txt");
                    fileOut = new BufferedWriter(new FileWriter(fWrite));
                    fileOut.write("");
                    fileOut.write(URL);
                    fileOut.newLine();
                    fileOut.close();
                    JOptionPane.showMessageDialog(null, "Successfully saved", "Home Page Dialog box", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(MouseHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (str.equals("favorite")){
                
                try {
                    fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\favorite.txt");
                    fileOut = new BufferedWriter(new FileWriter(fWrite, true));
                    fileOut.write(time + " " + URL);
                    fileOut.newLine();
                    fileOut.close();
                    JOptionPane.showMessageDialog(null, "Successfully saved", "Favorite Page Dialog box", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(MouseHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Invalid URL", "Error Dialog box", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadPage(String _URL, String btn){
        if(btn.equals("home")){
            
            // pushing current opened URL to stack
            if(!_URL.isEmpty() && _URL.startsWith("https://")){
                System.out.println("Pushing " + _URL + " to back stack");
                browser.s_back.push(_URL);
                browser.s_forward.clear();
                browser.updateBtnSimulation();
            }
            readFile("home");
            if(browser.homePage == null){
                showError("No Home page saved");
            }
            else{
                try {
                    browser.displayPage.setPage(browser.homePage);
                    browser.addressBar.setText(browser.displayPage.getPage().toString());
                    System.out.println("Page loaded to " + browser.homePage);
                } catch (IOException ex) {
                    browser.displayPage.setContentType("text/html");
                    browser.displayPage.setText("<html><html>Server is down | Slow connection</html>");
                }
            }
        }
        else if(btn.equals("refresh")){
            if(!_URL.isEmpty()&&_URL.startsWith("https://")){
                try {
                    browser.displayPage.setPage(_URL);
                    browser.addressBar.setText(browser.displayPage.getPage().toString());
                } catch (IOException ex) {
                    browser.displayPage.setContentType("text/html");
                    browser.displayPage.setText("<html><html>Server is down | Slow connection</html>");
                }
            }
            else{
                showError("Invalid URL");
                browser.addressBar.setText("");
            }
        }
        else if(btn.equals("go")){
            if(!_URL.isEmpty()&&_URL.startsWith("https://")){
                try {
                    browser.displayPage.setPage(_URL);
                    browser.addressBar.setText(browser.displayPage.getPage().toString());
                    updateHistoryFile(_URL);
                } catch (IOException ex) {
                    browser.displayPage.setContentType("text/html");
                    browser.displayPage.setText("<html><html>Server is down | Slow connection</html>");
                }
            }
            else{
                showError("Invalid URL");
                browser.addressBar.setText("");
            }
        }
        else if(btn.equals("back")){

            // push current opened URL to forward stack
            if(!_URL.isEmpty()&&_URL.startsWith("https://")){
                System.out.println("Pushing " + _URL + " to forward stack");
                browser.s_forward.push(_URL);
                browser.updateBtnSimulation();
            }
            // load a URL poped from back stack
            _URL = null;
            _URL = browser.s_back.pop();
            browser.updateBtnSimulation();
            try {
                browser.displayPage.setPage(_URL);
                browser.addressBar.setText(browser.displayPage.getPage().toString());
                System.out.println("Page loaded to " + _URL);
            } catch (IOException ex) {
                browser.displayPage.setContentType("text/html");
                browser.displayPage.setText("<html><html>Server is down | Slow connection</html>");
            }
        }
        else if(btn.equals("forward")){
            // push current URL to back stack
            if(!_URL.isEmpty()&&_URL.startsWith("https://")){
                System.out.println("Pushing " + _URL + " to back stack");
                browser.s_back.push(_URL);
                browser.updateBtnSimulation();
            }
            // pop URL from back stack and make it current URL i.e opened
            _URL = browser.s_forward.pop();
            System.out.println("Poped " + _URL + " from forward stack");
            browser.updateBtnSimulation();
            
            try {
                browser.displayPage.setPage(_URL);
                browser.addressBar.setText(browser.displayPage.getPage().toString());
                System.out.println("Page loaded to " + _URL);
            } catch (IOException ex) {
                browser.displayPage.setContentType("text/html");
                browser.displayPage.setText("<html><html>Server is down | Slow connection</html>");
            }
        }
        else if(btn.equals("list")){
            // push current URL to back stack
            String currURL = browser.addressBar.getText();
            if(!currURL.isEmpty()&&currURL.startsWith("https://")){
                System.out.println("Pushing " + currURL + " to back stack");
                browser.s_back.push(currURL);
                browser.s_forward.clear();
                browser.updateBtnSimulation();
            }
            // load page
            try {
                browser.displayPage.setPage(_URL);
                browser.addressBar.setText(browser.displayPage.getPage().toString());
                System.out.println("Page loaded to " + _URL);
            } catch (IOException ex) {
                browser.displayPage.setContentType("text/html");
                browser.displayPage.setText("<html><html>Server is down | Slow connection</html>");
            }
        }
        System.out.println("Back stack contains " + browser.s_back.size() + " links now");
        System.out.println("Forward stack contains " + browser.s_forward.size() + " links now");
        System.out.println();
    }
    
    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage,
        "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void readFile(String fileName) {
        BufferedReader fileIn;
        File fRead;
        String line;
        if(fileName.equals("history")){
            fRead = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\history.txt");
            try {
                fileIn = new BufferedReader(new FileReader(fRead));
                {
                    browser.historyArray.clear();
                    line = fileIn.readLine();
                    while(line!=null){
                        browser.historyArray.add(new JMenuItem(line));
                        line = fileIn.readLine();
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error opening history file");
            }
        }
        else if(fileName.equals("favorite")){
//            System.out.println("reading favorite file");
            fRead = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\favorite.txt");
            try {
                fileIn = new BufferedReader(new FileReader(fRead));
                {
                    browser.favoriteArray.clear();
                    line = fileIn.readLine();
                    while(line!=null){
                        browser.favoriteArray.add(new JMenuItem(line));
                        line = fileIn.readLine();
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error opening favoriteFile file");
            }
//            System.out.println("reading favorite file ends");
        }
        else if(fileName.equals("home")){
//            System.out.println("reading home file");
            fRead = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\homePage.txt");
            try {
                fileIn = new BufferedReader(new FileReader(fRead));
                {
                    line = fileIn.readLine();
                    browser.homePage = line;
                }
            } catch (IOException ex) {
                System.out.println("Error opening homePage file");
            }
//            System.out.println("reading home file ends");
        }
        else if(fileName.equals("firewall")){
            fRead = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\firewall.txt");
            try {
                fileIn = new BufferedReader(new FileReader(fRead));
                {
                    browser.firewallArray.clear();
                    line = fileIn.readLine();
                    while(line!=null){
                        browser.firewallArray.add(line);
                        line = fileIn.readLine();
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error opening homePage file");
            }
        }
    }
    
    public void updateHistoryFile(String _URL){
        try {
//            System.out.println(_URL);
            File fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\history.txt");
            BufferedWriter fileOut = new BufferedWriter(new FileWriter(fWrite, true));
            String time = browser.df.format(browser.dateObj);
            fileOut.write(time + " " + _URL);
            fileOut.newLine();
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(MouseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clearFile(String str) {
        File fWrite = null;
        if(str.equals("history")){
            fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\history.txt");
            browser.historyArray.clear();
        }
        if(str.equals("favorite")){
            fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\favorite.txt");
            browser.favoriteArray.clear();
        }
        
        BufferedWriter fileOut;
        try {
            fileOut = new BufferedWriter(new FileWriter(fWrite));
            fileOut.write("");
            fileOut.close();
            JOptionPane.showMessageDialog(null, "Successfully Cleared", str, JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(BrowserClass.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    private void searchBtnAction() {
        String text = null;
        text = JOptionPane.showInputDialog("Enter query");
        if(text != null){
            Document document = browser.displayPage.getDocument();
            for (int i = 0; i + text.length() < document.getLength(); i++){
                try {
                    String match = document.getText(i, text.length());
                    if(text.equals(match)){
                        javax.swing.text.DefaultHighlighter.DefaultHighlightPainter highlightPainter =
                                        new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                                browser.displayPage.getHighlighter().addHighlight(i, i + text.length(),
                                        highlightPainter);

                                System.out.println("match found");
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(MouseHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else{
            showError("No query Entered");
        }
    }

    private void settingsBtnAction() {
            browser.settingsParentMenu = new JPopupMenu();
            
            JMenuItem item0 = new JMenuItem();
            Action exitAction = new AbstractAction("Exit") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    System.exit(0);
                }
            };
            item0.setAction(exitAction);
            
            JMenuItem item1 = new JMenuItem();
            Action find = new AbstractAction("Find") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    searchBtnAction();
                }
            };
            item1.setAction(find);
            browser.settingsParentMenu.add(item1);
            browser.settingsParentMenu.addSeparator();
            
            JMenuItem item2 = new JMenuItem();
            Action enableFirewall = new AbstractAction("Enable FireWall") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(browser.firewall == false){
                        browser.firewall = true;
                    }
                }
            };
            Action disableFirewall = new AbstractAction("Disable FireWall") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(browser.firewall == true){
                        browser.firewall = false;
                    }
                }
            };
            if(browser.firewall == false){
                item2.setAction(enableFirewall);
            }
            else{
                item2.setAction(disableFirewall);
            }
            browser.settingsParentMenu.add(item2);
            
            JMenuItem item3 = new JMenuItem();
            Action AddFirewall = new AbstractAction("Add key words") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    String key = null;
                    key = JOptionPane.showInputDialog("Enter new key word");
                    if(key != null && !key.isEmpty()){
                        try {
                            File fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\firewall.txt");
                            BufferedWriter fileOut = new BufferedWriter(new FileWriter(fWrite, true));
                            fileOut.write(key);
                            fileOut.newLine();
                            fileOut.close();
                            JOptionPane.showMessageDialog(null, "Successfully Added '" + key + "' keyword", "Firewall", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            Logger.getLogger(MouseHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        showError("No key word entered");
                    }
                }
            };
            item3.setAction(AddFirewall);
            browser.settingsParentMenu.add(item3);
            
            JMenu firewallListMenu = new JMenu();
            readFile("firewall");
            if(!browser.firewallArray.isEmpty()){
                for (String n : browser.firewallArray) {
                    firewallListMenu.add(n);
                }
            }
            else{
                firewallListMenu.add("No exception");
            }
            Action showFirewallList = new AbstractAction("Show firewall words list") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    // do nothing
                }
            };
            firewallListMenu.setAction(showFirewallList);
            browser.settingsParentMenu.add(firewallListMenu);
            
            JMenuItem item5 = new JMenuItem();
            Action delFirewall;
            delFirewall = new AbstractAction("Delete any key words") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String input = JOptionPane.showInputDialog("Enter word ?");
                if(browser.firewallArray.contains(input)){
                    browser.firewallArray.remove(input);
//                    clearFile("firewall");
                    try {
                        File fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\firewall.txt");
                        BufferedWriter firewallFile = new BufferedWriter(new FileWriter(fWrite));
                        firewallFile.write("");
                        
                        firewallFile = new BufferedWriter(new FileWriter(fWrite, true));
                        for (String n : browser.firewallArray) {
                            firewallFile.write(n);
                            firewallFile.newLine();
                        }
                        firewallFile.close();
                        JOptionPane.showMessageDialog(null, "Successfully deleted '" + input + "' keyword", "Firewall", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        Logger.getLogger(MouseHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else {
                    showError(input + " is not in firewall list");
                }
            }
        };
            item5.setAction(delFirewall);
            browser.settingsParentMenu.add(item5);
            
            browser.settingsParentMenu.addSeparator();
            browser.settingsParentMenu.add(item0);
    }
}