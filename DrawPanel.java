import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
* Class created containing basic paint method
* */
public class DrawPanel extends JPanel {

    private BufferedImage image;
    private int lastX, lastY;
    private HomeScreen homeScreen ;

    public DrawPanel(HomeScreen homeScreen){
        this.homeScreen = homeScreen;
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
                saveState(); // Save state for undo
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                homeScreen.g2.setStroke(new BasicStroke(homeScreen.brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                homeScreen.g2.drawLine(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
                repaint();
            }
        });
        clear();
    }

    /*
    * clear() function was written by
    * Dahmani Abdelhak
    * */
    public void clear() {
        homeScreen.g2.setPaint(Color.WHITE);
        homeScreen.g2.fillRect(0, 0, getWidth(), getHeight());
        homeScreen.g2.setPaint(homeScreen.color);
        repaint();
    }

    /*
     * saveImage() function was written by
     * Meddahi Fares
     * */
    public void saveImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a location to save the image");
        fileChooser.setSelectedFile(new File("image.png"));
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                // Write the image to the selected file
                ImageIO.write(image, "PNG", fileToSave);
                JOptionPane.showMessageDialog(null, "Image saved as " + fileToSave.getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving image: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Save operation was canceled.");
        }
    }

    /*
     * undo() function was written by
     * Meddahi Fares
     * */
    public void undo() {
        if (!homeScreen.undoStack.isEmpty()) {
            homeScreen.redoStack.push(image);
            image = homeScreen.undoStack.pop();
            homeScreen.g2 = image.createGraphics();
            homeScreen.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            homeScreen.g2.setPaint(homeScreen.color);
            repaint();
        }
    }

    /*
     * redo() function was written by
     * Meddahi Fares
     * */
    public void redo(){
        if (!homeScreen.redoStack.isEmpty()) {
            homeScreen.undoStack.push(image);

            // Pop the next state from the redo stack
            image = homeScreen.redoStack.pop();

            // Update Graphics2D for the new image
            homeScreen.g2 = image.createGraphics();
            homeScreen.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            homeScreen.g2.setPaint(homeScreen.color);

            repaint();
        }
    }

    /*
     * saveState() function was written by
     * Meddahi Fares
     * */
    private void saveState() {
        BufferedImage state = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = state.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        homeScreen.undoStack.push(state);
        homeScreen.redoStack.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            homeScreen.g2 = image.createGraphics();
            homeScreen.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

}
