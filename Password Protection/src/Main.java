import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.FileSystemView;

class Frame{
    static JLabel file;
    static JTextField pass_wd;
    public void structure() throws IOException {
        //frame
        JFrame f = new JFrame("Password Encryption");
        f.setSize(400, 300);
        f.setResizable(false);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Buttons
        JButton e = new JButton("Encrypt");
        e.setBounds(40, 50, 100, 40);
        ActionListener encr = new Encrypt();
        e.addActionListener(encr);
        JButton d = new JButton("Decrypt");
        d.setBounds(40, 110, 100, 40);
        ActionListener decr  = new Decrypt();
        d.addActionListener(decr);

        JButton open = new JButton("Open");
        open.setBounds(180, 50, 150, 40);
        ActionListener menu = new Menu();
        open.addActionListener(menu);

        file = new JLabel("Nothing Selected!");
        file.setBounds(185, 110, 150, 40);

        JLabel pass = new JLabel("Password:");
        pass.setBounds(55, 190, 100, 20);
        pass_wd = new JTextField("");
        pass_wd.setBounds(180, 190, 150, 20);

        f.add(e);
        f.add(d);
        f.add(open);
        f.add(pass_wd);
        f.add(file);
        f.add(pass);

        f.setVisible(true);
    }
}

class Menu implements ActionListener{
    static File store;
    static String store_name;
    @Override
    public void actionPerformed(ActionEvent me){
        JFileChooser fc = new JFileChooser("d:");
        int r = fc.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION)
        {
            Frame.file.setText(fc.getSelectedFile().getName());
            store = fc.getSelectedFile().getAbsoluteFile();
            store_name = fc.getSelectedFile().getAbsolutePath();
        }
        else
            Frame.file.setText("the user cancelled the operation");
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Frame F = new Frame();
        F.structure();
    }
}
