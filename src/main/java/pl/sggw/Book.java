package pl.sggw;

public class Book {

    public String title;
    public String name;
    public String surname;
    public String id;

    public Book(String title, String name, String surname, String id){
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public Book(){
        title = "";
        name = "";
        surname = "";
        id = "";
    }

    @Override
    public String toString(){
        return String.format("%s - %s - %s %s", id, title, name, surname);
    }

    public Boolean isEmpty(){
        if(title.equals("") && name.equals("") && surname.equals("") && id.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}