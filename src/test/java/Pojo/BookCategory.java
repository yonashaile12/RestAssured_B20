package Pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

// we want to be able to name the fields any name we want
// rather than being limited to use same name as json object key
// but we need to tell Jackson data-bind
// which json key map to which pojo class field
// we use annotation @JsonProperties for this
public class BookCategory {

    @JsonProperty("id")
    private String category_id;
    @JsonProperty("name")
    private String category_name;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Override
    public String toString() {
        return "BookCategory{" +
                "id='" + category_id + '\'' +
                ", name='" + category_name + '\'' +
                '}';
    }
}
