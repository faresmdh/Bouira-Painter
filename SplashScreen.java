import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/*
* Class written By
* Meddahi Fares
* */
public class SplashScreen {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                try {
//                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (UnsupportedLookAndFeelException e) {
//                    e.printStackTrace();
//                }
                JWindow window = new JWindow();
                window.setSize(600,400);
                window.setLocationRelativeTo(null);
                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File("src/images/splash.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageIcon splash = new ImageIcon(image.getScaledInstance(600,400, Image.SCALE_SMOOTH));
                JLabel imageHolder = new JLabel();
                imageHolder.setIcon(splash);
                window.getContentPane().add(imageHolder);
                window.setVisible(true);

                endSplashStartHome(window);
            }
        });
    }

    private static void endSplashStartHome(JWindow window) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
                window.setVisible(false);
                window.dispose();
                new HomeScreen();
            }
        }).start();
    }

}
