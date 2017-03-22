package pl.konczak.nzoz.ewus.config;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import pl.konczak.nzoz.ewus.domain.checkcwu.CheckCWUStatusFacade;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

@Component
public class TrayIconConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrayIconConfigurator.class);

    private static final String IMAGE_PATH = "/tray-icon.png";

    private static final String TOOLTIP = "Text";

    private final ApplicationContext appContext;

    private final CheckCWUStatusFacade checkCWUStatusFacade;

    public TrayIconConfigurator(ApplicationContext appContext, CheckCWUStatusFacade checkCWUStatusFacade) {
        this.appContext = appContext;
        this.checkCWUStatusFacade = checkCWUStatusFacade;
    }

    @PostConstruct
    public void setup() {
        if (!SystemTray.isSupported()) {
            return;
        }
        SystemTray tray = SystemTray.getSystemTray();
        final TrayIcon trayIcon = new TrayIcon(createImage(IMAGE_PATH, TOOLTIP));
        final PopupMenu popup = new PopupMenu();

        trayIcon.setPopupMenu(popup);

        MenuItem aboutItem = new MenuItem("About");
        MenuItem checkAllItem = new MenuItem("CheckAll");
        MenuItem exitItem = new MenuItem("Exit");

        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(checkAllItem);
        popup.addSeparator();
        popup.add(exitItem);

        try {
            tray.add(trayIcon);
        } catch (AWTException ex) {
            LOGGER.warn("Failed to add TrayIcon", ex);
            return;
        }

        trayIcon.addActionListener(actionEvent -> JOptionPane.showMessageDialog(null, "This dialog box is run from System Tray"));

        aboutItem.addActionListener(actionEvent -> JOptionPane.showMessageDialog(null, "This dialog box is run from the About menu item"));

        checkAllItem.addActionListener(actionEvent -> {
            try {
                checkCWUStatusFacade.checkCWUForAll();
            } catch (Exception ex) {
                LOGGER.error("Failed to execute checkCWUForAll", ex);
            }
        });

        exitItem.addActionListener(actionEvent -> {
            tray.remove(trayIcon);
            SpringApplication.exit(appContext, () -> 0);
        });
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = TrayIconConfigurator.class.getResource(path);
        if (imageURL == null) {
            LOGGER.error("Failed to create TrayIcon image. Resource not found <{}>", path);
            return null;
        } else {
            return new ImageIcon(imageURL, description).getImage();
        }
    }
}
