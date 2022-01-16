package com.wudidemiao.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author wudidemiaoa
 * @date 2022/1/13
 * @apiNote
 */
public class Test9 {
    /**
     * 截图
     *
     * @param filePath
     *            截图保存文件夹路径
     * @param fileName
     *            截图文件名称
     * @throws Exception
     */
    public static void captureScreen(String filePath, String fileName) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        // 截图保存的路径
        File screenFile = new File(filePath + fileName);
        // 如果文件夹路径不存在，则创建
        if (!screenFile.getParentFile().exists()) {
            screenFile.getParentFile().mkdirs();
        }

        // 指定屏幕区域，参数为截图左上角坐标(100,100)+右下角坐标(500,500)
        BufferedImage subimage = image.getSubimage(100, 100, 500, 500);
        ImageIO.write(subimage, "png", screenFile);

    }

    public static void main(String[] args) throws Exception {
//        new MouseListener()
    }

    public static void captureScreens(String fileName, String folder) throws Exception {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        //保存路径
        File screenFile = new File(fileName);
        if (!screenFile.exists()) {
            screenFile.mkdir();
        }
        File f = new File(screenFile, folder);

        ImageIO.write(image, "png", f);
        //自动打开
        if (Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
            Desktop.getDesktop().open(f);
    }
}
