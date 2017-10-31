package stock.cryptodocmarket.Fragment;

/**
 * Created by Administrator on 15-08-2017.
 */

public class NewsData {
    String image,title,pubDate,header,description,link;

    public NewsData(String image, String title, String pubDate, String header, String description, String link) {
        this.image = image;
        this.title = title;
        this.pubDate = pubDate;
        this.header = header;
        this.description = description;
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
