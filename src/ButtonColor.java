import javax.swing.*;
import java.awt.*;

public class ButtonColor {

    private JButton button;
    private Color color;

    public ButtonColor(JButton button, Color color) {
        this.button = button;
        this.color = color;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
