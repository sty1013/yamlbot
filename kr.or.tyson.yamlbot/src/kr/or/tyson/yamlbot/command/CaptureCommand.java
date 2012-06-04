package kr.or.tyson.yamlbot.command;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.eclipse.swtbot.swt.finder.SWTBot;

public class CaptureCommand extends AbstractCommand {

    String target;
    
    public CaptureCommand(String target) {
        this.target = target;
    }
    
    @Override
    public SWTBot execute(SWTBot bot) throws Exception {
        BufferedImage screenCapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        File file = new File(target);
        ImageIO.write(screenCapture, "jpg", file);
        
        return bot;
    }

}
