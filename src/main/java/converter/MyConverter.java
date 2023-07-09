package converter;

public class MyConverter {
    public String toJson(Object object) {
        if(null == object) {
            return "";
        }
        return "{}";
    }
}
