package pe.edu.upc.mobile.XMLParser;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pe.edu.upc.mobile.Entities.Evento;

public class EventoHandler extends DefaultHandler {
    static final String DESCRIPTION = "description";
    static final String ITEM = "item";
    static final String LINK = "link";
    static final String PUB_DATE = "pubDate";
    static final String TITLE = "title";
    private Evento evento;
    private List<Evento> eventosList;
    private StringBuilder stringBuilder;

    public List<Evento> getNews() {
        return this.eventosList;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        this.stringBuilder.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (this.evento != null) {
            if (localName.equalsIgnoreCase(TITLE)) {
                this.evento.setTitle(this.stringBuilder.toString().trim());
            } else if (localName.equalsIgnoreCase(LINK)) {
                this.evento.setLink(this.stringBuilder.toString().trim());
            } else if (localName.equalsIgnoreCase(DESCRIPTION)) {
                this.evento.setDescription(this.stringBuilder.toString().trim());
            } else if (localName.equalsIgnoreCase(PUB_DATE)) {
                this.evento.setPubDate(this.stringBuilder.toString().trim());
            } else if (localName.equalsIgnoreCase(ITEM)) {
                this.eventosList.add(this.evento);
            }
        }
        this.stringBuilder.setLength(0);
    }

    public void startDocument() throws SAXException {
        super.startDocument();
        this.eventosList = new ArrayList();
        this.stringBuilder = new StringBuilder();
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase(ITEM)) {
            this.evento = new Evento();
        }
    }
}
