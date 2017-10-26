import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by kattis on 10/03/17.
 */
public class GUI extends JFrame
{
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 300;

    private Register register;

    private JPanel startPanel;
    private JPanel memberPanel;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField districtField;
    private JTextField postalField;
    private JTextField phoneField;
    private JList memberList;
    private JTextField memberField = new JTextField();

    private final int NAME_LENGTH = 15;

    public static void main(String[] args)
    {
        GUI frame = new GUI();
        frame.setVisible(true);
    }

    public GUI()
    {
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        createComponents();
        setTitle("Members register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createComponents()
    {
        register = new Register();

       String message;
        try
        {
            register.init();
        }
        catch(FileNotFoundException fe)
        {
           
        }
        catch(IOException e)
        {
            message = "Det gick inte läsa från filen. Kontakta IT stöd. /n Programmet avslutas.";
            register.showLogError(message);
            System.exit(1); // avsluta programmet
        }
        catch (ClassNotFoundException e)
        {
            message = "Objektet du söker(kund) hittades inte. Kontakta IT stöd./n Programmet avslutas.";
            register.showLogError(message);
            System.exit(1);
        }

        startPanel= new JPanel(new BorderLayout());
        startPanel.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));

        memberPanel = new JPanel(new BorderLayout());
        memberPanel.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));

        JPanel framePanel = new JPanel();

        createStartPanel();
        framePanel.add(startPanel);
        createMemberPanel();
        framePanel.add(memberPanel);
        add(framePanel);
        pack();

        memberList.setListData(register.getAllPersons().toArray());
    }

    private void createStartPanel()
    {
        ActionListener listener = new ClickStartListener();

        // Skapar upp topPanel med 1 rad och två kolumner
        JPanel topPanel = new JPanel(new GridLayout(1,2));
        // Skapar upp leftPanel med 5 rader och 1 kolumn
        JPanel leftPanel = new JPanel(new GridLayout(5,1));

        // Skapar och lägger till knappar
        JButton addButton = new JButton("Add");
        addButton.addActionListener(listener);
        leftPanel.add(addButton);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(listener);
        leftPanel.add(removeButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(listener);
        leftPanel.add(searchButton);

        JButton printButton = new JButton("Print");
        printButton.addActionListener(listener);
        leftPanel.add(printButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(listener);
        leftPanel.add(exitButton);

        // Lägger till leftPanel till topPanel
        topPanel.add(leftPanel);

        // Skapar och lägger till JList personList
        memberList = new JList();
        memberList.setBorder(BorderFactory.createTitledBorder("Members"));

        // Anonym lyssnare som hanterar vad som händer när vi klickar på en person i listan
        memberList.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent evt)
            {
                getMemberInfo();
            }
        });

        // Lägger till JList personList till topPanel
        topPanel.add(memberList);

        // Lägger till topPanel till personPanel
        startPanel.add(topPanel);

        // Lägger till personTextLabel längst ner i personPanel
       // personPanel.add(personTextLabel, BorderLayout.SOUTH);

        // Denna panel ska visas när programmet startar
        startPanel.setVisible(true);
    }

    private void createMemberPanel()
    {
        memberPanel = new JPanel(new BorderLayout());
        memberPanel.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
        ActionListener listener = new ClickMemberListener();
        //Skapar en panel som får en label och fält.
        JPanel panel = new JPanel(new GridLayout(7,1));

        nameField = new JTextField(NAME_LENGTH);
        nameField.setBorder(BorderFactory.createTitledBorder("Name"));
        panel.add(nameField);

        addressField = new JTextField(NAME_LENGTH);
        addressField.setBorder(BorderFactory.createTitledBorder("Address"));
        panel.add(addressField);

        districtField = new JTextField(NAME_LENGTH);
        districtField.setBorder(BorderFactory.createTitledBorder("District"));
        panel.add(districtField);

        postalField = new JTextField(NAME_LENGTH);
        postalField.setBorder(BorderFactory.createTitledBorder("Postal code"));
        panel.add(postalField);

        phoneField = new JTextField(NAME_LENGTH);
        phoneField.setBorder(BorderFactory.createTitledBorder("Phonenumber"));
        panel.add(phoneField);

        JButton okButton = new JButton("Add");
        okButton.addActionListener(listener);
        panel.add(okButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(listener);
        panel.add(backButton);

        memberPanel.add(panel, BorderLayout.WEST);

        memberPanel.setVisible(false);
    }


    private class ClickStartListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String buttonText = e.getActionCommand();
            int position = memberList.getSelectedIndex();

            if(buttonText.equals("Add"))
            {
                startPanel.setVisible(false);
                memberPanel.setVisible(true);
            }
            if(buttonText.equals("Remove"))
            {
                if (position > -1)
                {
                    int idNr = getIdNr();
                    Member member = register.searchMember(idNr);
                    if(member!= null)
                    {
                        register.removeMember(idNr);
                        JOptionPane.showMessageDialog(null, "The member " + idNr + " has been removed.\n" );
                        // Ser till så att testpersonerna syns i JList
                        memberList.setListData(register.getAllPersons().toArray());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "The member was not found");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Click on a member!");
                }
            }
            if(buttonText.equals("Search"))
            {
                String search = JOptionPane.showInputDialog("What the member number:");
                int idNr = Integer.parseInt(search);
                Member member = register.searchMember(idNr);
                if(member!= null)
                {
                    JOptionPane.showMessageDialog(null, register.printInfoMember(member));
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "The member was not found");
                }
            }
            if(buttonText.equals("Print"))
            {
                JOptionPane.showMessageDialog(null, register.memberInfo());
            }
            if(buttonText.equals("Exit"))
            {
                //System.exit(0);
                try
                {
                    register.saveMembers();
                    System.exit(0);
                }
                catch (IOException er)
                {
                    register.showLogError("We couldn't write to the file reg.dat");
                    System.exit(1);

                }
            }


        }
    }

    private class ClickMemberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String buttonText = e.getActionCommand();

            if(buttonText.equals("Add"))
            {
                String name = nameField.getText();
                String address = addressField.getText();
                String district = districtField.getText();
                int postal = Integer.parseInt(postalField.getText());
                int phone = Integer.parseInt(phoneField.getText());
                register.addMember(name, address, district, postal, phone);
                memberList.setListData(register.getAllPersons().toArray());
            }
            if(buttonText.equals("Back"))
            {
                memberPanel.setVisible(false);
                startPanel.setVisible(true);
            }
        }
    }

    private void getMemberInfo()
    {
        // Index till person som markerats i personList
        int position = memberList.getSelectedIndex();
        // Endast om man valt en person
        if(position > -1)
        {
            // Gå via logic för att hämta ut info om personen
            String info = register.getMemberInfo(position);
            // Sätter info till personTextLabel
            //personTextLabel.setText(info);
            // Sätter info även till personField
            //personField.setText(info);
        }
    }

    private int getIdNr()
    {
        int idNr = 0;
        int position = memberList.getSelectedIndex();

        if(position > -1 )
        {
            idNr = register.getIdNr(position);
        }
        return idNr;
    }


}
