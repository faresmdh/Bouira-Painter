import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class HomeScreen extends JFrame implements ActionListener {

    //GUI variables
    private JPanel homePanel, toolbarPanel, canvasPanel;
    private DrawPanel drawPanel;
    private JMenuItem save, exit, undo, redo, clear, aboutDev, aboutApp;
    private JButton saveBtn = new JButton(), undoBtn = new JButton(), redoBtn = new JButton() ,clearBtn = new JButton(), exitBtn = new JButton();
    private ArrayList<ButtonColor> colorsList = new ArrayList<ButtonColor>();
    private JLabel currentColorLabel;
    private JSlider brushSizeSlider;

    //Run variables
    public int brushSize = 1;
    public Color color = Color.BLACK;
    public Stack<BufferedImage> undoStack = new Stack<>();
    public Graphics2D g2 = new MyGraphics2D();


    public HomeScreen(){
        super();
        initJFrame();
        initJMenuBar();
        initContentPane();
        handleMenuBarAndToolbarClicks();
        listenToColorAndBrushSizeChanges();
        setVisible(true);
    }

    private void initJFrame() {
        setTitle("Bouira Painter");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image image = Toolkit.getDefaultToolkit().getImage("src/images/logo.png");
        setIconImage(image);
    }

    private void initJMenuBar() {
        //initialising new JMenuBar
        JMenuBar menuBar = new JMenuBar();

        //initialising new JMenus
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");

        //add some padding
        file.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
        edit.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
        help.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));

        //initialising JMenuItems
        save = new JMenuItem("Save as");
        exit = new JMenuItem("Exit");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        clear = new JMenuItem("Clear canvas");
        aboutDev = new JMenuItem("About developer");
        aboutApp = new JMenuItem("About app");

        //Adding every item to his menu
        file.add(save);
        file.add(exit);
        edit.add(undo);
        edit.add(redo);
        edit.add(clear);
        help.add(aboutDev);
        help.add(aboutApp);

        //Adding menus to menuBar
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);

        //Setting menuBar to the JFrame
        setJMenuBar(menuBar);
    }

    private void initContentPane() {
        //initialising the three panels
        homePanel = new JPanel();
        toolbarPanel = new JPanel();
        canvasPanel = new JPanel();

        //change layout to the three panels
        homePanel.setLayout(new BorderLayout(0,16));
        toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT,8,8));
        canvasPanel.setLayout(new CardLayout());

        //add padding to the three layouts
        homePanel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        canvasPanel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        //adding titled borders to the two children panels (toolbar and canvas)
        toolbarPanel.setBorder(BorderFactory.createTitledBorder("Toolbar"));
        canvasPanel.setBorder(BorderFactory.createTitledBorder("Canvas"));

        //initialising the two children panels (toolbar and canvas)
        initToolbar();
        initCanvas();

        //add the two child panels to the parent panel
        homePanel.add(toolbarPanel,BorderLayout.NORTH);
        homePanel.add(canvasPanel,BorderLayout.CENTER);

        //set the content pane home panel
        setContentPane(homePanel);
    }

    private void initToolbar(){
        //create toolbar buttons
        initButton(saveBtn,"src/images/save.png");
        initButton(undoBtn,"src/images/undo.png");
        initButton(redoBtn,"src/images/redo.png");
        initButton(clearBtn,"src/images/clear.png");
        initButton(exitBtn,"src/images/exit.png");
        toolbarPanel.add(saveBtn);
        toolbarPanel.add(undoBtn);
        toolbarPanel.add(redoBtn);
        toolbarPanel.add(clearBtn);
        toolbarPanel.add(exitBtn);
        colorsList.add(createButtonColor(Color.BLACK));
        colorsList.add(createButtonColor(Color.WHITE));
        colorsList.add(createButtonColor(Color.RED));
        colorsList.add(createButtonColor(Color.GREEN));
        colorsList.add(createButtonColor(Color.BLUE));
        colorsList.add(createButtonColor(Color.YELLOW));
        colorsList.add(createButtonColor(Color.ORANGE));
        colorsList.add(createButtonColor(Color.PINK));
        colorsList.add(createButtonColor(Color.MAGENTA));
        JPanel colorsPanel = new JPanel();
        colorsPanel.setBorder(BorderFactory.createTitledBorder("Colors"));
        for (ButtonColor c:colorsList) colorsPanel.add(c.getButton());
        colorsPanel.setLayout(new GridLayout(3,3,4,4));
        toolbarPanel.add(colorsPanel);
        currentColorLabel = new JLabel("        ");
        currentColorLabel.setSize(38,38);
        currentColorLabel.setOpaque(true);
        currentColorLabel.setBackground(color);
        currentColorLabel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        toolbarPanel.add(currentColorLabel);
        brushSizeSlider =  new JSlider(1,20,1);
        brushSizeSlider.setMajorTickSpacing(5);
        brushSizeSlider.setMinorTickSpacing(1);
        brushSizeSlider.setPaintTicks(true);
        brushSizeSlider.setPaintLabels(true);
        brushSizeSlider.setBorder(BorderFactory.createTitledBorder("Brush size"));
        brushSizeSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toolbarPanel.add(brushSizeSlider);
    }

    private void initCanvas(){
        drawPanel = new DrawPanel(this);
        canvasPanel.add(drawPanel);
    }

    /*
     * This function take a color as parameter and do the following
     * create new button(10X10) with the previous color as background
     * create new ButtonColor object then return it
     * */
    private ButtonColor createButtonColor(Color color){
        JButton btn = new JButton("                  ");
        btn.setSize(10,10);
        btn.setBackground(color);
        btn.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ButtonColor buttonColor = new ButtonColor(btn,color);
        return buttonColor;
    }

    /*
    * This function take image path and JButton as parameters and do the following
    * put the image inside button
    * make button looks nicer
    * */
    private void initButton(JButton myBtn,String path){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(image.getScaledInstance(30,30, Image.SCALE_SMOOTH));
        myBtn.setIcon(icon);
        myBtn.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        myBtn.setBackground(Color.white);
        myBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void handleMenuBarAndToolbarClicks() {
        save.addActionListener(this);
        exit.addActionListener(new ExitActionListener());
        undo.addActionListener(this);
        redo.addActionListener(this);
        clear.addActionListener(this);
        aboutApp.addActionListener(new AboutAppActionListener());
        aboutDev.addActionListener(new AboutDevActionListener());
        saveBtn.addActionListener(this);
        undoBtn.addActionListener(this);
        redoBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        exitBtn.addActionListener(new ExitActionListener());
    }

    private void listenToColorAndBrushSizeChanges(){
        //listen to color changes
        for (ButtonColor bc : colorsList) {
            bc.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    g2.setColor(bc.getColor());
                    color = bc.getColor();
                    currentColorLabel.setBackground(color);
                }
            });
        }

        //listen to brush size
        brushSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                brushSize = source.getValue();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == save || source == saveBtn) drawPanel.saveImage();
        else if (source == undo || source == undoBtn) drawPanel.undo();
        else if (source == redo || source == redoBtn) drawPanel.redo();
        else if (source == clear || source == clearBtn) drawPanel.clear();

    }
}
