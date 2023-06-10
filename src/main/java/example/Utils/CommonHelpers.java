package example.Utils;

public class CommonHelpers {

    public String getUriParsed(String uri, String value){
       String newPath = uri.replace("{$}",value);
        System.out.println("New Path " + newPath);
        return newPath;
    }
}
