package com.pfe.bls;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 

public class Xml_a_envoyer extends MainActivity {
	

    public static void cree() {
    	
    	 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder;
         try {
             dBuilder = dbFactory.newDocumentBuilder();
             Document doc = dBuilder.newDocument();
        	

            //add elements to Document
            Element rootElement =
                doc.createElement("informations");
            //append root element to document
            doc.appendChild(rootElement);
          //append first child element to root element
           
          
             
                
            
            
            rootElement.appendChild(getElements(doc, rootElement, "nom", nom_application));
            rootElement.appendChild(getElements(doc, rootElement, "description", description_application));
            rootElement.appendChild(getElements(doc, rootElement,"api", api));

 
            rootElement.appendChild(getElements(doc, rootElement,"messagerie", string_messagerie));
            //append truism  child
            rootElement.appendChild(getElements(doc,rootElement, "repertoire", string_repertoire));
            //append second child
            rootElement.appendChild(getElements(doc, rootElement,"calculatrice", string_calculatrice));
            rootElement.appendChild(getElements(doc, rootElement,"calendrier", string_calendrier));

            rootElement.appendChild(getElements(doc, rootElement,"mms", string_mms));
            rootElement.appendChild(getElements(doc, rootElement, "telephone",string_phone));
            rootElement.appendChild(getElements(doc, rootElement, "galerie",string_gallery));

            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
 
            //write to console or file
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File("/data/data/com.pfe.bls/informations.xml"));
 
            //write data
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("DONE");
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Node getElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
   }
   
	
}
