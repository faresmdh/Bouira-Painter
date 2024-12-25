import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* Class written by
* Ben Abdelhafid Asma
* */
public class ExitActionListener extends JDialog implements ActionListener {

    public ExitActionListener(){
        super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setTitle("Exit application");
        setSize(300,130);
        setLocationRelativeTo(null);
        setResizable(false);
        Image image = Toolkit.getDefaultToolkit().getImage("src/images/logo.png");
        setIconImage(image);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        setContentPane(panel1);
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel3.setLayout(new FlowLayout(FlowLayout.RIGHT,8,8));
        JLabel label = new JLabel("Do you really want to exit Bouira Painter?");
        panel2.add(label);
        JButton btn1 = new JButton("Yes");
        JButton btn2 = new JButton("No");
        btn1.addActionListener(e1 -> {
            System.exit(0);
        });
        btn2.addActionListener(e12 -> {
            setVisible(false);
        });
        panel3.add(btn1);
        panel3.add(btn2);
        panel1.add(panel2,BorderLayout.CENTER);
        panel1.add(panel3,BorderLayout.SOUTH);
        setVisible(true);
    }
}
