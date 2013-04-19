package ua.android;

public interface IDao {

    public void initDB();
    
    public String readAll();

    public String insert(String id, String fName, String lName, String age);

    public String update(String id, String fName, String lName, String age);

    public int deleteById(String id);
}
