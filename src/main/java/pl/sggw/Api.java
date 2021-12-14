package pl.sggw;

import java.util.HashMap;
import java.util.Map;

public class Api {
    Database db;

    public Api(Database db){
        this.db = db;
    }

    public String call(String request, String payload){
        if(request.contains("GET /books.html"))
            return db.getBooks();
        else if(request.contains("GET /manage.html"))
            return "<h2>Formularz czyszczenia bazy danych</h2>\n" +
                    "\n" +
                    "<form action=\"/clearBooksAction\" method=\"POST\">\n" +
                    "  <input type=\"submit\" value=\"Wyczyść bazę danych\">\n" +
                    "</form> ";
        else if(request.contains("POST /clearBooksAction"))
            return db.clearDatabase();
        else if(request.contains("GET /addBook.html"))
            return "<h2>Formularz dodawania książek</h2>\n" +
                    "\n" +
                    "<form action=\"/books.html\"method=\"POST\">\n" +
                    "  <label for=\"fname\">Title:</label><br>\n" +
                    "  <input type=\"text\" id=\"title\" name=\"title\"><br>\n" +
                    "  <label for=\"name\">Author name:</label><br>\n" +
                    "  <input type=\"text\" id=\"name\" name=\"authorName\"><br><br>\n" +
                    "  <label for=\"surname\">Author surname:</label><br>\n" +
                    "  <input type=\"text\" id=\"surname\" name=\"authorSurname\"><br><br>\n" +
                    "  <input type=\"submit\" value=\"Dodaj książkę\">\n" +
                    "</form> ";
        else if(request.contains("POST /books.html"))
        {
            Map<String, String> params = payloadSpliter(payload);
            return db.addBook(params.get("title"),params.get("authorName"),params.get("authorSurname"));
        }
        else if(request.contains("GET /updateBook.html"))
        {
            payload = request.replace("GET /updateBook.html","").replace("?","").replaceAll("\\s.*", "");

            Map<String, String> params = payloadSpliter(payload);
            Book b = db.books.get(Integer.parseInt(params.get("id")));
            return "<h2>Formularz edytowania książki"+b+"</h2>\n" +
                    "\n" +
                    "<form action=\"/updateBookAction\"method=\"POST\">\n" +
                    "  <label for=\"fname\">Title:</label><br>\n" +
                    String.format("  <input type=\"text\" id=\"title\" name=\"title\" placeholder=\"%s\"><br>\n",b.title) +
                    "  <label for=\"name\">Author name:</label><br>\n" +
                    String.format("  <input type=\"text\" id=\"name\" name=\"authorName\" placeholder=\"%s\"><br><br>\n", b.name) +
                    "  <label for=\"surname\">Author surname:</label><br>\n" +
                    String.format("  <input type=\"text\" id=\"surname\" name=\"authorSurname\" placeholder=\"%s\"><br><br>\n", b.surname) +
                    String.format("  <input type=\"hidden\" id=\"id\" name=\"id\" value=\"%s\"><br><br>\n", b.id) +
                    "  <input type=\"submit\" value=\"Edytuj książęk książkę\">\n" +
                    "</form> ";
        }else if(request.contains("POST /updateBookAction"))
        {
            Map<String, String> params = payloadSpliter(payload);
            return db.updateBook(params.get("title"),params.get("authorName"),params.get("authorSurname"),params.get("id"));
        }
        else
            return "404";
    }

    static Map<String, String> payloadSpliter(String payload){
        Map<String, String> map = new HashMap<String, String>();
        String[] pairs = payload.split("&");
        for(String pair : pairs){
            String[] value = pair.split("=");
            map.put(value[0],value.length>1?value[1]:"");
        }
        return map;
    }
}
