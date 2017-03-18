package pl.konczak.nzoz.ewus.config;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;

@Component
public class MyTrayIcon
        extends TrayIcon {

    private static final String IMAGE_PATH = "/tray-icon.png";

    private static final String TOOLTIP = "Text";

    private PopupMenu popup;

    private SystemTray tray;

    public MyTrayIcon() {
        super(createImage(IMAGE_PATH, TOOLTIP), TOOLTIP);
        popup = new PopupMenu();
        tray = SystemTray.getSystemTray();
    }

    @PostConstruct
    public void setup() throws AWTException {
        // popup.add(itemAbout);
        // here add the items to your popup menu. These extend MenuItem
        // popup.addSeparator();
        setPopupMenu(popup);
        tray.add(this);
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = MyTrayIcon.class.getResource(path);
        if (imageURL == null) {
            System.err.println("Failed Creating Image. Resource not found: " + path);
            return null;
        } else {
            return new ImageIcon(imageURL, description).getImage();
        }
    }
}
