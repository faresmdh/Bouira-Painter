import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Class written by
 * Ben Abdelhafid Asma
 * */
public class AboutAppActionListener extends JDialog implements ActionListener {

    public AboutAppActionListener(){
        super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setTitle("About Bouira Painter");
        setSize(400,200);
        setLocationRelativeTo(null);
        setResizable(false);
        Image image = Toolkit.getDefaultToolkit().getImage("src/images/logo.png");
        setIconImage(image);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel title = new JLabel("About Bouira Painter :");
        title.setOpaque(true);
        title.setFont(new Font("Calibri", Font.PLAIN, 24));
        JLabel desc = new JLabel("<html>Bouira Painter is a simple project that was built with Java Swing<br/> for educational purposes.</html>");
        JButton btn = new JButton("Close");
        btn.addActionListener(e1 -> {
            setVisible(false);
        });
        panel2.add(title);
        panel3.add(desc);
        panel4.add(btn);
        panel1.add(panel2,BorderLayout.NORTH);
        panel1.add(panel3,BorderLayout.CENTER);
        panel1.add(panel4,BorderLayout.SOUTH);
        setContentPane(panel1);
        setVisible(true);
    }
}
