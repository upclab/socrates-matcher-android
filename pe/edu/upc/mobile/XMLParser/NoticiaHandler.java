package pe.edu.upc.mobile.XMLParser;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pe.edu.upc.mobile.Entities.Noticia;

public class NoticiaHandler extends DefaultHandler {
    static final String DESCRIPTION = "description";
    static final String ITEM = "item";
    static final String LINK = "link";
    static final String PUB_DATE = "pubDate";
    static final String TITLE = "title";
    private Noticia noticia;
    private List<Noticia> noticiasList;
    private StringBuilder stringBuilder;

    public List<Noticia> getNews() {
        return this.noticiasList;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        this.stringBuilder.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (this.noticia != null) {
            if (localName.equalsIgnoreCase(TITLE)) {
                this.noticia.setTitle(this.stringBuilder.toString().trim());
            } else if (localName.equalsIgnoreCase(LINK)) {
                this.noticia.setLink(this.stringBuilder.toString().trim());
            } else if (localName.equalsIgnoreCase(DESCRIPTION)) {
                this.noticia.setDescription(this.stringBuilder.toString().trim());
            } else if (localName.equalsIgnoreCase(PUB_DATE)) {
                this.noticia.setPubDate(this.stringBuilder.toString().trim());
            } else if (localName.equalsIgnoreCase(ITEM)) {
                this.noticiasList.add(this.noticia);
            }
        }
        this.stringBuilder.setLength(0);
    }

    public void startDocument() throws SAXException {
        super.startDocument();
        this.noticiasList = new ArrayList();
        this.stringBuilder = new StringBuilder();
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase(ITEM)) {
            this.noticia = new Noticia();
        }
    }
}
