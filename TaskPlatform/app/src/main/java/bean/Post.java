package bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Post extends BmobObject implements Serializable {

    private String title;           //标题
    private String content;         //内容
    private User author;            //作者
    private Boolean acceptable;     //被接收否
    private User accepter;          //作者

    public User getAccepter() {
        return accepter;
    }

    public void setAccepter(User accepter) {
        this.accepter = accepter;
    }

    public Boolean getAcceptable() {
        return acceptable;
    }

    public void setAcceptable(Boolean acceptable) {
        this.acceptable = acceptable;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
