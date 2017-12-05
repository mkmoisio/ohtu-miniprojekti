
package domain;

public class Tag {
    
    private int tag_id;
    private String tag_nimi;

    public Tag(String tag_nimi) {
        this.tag_nimi = tag_nimi;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_nimi() {
        return tag_nimi;
    }

    public void setTag_nimi(String tag_nimi) {
        this.tag_nimi = tag_nimi;
    }
    
    
    
}
