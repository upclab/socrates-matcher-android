package pe.edu.upc.mobile.XMLParser;

import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import pe.edu.upc.mobile.Entities.Evento;
import pe.edu.upc.mobile.Entities.Noticia;

public class XMLParser {
    public List<Noticia> parseNoticias(InputStream is) throws Exception {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        NoticiaHandler handler = new NoticiaHandler();
        parser.parse(is, handler);
        return handler.getNews();
    }

    public List<Evento> parseEventos(InputStream is) throws Exception {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        EventoHandler handler = new EventoHandler();
        parser.parse(is, handler);
        return handler.getNews();
    }
}
