import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kattis on 10/03/17.
 */
public class Register
{
    private ArrayList<Member> members = new ArrayList<Member>();
    int lastAssignedId;

    public void addMember(String name, String address, String district, int postal, int phonenumber)
    {
        lastAssignedId++;
        members.add(new Member(lastAssignedId, name,address, district, postal, phonenumber));
    }

    public void removeMember(int idNr)
    {
        Member member = searchMember(idNr);
        if (member != null)
        {
            members.remove(member);
        }
    }

    public Member searchMember(int idNr)
    {
        for(Member member: members)
        {
            if(member.getIdNumber() == idNr)
            {
                return member;
            }
        }
        return null;
    }
    public String printInfoMember(Member member)
    {
        String info = member.printInfoMember();
        return info;
    }

    public void printMembers()
    {
        JOptionPane.showMessageDialog(null, memberInfo());
    }

    public String memberInfo()
    {
        String info = "";
        for(Member member: members)
        {
          info = info + member.getName() + ", " + member.getIdNumber() + "\n";
        }
        return info;
    }

    public ArrayList<Member> getAllPersons()
    {
        return (ArrayList<Member>)members.clone();
    }

    public String getMemberInfo(int position)
    {
        Member member = members.get(position);
        return member.getInfo();
    }

    // File handling
    public void init() throws IOException, ClassNotFoundException
    {
        List<Member> readMembers = readMembers();
        members.clear();
        members.addAll(readMembers);
        List<Integer> allIds = new ArrayList<>();

        for (Member member: members)
        {
            allIds.add(member.getIdNumber());
        }

        if(allIds.isEmpty())
        {
            lastAssignedId = 0;
        }
        else
        {
            lastAssignedId = Collections.max(allIds);
        }

    }
    //Läsning av fil
    private List<Member> readMembers() throws IOException, ClassNotFoundException
    {

        ObjectInputStream inFile = new ObjectInputStream (new FileInputStream("reg.dat"));
        List<Member> readMembers = (List<Member>)inFile.readObject();
        inFile.close();
        return readMembers;
    }

    //Spara fil

    public void saveMembers() throws IOException {

        ObjectOutputStream outFile = new ObjectOutputStream (new FileOutputStream("reg.dat"));
        outFile.writeObject(members);
        outFile.close();
    }

    public void showLogError(String message)
    {
        JOptionPane.showMessageDialog(null, message);
        System.out.println(message);
    }

    public int getIdNr(int position)
    {
       Member member = members.get(position);
        return member.getIdNumber();
    }
}
