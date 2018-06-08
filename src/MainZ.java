/*
Blueprint for future applications
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;

import static java.awt.GridBagConstraints.*;

public class MainZ {
    public static void main(String[] args){
        new MainZ();
    }
    private  MainZ(){
        new GUIFrame();
    }

    //The Main GUI for the application
    //GridBagLayout will be the solely used layout
    private class GUIFrame extends JFrame{
        //Primary fields that will most likely be needed by most methods and inner classes
        private  JPanel mainPanel = new JPanel();
        private  JPanel secondaryPanel = new JPanel();
        private JLabel screenLabel = new JLabel("Home", SwingConstants.CENTER);
        private JButton cButton1 = new JButton("Execute");
        private JButton cButton2 = new JButton("Home");
        private JTextArea infoText = new JTextArea("Info");
        private JScrollPane infoArea = new JScrollPane(infoText);
        private JLabel mainLabel = new JLabel("", SwingConstants.CENTER);
        private GridBagConstraints gbc = new GridBagConstraints();
        private GridBagLayout gbl = new GridBagLayout();

        private GUIFrame(){
            super("Project Zero");
            setSize(550, 400);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            //Center GUI
            setLocationRelativeTo(null);
            this.setLayout(gbl);

            //Main panel set up
            mainPanel.setLayout(gbl);
            mainPanel.add(mainLabel);
            gbl.setConstraints(mainLabel, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, CENTER,BOTH, gbc.insets, 0, 0));
            mainLabel.setFont(new Font("Times New Roman", Font.BOLD, 90));

            //Secondary panel set up
            secondaryPanel.setLayout(gbl);
            secondaryPanel.add(cButton1);
            gbl.setConstraints(cButton1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, CENTER,0, new Insets(20,0,0,0), 0, 0));
            secondaryPanel.add(cButton2);
            gbl.setConstraints(cButton2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, CENTER,0, new Insets(0,0,0,0), 0, 0));
            secondaryPanel.add(infoArea);
            gbl.setConstraints(infoArea, new GridBagConstraints(1, 0, 1, 2, 2.0, 1.0, CENTER,BOTH, gbc.insets, 0, 0));

            //Border Colors
            screenLabel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            mainPanel.setBorder(BorderFactory.createLineBorder(Color.black,2));
            secondaryPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

            //Adding the major panels to the main frame
            this.add(screenLabel);
            this.add(mainPanel);
            this.add(secondaryPanel);

            //Main Panels Config
            gbl.setConstraints(screenLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, CENTER, BOTH, gbc.insets, 0, 0));
            gbl.setConstraints(mainPanel, new GridBagConstraints(0, 1, 1, 2, 10.0, 10.0, CENTER, BOTH, gbc.insets, 0, 0));
            gbl.setConstraints(secondaryPanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, CENTER, BOTH, gbc.insets, 0, 0));
            homeScreen();

            //Actions for Control buttons
            this.setVisible(true);
            ActionListener buttonActions = (event) -> {
                String action = event.getActionCommand();

                if(action.equals("Home")){
                    homeScreen();
                }

            };
            cButton1.addActionListener(buttonActions);
            //Unneeded second button. May use for later implementations.
            cButton1.setVisible(false);
            cButton2.addActionListener(buttonActions);
        }
        private void homeScreen(){
            screenLabel.setText("Home");
            screenLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
            mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
            secondaryPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
            backgroundThread = null;
            mainPanel.removeAll();
            mainPanel.revalidate();
            mainPanel.repaint();
            infoText.setText("");
            JButton entry01 = new JButton("TaskChooser");
            JButton entry02 = new JButton("JpgRenamer");
            mainPanel.add(entry01);
            mainPanel.add(entry02);
            gbl.setConstraints(entry01, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, CENTER, 0, gbc.insets, 0, 0));
            gbl.setConstraints(entry02, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, CENTER, 0, gbc.insets, 0, 0));
            ActionListener entryAction = (event) ->{
                String action = event.getActionCommand();
                if(action.equals("TaskChooser")){
                    taskChooser();
                }
                else if(action.equals("JpgRenamer")){
                    jpgRenamer();
                }
            };

            entry01.addActionListener(entryAction);
            entry02.addActionListener(entryAction);
        }

        Thread backgroundThread = null;
        private int timer = 0;
        private String mission = "";
        private void taskChooser(){
            mainPanel.removeAll();
            mainPanel.revalidate();
            mainPanel.repaint();

            screenLabel.setText("Task Chooser");
            mainLabel.setText("RICE");
            JButton mButton01 = new JButton("Random");
            JButton mButton02 = new JButton("Stop");
            mButton02.setVisible(false);
            mainPanel.add(mainLabel);
            mainPanel.add(mButton01);
            mainPanel.add(mButton02);
            gbl.setConstraints(mainLabel, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, CENTER,BOTH, gbc.insets, 0, 0));
            gbl.setConstraints(mButton01, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, CENTER,0, gbc.insets, 0, 0));
            gbl.setConstraints(mButton02, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, WEST,0, gbc.insets, 0, 0));

            Runnable runnable = () -> {
                while(backgroundThread != null){
                    infoText.setText(mission);
                    //Ternary Operator FTW
                    mainLabel.setText("" + ((timer / 60) >= 10 ? timer / 60 : "0" + timer / 60) + ':' + ((timer % 60) >= 10 ? (timer % 60) : "0" + (timer % 60)));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        backgroundThread = null;
                    }
                    this.timer--;
                    if(this.timer <= 0){
                        backgroundThread = null;
                        mainLabel.setText("D.U.N.");
                    }
                }
            };

            ActionListener mainActions = (event) -> {
                String action = event.getActionCommand();
                int randomInt01 = (int)(Math.random()*255);
                int randomInt02 = (int)(Math.random()*255);
                int randomInt03 = (int)(Math.random()*255);

                switch (action) {
                    case "Random":
                        if (backgroundThread == null) {
                            backgroundThread = new Thread(runnable);
                        }
                        screenLabel.setBorder(BorderFactory.createLineBorder(new Color(randomInt01, randomInt02, randomInt03), 2));
                        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(randomInt02, randomInt03, randomInt01), 2));
                        secondaryPanel.setBorder(BorderFactory.createLineBorder(new Color(randomInt03, randomInt02, randomInt01), 2));

                        String[] AssList = {"Git Essentials", "Current Events", "Android Programming", "Web Programming", "Career Building", "Scrum Methodology", "Japanese", "Anything", "Wild"};
                        int[] times = {20, 25, 30};
                        int rand01 = (int) (Math.random() * 9);
                        int rand02 = (int) (Math.random() * 3);
                        timer = times[rand02];
                        mission = "Study " + AssList[rand01] + " for " + times[rand02] + " min.";

                        timer = timer * 60;

                        if (!backgroundThread.isAlive()) {
                            backgroundThread.start();
                        }

                        //Button layout change
                        gbl.setConstraints(mButton01, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, EAST, 0, gbc.insets, 0, 0));
                        mButton02.setVisible(true);
                        mButton02.setText("Stop");
                        mainPanel.repaint();
                        break;
                    case "Stop":
                        backgroundThread.interrupt();
                        backgroundThread = null;
                        mButton02.setText("Go");
                        mainPanel.repaint();
                        break;
                    case "Go":
                        if (backgroundThread == null) {
                            backgroundThread = new Thread(runnable);
                        }
                        if (!backgroundThread.isAlive()) {
                            backgroundThread.start();
                        }
                        mButton02.setText("Stop");
                        mainPanel.repaint();
                        break;
                }
            };
            mButton01.addActionListener(mainActions);
            mButton02.addActionListener(mainActions);
        }
    }
        private void jpgRenamer(){
            // change file names in 'Directory':
            //"C:\\Dropbox\\java\\Directory";
            String absolutePath = null;
            try {
                absolutePath = new File(MainZ.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            System.out.println(absolutePath);

            File dir = new File(absolutePath);
            File[] filesInDir = dir.listFiles();
            for(File file:filesInDir) {
                long randomNum = (long)(Math.random() * 500_000_000);
                String name = file.getName();
                String newName = "Image_" + randomNum + ".jpg";
                String newPath = absolutePath + "\\" + newName;
                file.renameTo(new File(newPath));
                System.out.println(name + " changed to " + newName);
            }

        }
}
