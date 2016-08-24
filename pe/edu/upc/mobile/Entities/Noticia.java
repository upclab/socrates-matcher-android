package pe.edu.upc.mobile.Entities;

public class Noticia {
    private String Description;
    private String Link;
    private String PubDate;
    private String Title;

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getLink() {
        return this.Link;
    }

    public void setLink(String link) {
        this.Link = link;
    }

    public String getPubDate() {
        return this.PubDate;
    }

    public void setPubDate(String pubDate) {
        this.PubDate = pubDate;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
}
