import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Class written by
 * Dahmani Abdelhak
 * */
public class AboutDevActionListener extends JDialog implements ActionListener {

    public AboutDevActionListener(){
        super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setTitle("About Developers");
        setSize(400,250);
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
        JLabel title = new JLabel("About Developers :");
        title.setOpaque(true);
        title.setFont(new Font("Calibri", Font.PLAIN, 24));
        JLabel desc = new JLabel("<html>We are four (04) students of Computer science at Bouira<br/>university, This our IHM project.<br/>Made by :<br/>- Meddahi Fares<br/>- Dahmani Abdelhak<br/>- Karaoui Rayan<br/>- Ben Abdelhafid Asma</html>");
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
