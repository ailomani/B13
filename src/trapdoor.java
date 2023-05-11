import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class trapdoor extends JFrame {
    private JLabel send, admin;
    private JTextArea t1, t2, t3, ut1, ut2, t4;

    private JButton tr, test, ut, test1;

    public trapdoor() {
        JFrame frame = new JFrame();
        setSize(getMaximumSize());
        JPanel panel = new JPanel();
        panel.setBackground(Color.pink);

        JLabel send = new JLabel("Sender");
        send.setPreferredSize(new Dimension(600, 60));
        send.setFont(new Font("Times New Roman",Font.BOLD,26));

        JLabel receiver = new JLabel("Receiver");
        receiver.setPreferredSize(new Dimension(600, 60));
        receiver.setFont(new Font("Times New Roman",Font.BOLD,26));


        JTextArea t1 = new JTextArea();
        t1.setPreferredSize(new Dimension(600, 80));
        t1.setLineWrap(true);

        JTextArea t2 = new JTextArea();
        t2.setPreferredSize(new Dimension(600, 80));
        t2.setLineWrap(true);

        JTextArea t3 = new JTextArea();
        t3.setPreferredSize(new Dimension(600, 80));
        t3.setLineWrap(true);

        JButton tr = new JButton("Trapdoor");
        tr.setPreferredSize(new Dimension(300, 20));

        JButton test = new JButton("Test ");
        test.setPreferredSize(new Dimension(300, 20));

        // first test over

        JButton ut = new JButton("Updated Trapdoor");
        ut.setPreferredSize(new Dimension(300, 20));

        JTextArea ut1 = new JTextArea();
        ut1.setPreferredSize(new Dimension(600, 80));
        ut1.setLineWrap(true);

        JTextArea ut2 = new JTextArea();
        ut2.setPreferredSize(new Dimension(600, 80));
        ut2.setLineWrap(true);

        JTextArea t4 = new JTextArea();
        t4.setPreferredSize(new Dimension(600, 80));
        t4.setLineWrap(true);

        JButton test1 = new JButton("Updated Test ");
        test1.setPreferredSize(new Dimension(300, 20));

        Pairing pairing = PairingFactory.getPairing("params.properties");
        PairingFactory.getInstance().setUsePBCWhenPossible(true);
        // initiate p
        Element P = pairing.getG1().newRandomElement().getImmutable();
        System.out.println("P is :" + P);

        // initiate g
        Element g = pairing.getG1().newRandomElement().getImmutable();
        System.out.println("g is :" + g);

        // Key Generation for receiver  - Starts here
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

        // Keyword Encryption - ends here

        //Trapdoor Generation Starts here

        Element r3 = pairing.getZr().newRandomElement().getImmutable();
        //Element h1_trapdoor_hash_int = pairing.getZr().newRandomElement().getImmutable();
        // Element tw1pow = r3.div((skr2.mul(h1_trapdoor_hash_int).add(x3)));
        Element tw1pow = r3.mul((skr2.mul(h1_hash_int).add(x3)).invert());
        Element Tw1 = g.powZn(tw1pow);
        System.out.println("Tw1 is :" + Tw1);
        Element Tw2 = g.powZn(r3);
        System.out.println("Tw2 is :" + Tw2);

        // Trapdoor generation ends here
        // Test
        Element e1 = pairing.pairing(c1, Tw1);
        Element e2 = pairing.pairing(c2, Tw2);
        System.out.println("e1 is : " + e1);
        System.out.println("e2 is : " + e2);
        // update key generation starts here
        Element upd_h3_hash_point = pairing.getZr().newRandomElement().getImmutable();
        Element upd_h2_hash_point = pairing.getZr().newRandomElement().getImmutable();
        Element uks1 = upd_h3_hash_point;
        System.out.println("Updated Key generation1 is : " + uks1);
        //Element uks2_div= (x2.mul(upd_h2_hash_point).add(x3).invert());
        Element uks2 = x4.mul((x2.mul(upd_h2_hash_point).add(x3).invert()));
        System.out.println("Updated Key generation2 is : " + uks2);

        // update key generation ends here

        // UC is Update Encryption starts here
        Element H = pairing.getG1().newRandomElement().getImmutable();
        Element UC1 = pairing.pairing(H, c2);
        System.out.println("update enc1 :" + UC1);
        Element UC2 = pairing.pairing(c5, g);
        System.out.println("update enc2 :" + UC2);
        if (UC1.isEqual(UC2)) {
            System.out.println("Tampered by adversaries");
        } else {
            Element c6 = g.powZn(r2.mul(x4));
            System.out.println("c6 is " + c6);
            Element C = pairing.getG1().newRandomElement().getImmutable();
            Element UC = pairing.pairing(C, c6);
            System.out.println("Updated Encryption is : " + UC);

            // UC is Update Encryption ends here

            //Constant Trapdoor generation starts here
            Element r = pairing.getZr().newRandomElement().getImmutable();
            Element utw1 = g.powZn(x4.mul(r));
            System.out.println("Updated Trapdoor1 :" + utw1);
            Element utw2 = c4_hash_point.powZn(r);
            System.out.println("Updated Trapdoor2 :" + utw2);

            //Constant Trapdoor generation ends here

            //updated test starts here
            Element UE1 = pairing.pairing(c4, utw1);
            System.out.println("UE1 is : " + UE1);
            Element UE2 = pairing.pairing(c6, utw2);
            System.out.println("UE2 is : " + UE2);

            tr.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    t1.setText(" Trapdoor1  is " + Tw1.toString());
                    t2.setText(" Trapdoor2 is " + Tw2.toString());
                }
            });
            test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e1.isEqual(e2)) {
                    System.out.println("Test Matched"+UE1);
                    t3.setText(e1.toString());
                } else {
                    t3.setText("Test is not matched");
                }
            }
        });

            ut.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ut1.setText(" Trapdoor1  is " + utw1.toString());
                    ut2.setText(" Trapdoor2 is " + utw2.toString());
                }
            });

            test1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ut1.setText(" Trapdoor1  is " + utw1.toString());
                    ut2.setText(" Trapdoor2 is " + utw2.toString());
                    if (UE1.isEqual(UE2)) {
                        t4.setText(UE1.toString());
                    } else {
                        System.out.println("Updated test not matched");
                    }
                }
                });
            panel.add(send);
            panel.add(receiver);
            panel.add(t1);
            panel.add(ut1);
            panel.add(t2);
            panel.add(ut2);
            panel.add(t3);
            panel.add(t4);
            panel.add(tr);
            panel.add(test);
            panel.add(ut);
            panel.add(test1);


            add(panel);
            panel.setVisible(true);
            setVisible(true);
        }
    }
}