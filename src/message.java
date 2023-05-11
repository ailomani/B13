import com.sun.tools.javac.Main;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
public class message extends JFrame{
    private JTextArea c1, c2, c3, c4, c5;
private JLabel l1, l2, l3, l4, l5;
    private JButton enc, t;
    public message() {

        JFrame frame = new JFrame();
        setSize(getMaximumSize());
        JPanel panel = new JPanel();
        panel.setBackground(new Color(216, 191, 216));

        JLabel l1 = new JLabel("Cipher text 1: ");
        l1.setPreferredSize(new Dimension(1000,30));
        l1.setFont(new Font("Times New Roman",Font.BOLD,18));
        JLabel l2 = new JLabel("Cipher text 2: ");
        l2.setPreferredSize(new Dimension(1000,30));
        l2.setFont(new Font("Times New Roman",Font.BOLD,18));
        JLabel l3 = new JLabel("Cipher text 3: ");
        l3.setPreferredSize(new Dimension(1000,30));
        l3.setFont(new Font("Times New Roman",Font.BOLD,18));
        JLabel l4 = new JLabel("Cipher text 4: ");
        l4.setPreferredSize(new Dimension(1000,30));
        l4.setFont(new Font("Times New Roman",Font.BOLD,18));
        JLabel l5 = new JLabel("Cipher text 5: ");
        l5.setPreferredSize(new Dimension(1000,30));
        l5.setFont(new Font("Times New Roman",Font.BOLD,18));

        JTextArea lc1 = new JTextArea();
        lc1.setPreferredSize(new Dimension(1000,60));
        lc1.setLineWrap(true);
        JTextArea lc2 = new JTextArea();
        lc2.setPreferredSize(new Dimension(1000,60));
        lc2.setLineWrap(true);
        JTextArea lc3 = new JTextArea();
        lc3.setPreferredSize(new Dimension(1000,60));
        lc3.setLineWrap(true);
        JTextArea lc4 = new JTextArea();
        lc4.setPreferredSize(new Dimension(1000,60));
        lc4.setLineWrap(true);
        JTextArea lc5 = new JTextArea();
        lc5.setPreferredSize(new Dimension(1000,60));
        lc5.setLineWrap(true);

        JButton enc = new JButton("Encryption");
        enc.setPreferredSize(new Dimension(300,30));

        JButton t = new JButton("Trapdoor");
        t.setPreferredSize(new Dimension(300,30));

        // code starts here
        Pairing pairing = PairingFactory.getPairing("params.properties");
        PairingFactory.getInstance().setUsePBCWhenPossible(true);

        // initiate p
        Element P = pairing.getG1().newRandomElement().getImmutable();
        System.out.println("P is :" + P);

        // initiate g
        Element g = pairing.getG1().newRandomElement().getImmutable();
        System.out.println("g is :" + g);

        Element x1 = pairing.getZr().newRandomElement().getImmutable();
        Element x2 = pairing.getZr().newRandomElement().getImmutable();
        Element x3 = pairing.getZr().newRandomElement().getImmutable();
        Element x4 = pairing.getZr().newRandomElement().getImmutable();
            /*
            // public key generation
               Element pr- public key of Receiver and ps- public key of sender */

        Element pr1 = g.powZn(x1);
        System.out.println("public key1: " + pr1);
        Element pr2 = g.powZn(x2);
        System.out.println("public key2: " + pr2);
        Element pr3 = g.powZn(x3);
        System.out.println("public key3: " + pr3);

        // Skr1= x1,skr2=x2,skr3=x3 and skr4=x4 are the serect keys
        Element skr1 = x1;
        Element skr2 = x2;
        Element skr3 = x3;
        Element skr4 = x4;

        // Key Generation for receiver  - ends here.

        // Key Generation for Sender  - Starts here.
        // y is the Secret key of sender.
        Element y = pairing.getZr().newRandomElement().getImmutable();

        // public key of Sender is pks
        Element pks = g.powZn(y);
        System.out.println("public key Sender: " + pks);

        // Key Generation for Sender  - Ends here.

        // Keyword Encryption Starts here
        /* Random number for powers */
        Element r1 = pairing.getZr().newRandomElement().getImmutable();
        Element r2 = pairing.getZr().newRandomElement().getImmutable();

        /* Encryption*/
        Element h1_hash_int = pairing.getZr().newRandomElement().getImmutable();
        Element c1 = (pr2.powZn(h1_hash_int).add(pr3)).powZn(r1);

        Element c2 = g.powZn(r1);

        Element h2_hash_int = pairing.getZr().newRandomElement().getImmutable();
        Element h3_hash_int = pairing.getZr().newRandomElement().getImmutable();
        Element c3 = ((pr2.powZn(h2_hash_int).add(pr3)).powZn(r2)).add(g.powZn(h3_hash_int.mul(r1)));

        Element c4_hash_point = pairing.getG1().newRandomElement().getImmutable();
        Element c4 = c4_hash_point.powZn(r2);

        Element c5_hash_point = pairing.getG1().newRandomElement().getImmutable();
        Element c5 = c5_hash_point.powZn(r1);

        System.out.println("c1 is : " + c1);
        System.out.println("c2 is : " + c2);
        System.out.println("c3 is : " + c3);
        System.out.println("c4 is : " + c4);
        System.out.println("c5 is : " + c5);

        enc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lc1.setText(" Chiper Text1 is "+c1.toString());
                lc2.setText(" Chiper Text2 is "+c2.toString());
                lc3.setText(" Chiper Text3 is "+c3.toString());
                lc4.setText(" Chiper Text4 is "+c4.toString());
                lc5.setText(" Chiper Text5 is "+c5.toString());
            }
        });
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trapdoor trw = new trapdoor();
                trw.setVisible(true);
            }
        });

        panel.add(l1);
        panel.add(lc1);
        panel.add(l2);
        panel.add(lc2);
        panel.add(l3);
        panel.add(lc3);
        panel.add(l4);
        panel.add(lc4);
        panel.add(l5);
        panel.add(lc5);
        panel.add(enc);
        panel.add(t);

        add(panel);
        panel.setVisible(true);
        setVisible(true);

        // Keyword Encryption - ends here
    }
    // Code ends here

    }
