
package browser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

class HyperlinkHandler implements HyperlinkListener{
    
//    String currURL;
    BrowserClass browser;
    
    HyperlinkHandler(BrowserClass _browser){
        browser = _browser;
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent he) { // this function will be automatically called whenever i click on any ink on web page

        URL currURL = browser.displayPage.getPage();

        if(he.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
            browser.s_back.push(currURL.toString()); // pushing currently opened page to back stack
            browser.s_forward.clear();
            browser.updateBtnSimulation();
            loadPage(he.getURL());
        }

    }

    private void loadPage(URL _URL) {
        try {
            if(_URL != null){
                browser.displayPage.setPage(_URL);
                browser.addressBar.setText(_URL.toString());
                updateHistoryFile(_URL);
            }
            else{
               showError("Invalid URL");
            }
        } catch (IOException ex) {
            browser.displayPage.setContentType("text/html");
            browser.displayPage.setText("<html>server is down | slow connection</html>");
        }
        System.out.println("Page updated to " + _URL.toString());
        System.out.println("Back stack contains " + browser.s_back.size() + " links now");
        System.out.println("Forward stack contains " + browser.s_forward.size() + " links now");
    }
    
    public void updateHistoryFile(URL _URL){
        BufferedWriter fileOut = null;
        try {
            File fWrite = new File("C:\\Users\\Hamid\\Documents\\NetBeansProjects\\WebBrowser\\src\\browser\\files\\history.txt");
            fileOut = new BufferedWriter(new FileWriter(fWrite, true));
            String time = browser.df.format(browser.dateObj);
            fileOut.write(time + " " + _URL.toString());
            fileOut.newLine();
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(HyperlinkHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage,
        "Error", JOptionPane.ERROR_MESSAGE);
        browser.addressBar.setText("");
    }
}
