package jp.artonv.android.twittertlclassify;

//お気に入りツイート表示用データクラス
public class FavoriteTweetCardData {
    private String owner; //ユーザー名
    private String category; //カテゴリ
    private String sentence; //つぶやき本文

    private int categoryID; //カテゴリID

    public FavoriteTweetCardData(String owner,String category, String sentence, int categoryID){
        this.owner = owner;
        this.category = category;
        this.sentence = sentence;
        this.categoryID = categoryID;
    }

    public String getOwner() {
        return owner;
    }

    public String getCategory() {
        return category;
    }

    public String getSentence() {
        return sentence;
    }

    public int getCategoryID() { return categoryID; }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setCategoryID(int categoryID) { this.categoryID = categoryID; }
}
