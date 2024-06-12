package gui;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {

    private Color color;

    public MyLabel(Color color){
        setFont(new Font("Verdana", Font.PLAIN, 18));
        setForeground(color);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }
}
