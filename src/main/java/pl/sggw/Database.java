package pl.sggw;

import java.util.ArrayList;
import java.util.List;

public class Database {
    ArrayList<Book> books;

    public Database(){
        books = new ArrayList<Book>();
    }
    public String getBooks(){
        if(!books.isEmpty()){
            String result ="";
            for(Book b : books){
                result += String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",b.id, b.title, b.name, b.surname);
            }
            return "<table>"+result+"</table>";
        }
        return "Brak książek";
    }

    public String clearDatabase(){
        books.clear();
        return getBooks();
    }

    public String addBook(String title, String name, String surname){
        books.add(new Book(title, name, surname, Integer.toString(books.size())));
        return getBooks();
    }

    public String updateBook(String title, String name, String surname, String id){
        Integer idInt = Integer.parseInt(id);
        Book b = books.get(idInt);
        if(!title.isEmpty())
            b.title = title;
        if(!name.isEmpty())
            b.name = name;
        if(!surname.isEmpty())
            b.surname = surname;
        return getBooks();
    }
}
