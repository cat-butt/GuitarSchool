package com.jackson.guitar;
//
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.control.Label;
//
//import java.io.FileNotFoundException;

/**
 * Created by Jackson on 3/21/2017.
 */
public class ControlPaneTemp {

//    private final Node content;
//    private final Parent effectPane;
//
//    ScaleButtonPanel scaleButtonPanel;
//    RootButtonPanel rootButtonPanel;
//    ScaleFinder scaleFinder;
//    String currentScale = "Ionian";
//    String currentRoot = "C";
//    Label currentLabel = new Label(currentRoot + " " + currentScale);
//    ControlPaneTemp(Node content) {
//        this.content = content;
//        this.effectPane = new StackPane
//        try {
//            scaleFinder = new ScaleFinder();
//            scaleFinder.setRoot(0);
//            scaleFinder.setScaleName("Ionian");
//        } catch(FileNotFoundException e) {
//            System.out.println("must provide a 'scales.dat' file");
//            System.exit(0);
//        }
//        scaleButtonPanel = new ScaleButtonPanel();
//        rootButtonPanel = new RootButtonPanel();
//        currentLabel.setHorizontalAlignment(JLabel.CENTER);
//        currentLabel.setForeground(Color.black);
//        currentLabel.setFont(new Font("Serif",Font.BOLD,18));
//        setLayout(new BorderLayout());
//        add(scaleButtonPanel,BorderLayout.EAST);
//        add(rootButtonPanel,BorderLayout.WEST);
//        add(currentLabel,BorderLayout.SOUTH);
//    }
//    class ScaleButtonPanel implements ActionListener{
//        String[] groups = scaleFinder.getGroupNameList();
//        ButtonPanel buttonPanels[];
//        public ButtonGroup bg = new ButtonGroup();
//
//        ScaleButtonPanel() {
//            buttonPanels = new ButtonPanel[groups.length];
//            setLayout(new GridLayout(groups.length,1));
//            for(int i=0; i<groups.length; i++) {
//                buttonPanels[i] = new ButtonPanel(groups[i], scaleFinder.getGroup(groups[i]));
//                add(buttonPanels[i]);
//            }
//            setBorder(BorderFactory.createEtchedBorder());
//        }
//        class ButtonPanel extends JPanel {
//            JComboBox comboBox;
//            JLabel label;
//            ButtonPanel(String title,String list[]) {
//                super();
//                comboBox = new JComboBox(list);
//                label = new JLabel(title);
//                comboBox.addActionListener(ScaleButtonPanel.this);
//                add(label);
//                add(comboBox);
//            }
//        }
//        public void actionPerformed(ActionEvent e) {
//            JComboBox box = (JComboBox)e.getSource();
//            currentScale = (String)box.getSelectedItem();
//            scaleFinder.setScaleName(currentScale);
//            currentLabel.setText(currentRoot + " " + currentScale);
//            guitarPanel.setScale(scaleFinder);
//            pianoPanel.setScale(scaleFinder);
//        }
//
//    }
//    class RootButtonPanel extends JPanel implements ActionListener {
//        JRadioButton rootList[] = new JRadioButton[ScaleFinder.NOTE_NAMES.length];
//        public ButtonGroup bg = new ButtonGroup();
//        RootButtonPanel() {
//            super();
//            setLayout(new GridLayout(12,1));
//            for(int i=0; i<ScaleFinder.NOTE_NAMES.length; i++) {
//                bg.add(rootList[i] = new JRadioButton(ScaleFinder.NOTE_NAMES[i]));
//                add(rootList[i]);
//                rootList[i].addActionListener(RootButtonPanel.this);
//                setBorder(BorderFactory.createEtchedBorder());
//            }
//            rootList[0].setSelected(true);
//        }
//        public void actionPerformed(ActionEvent e) {
//            JRadioButton btn = (JRadioButton)e.getSource();
//            currentRoot = btn.getText();
//            scaleFinder.setRoot(currentRoot);
//            currentLabel.setText(currentRoot + " " + currentScale);
//            guitarPanel.setScale(scaleFinder);
//            pianoPanel.setScale(scaleFinder);
//        }
//    }

}
