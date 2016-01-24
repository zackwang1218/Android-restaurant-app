package com.example.zackwang.testapp.utility;

import android.util.Xml;

import com.example.zackwang.testapp.entity.Reservation;
import com.example.zackwang.testapp.entity.User;
import com.example.zackwang.testapp.tools.ReservationDTO;
import com.example.zackwang.testapp.tools.Table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Zack Wang on 2015/10/4.
 */
public class XmlHandler {

    public String MakeLoginXml(User user) {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("utf-8", true);

            serializer.startTag("", "login");
            serializer.startTag("", "data");

            serializer.startTag("", "username");
            serializer.text(user.getUsername());
            serializer.endTag("", "username");

            serializer.startTag("", "password");
            String encryptPassword = hash256(user.getPassword());
            serializer.text(encryptPassword);
            serializer.endTag("", "password");

            serializer.endTag("", "data");
            serializer.endTag("", "login");

            serializer.endDocument();

        } catch (IllegalArgumentException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public String MakeSignUpXml(User user) {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("utf-8", true);

            serializer.startTag("", "signup");
            serializer.startTag("", "data");

            serializer.startTag("", "username");
            serializer.text(user.getUsername());
            serializer.endTag("", "username");

            serializer.startTag("", "password");
            String encryptPassword = hash256(user.getPassword());
            serializer.text(encryptPassword);
            serializer.endTag("", "password");

            serializer.startTag("", "email");
            serializer.text(user.getEmail());
            serializer.endTag("", "email");

            serializer.startTag("", "title");
            serializer.text(user.getTitle());
            serializer.endTag("", "title");

            serializer.startTag("", "fullname");
            serializer.text(user.getFullname());
            serializer.endTag("", "fullname");

            serializer.startTag("", "mobile");
            serializer.text(user.getPhone());
            serializer.endTag("", "mobile");

            serializer.endTag("", "data");
            serializer.endTag("", "signup");

            serializer.endDocument();

        } catch (IllegalArgumentException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO: handle exception
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public String makeBookingEnqueryXml(Reservation reservation) {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("utf-8", true);

            serializer.startTag("", "booking");
            serializer.startTag("", "data");

            serializer.startTag("", "userid");
            serializer.text(reservation.getUsername());
            serializer.endTag("", "userid");

            serializer.startTag("", "anoymousID");
            if (reservation.getAnonymousID() != null) {
                serializer.text(reservation.getAnonymousID());
            } else {
                serializer.text("");
            }
            serializer.endTag("", "anoymousID");

            serializer.startTag("", "selectday");
            serializer.text(reservation.getDate());
            serializer.endTag("", "selectday");

            serializer.startTag("", "section");
            serializer.text(reservation.getSection());
            serializer.endTag("","section");

            serializer.startTag("", "time");
            serializer.text(reservation.getTime());
            serializer.endTag("", "time");

            serializer.startTag("", "duration");
            serializer.text(reservation.getDuration());
            serializer.endTag("", "duration");

            serializer.startTag("", "numberofpeople");
            serializer.text(reservation.getNumOfPeople());
            serializer.endTag("", "numberofpeople");

            serializer.endTag("", "data");
            serializer.endTag("", "booking");

            serializer.endDocument();

        } catch (IllegalArgumentException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO: handle exception
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public String ReadLoginFeedBack(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(inputStream);

        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("data");
        Node nNode = nList.item(0);
        Element eElement = (Element) nNode;
        String flag = eElement.getElementsByTagName("flag").item(0).getTextContent();
        return flag;

    }

    public String ReadRegisterFeedBack(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(inputStream);

        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("data");
        Node nNode = nList.item(0);
        Element eElement = (Element) nNode;
        String flag = eElement.getElementsByTagName("flag").item(0).getTextContent();
        return flag;
    }

    public ReservationDTO ReadBookingSearchResult(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(inputStream);

        doc.getDocumentElement().normalize();
        NodeList nListData = doc.getElementsByTagName("data");
        Node nNodeData = nListData.item(0);
        Element eElementData = (Element) nNodeData;
        String flag = eElementData.getElementsByTagName("flag").item(0).getTextContent();

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setResultFlag(flag);
        if (flag.equals("true")){
            Element nNodeTables = (Element) eElementData.getElementsByTagName("tables").item(0);
            NodeList nodeListTables = nNodeTables.getElementsByTagName("table");
            for (int i = 0; i < nodeListTables.getLength(); i++) {
                Element elementTable = (Element) nodeListTables.item(i);
                //String section = elementTable.getElementsByTagName("section").item(0).getTextContent();
                int tableid = Integer.parseInt(elementTable.getElementsByTagName("tableid").item(0).getTextContent());
                Table table = new Table();
                table.setId(tableid);
                //table.setSection(section);
                reservationDTO.getTableList().add(table);
            }
        }
        return reservationDTO;
        //return reservationDTO;
    }

    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
