//
//
//
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.AbstractAction;
//import javax.swing.JMenu;
//import javax.swing.JMenuItem;
//import javax.swing.JOptionPane;
//import javax.swing.JPopupMenu;
//
//class BtnHandler implements ActionListener{
//    
//    BrowserClass browser;
//    
//    BtnHandler(BrowserClass _browser){
//        browser = _browser;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent ae) {
//        
//        if(ae.getSource().equals(browser.backBtn)){         // back button action
//            backBtnAction();
//        }
//        else if(ae.getSource().equals(browser.forwardBtn)){         // forward button action
//            forwardBtnAction();
//        }
//        else if(ae.getSource().equals(browser.refreshBtn)){         // refresh button action
//            refreshBtnAction();
//        }
//        else if(ae.getSource().equals(browser.goBtn)){         // go button action
//            goBtnAction();
//        }
//        else if(ae.getSource().equals(browser.homeBtn)){
////            homeBtnAction();
//        }
//        else if(ae.getSource().equals(browser.historyBtn)){
//            historyBtnAction();
//        }
//        else if(ae.getSource().equals(browser.favoriteBtn)){
//            favoriteBtnAction();
//        }
//        else if(ae.getSource().equals(browser.searchBtn)){
////            searchBtnAction();
//        }
//    }
//    
//    private void backBtnAction() {
//        System.out.println("back button called");
//        
//        System.out.println("back button called end");
//    }
//
//    private void forwardBtnAction() {
//        System.out.println("forward button called");
//        
//        System.out.println("forward button called end");
//    }
//    
//    // :) 
//    private void refreshBtnAction() {
//        System.out.println("refresh button called");
//        String URL = browser.addressBar.getText();
//        loadPage(URL, "refresh");
//        System.out.println("refresh button called end");
//    }
//    
//    // :)
//    private void goBtnAction() {
//        String URL = browser.addressBar.getText();
//        loadPage(URL, "go");
//    }
//    
//    // home button menu
//    public void homeBtnAction(){
//        
//        // home menu
//        browser.homeBtnPopup = new JPopupMenu();
//        
//        // items for home menu and their actions
//        browser.homeBtnItem1 = new JMenuItem(new AbstractAction("Set Current page as Home Page") {
//            
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                savePage("home");
//            }
//        });
//        browser.homeBtnItem2 = new JMenuItem(new AbstractAction("Show Home Page") {
//            
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                String URL = browser.addressBar.getText();
//                loadPage(URL, "home");
//            }
//        });
//
//        // add items to home menu
//        browser.homeBtnPopup.add(browser.homeBtnItem1);
//        browser.homeBtnPopup.add(browser.homeBtnItem2);
//        
//    }
//    
//    // history button menu
//    public void historyBtnAction(){
//        browser.historyParentMenu = new JPopupMenu();
//        JMenuItem historyBtnItem1 = new JMenuItem(new AbstractAction("Clear History") {
//            
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                clearHistory();        
//            }
//
//            private void clearHistory() {
//                File fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\history.txt");
//                BufferedWriter fileOut;
//                try {
//                    fileOut = new BufferedWriter(new FileWriter(fWrite));
//                    fileOut.write("");
//                    fileOut.close();
//                    JOptionPane.showMessageDialog(null, "Successfully Cleared","History", JOptionPane.OK_OPTION);
//                } catch (IOException ex) {
//                    Logger.getLogger(BrowserClass.class.getName()).log(Level.SEVERE, null, ex);
//                }   
//            }
//        });
//        browser.historyParentMenu.add(historyBtnItem1);
//        
//        //  sub menu
//        JMenu historyListMenu = new JMenu("Show History");
//        JMenuItem historyListItems = new JMenuItem(new AbstractAction("Show History") {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                showHistory();
//            }
//
//            private void showHistory() {
//                readFile("history");
//            }
//        });
//        historyListMenu.add(historyListItems);
//        browser.historyParentMenu.add(historyListMenu);
//    }
//    
//    // favorite button menu
//    public void favoriteBtnAction(){
////        browser.readFavorite();
//        browser.favoriteBtnPopup = new JPopupMenu();
//        
//        // favorite button items and their actions
//        browser.favoriteBtnItem1 = new JMenuItem(new AbstractAction("Set Current URL as Favorite") {
//            
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                savePage("favorite");
//            }
//        });
//        browser.favoriteBtnItem2 = new JMenuItem(new AbstractAction("Show Favorites") {
//            
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                showFavorite();
//            }
//
//            private void showFavorite() {
//                
//            }
//        });
//
//        // add items to favorite menu
//        browser.favoriteBtnPopup.add(browser.favoriteBtnItem1);
//        browser.favoriteBtnPopup.add(browser.favoriteBtnItem2);
//        
//    }
//    
//    private void savePage(String str) {
//        String URL = browser.addressBar.getText();
//        if(!URL.isEmpty() && URL.startsWith("https://")){
//            File fWrite = null;
//            BufferedWriter fileOut;
//            // for saving home page 
//            if(str.equals("home")){
//                try {
//                    fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\homePage.txt");
//                    fileOut = new BufferedWriter(new FileWriter(fWrite));
//                    fileOut.write("");
//                    fileOut.write(URL);
//                    fileOut.newLine();
//                    fileOut.close();
//                    JOptionPane.showMessageDialog(null, "Successfully saved", "Home Page Dialog box", JOptionPane.OK_OPTION);
//                } catch (IOException ex) {
//                    Logger.getLogger(BtnHandler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            else if (str.equals("favorite")){
//                try {
//                    fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\favorite.txt");
//                    fileOut = new BufferedWriter(new FileWriter(fWrite, true));
//                    fileOut.write(URL);
//                    fileOut.newLine();
//                    fileOut.close();
//                    JOptionPane.showMessageDialog(null, "Successfully saved", "Favorite Page Dialog box", JOptionPane.OK_OPTION);
//                } catch (IOException ex) {
//                    Logger.getLogger(BtnHandler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        else{
//            JOptionPane.showMessageDialog(null, "Invalid URL", "Error Dialog box", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//    
//    // :)
//    private void loadPage(String _URL, String btn){
//        if(!_URL.isEmpty()&&_URL.startsWith("https://")){
//            if(btn.equals("refresh")){
//                try {
//                    browser.displayPage.setPage(_URL);
//                    browser.addressBar.setText(_URL);
//                } catch (IOException ex) {
//                    browser.displayPage.setContentType("text/html");
//                    browser.displayPage.setText("<html><html>Server is down | Slow connection</html>");
//                }
//            }
//            if(btn.equals("go")){
//                try {
//                    browser.displayPage.setPage(_URL);
//                    browser.addressBar.setText(_URL);
//                    updateHistoryFile(_URL);
//                } catch (IOException ex) {
//                    browser.displayPage.setContentType("text/html");
//                    browser.displayPage.setText("<html><html>Server is down | Slow connection</html>");
//                }
//            }
//        }
//        if(btn.equals("home")){
//            // pushing current opened URL to stack
//            if(!_URL.isEmpty()&&_URL.startsWith("https://")){
//                browser.s_back.push(_URL);
//            }
//            File fRead = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\homePage.txt");
//            BufferedReader fileIn;
//            try {
//                fileIn = new BufferedReader(new FileReader(fRead));
//                {
//                    String line = fileIn.readLine();
//                    if(line.isEmpty()){
//                        JOptionPane.showMessageDialog(null, "No Home Page Saved", "Home Page Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                    else{
//                        browser.displayPage.setPage(line);
//                        browser.addressBar.setText(line);
//                    }
//                }
//            } catch (IOException ex) {
//                System.out.println("Error opening file");
//            }
//        }
//        else {
//            showError("Invalid URL");
//        }
//    }
//    
//    // :)
//    public void updateHistoryFile(String _URL){
//        try {
//            System.out.println(_URL);
//            File fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\history.txt");
//            BufferedWriter fileOut = new BufferedWriter(new FileWriter(fWrite, true));
//            fileOut.write(_URL);
//            fileOut.newLine();
//            fileOut.close();
//        } catch (IOException ex) {
//            Logger.getLogger(BtnHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void showError(String errorMessage) {
//        JOptionPane.showMessageDialog(null, errorMessage,
//        "Error", JOptionPane.ERROR_MESSAGE);
//        browser.addressBar.setText("");
//    }
//    
//    
//    private void readFile(String fileName) {
//        BufferedReader fileIn;
//        File fRead;
//        String line;
//        if(fileName.equals("history")){
//            fRead = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\history.txt");
//            try {
//                fileIn = new BufferedReader(new FileReader(fRead));
//                {
//                    line = fileIn.readLine();
//                    while(line!=null){
//                        browser.historyArray.add(line);
//                        line = fileIn.readLine();
//                    }
//                }
//            } catch (IOException ex) {
//                System.out.println("Error opening file");
//            }
//        }
//        else if(fileName.equals("favorite")){
//            fRead = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\favorite.txt");
//            try {
//                fileIn = new BufferedReader(new FileReader(fRead));
//                {
//                    line = fileIn.readLine();
//                    while(line!=null){
//                        browser.favoriteArray.add(line);
//                        line = fileIn.readLine();
//                    }
//                }
//            } catch (IOException ex) {
//                System.out.println("Error opening file");
//            }
//        }
//    }
//}