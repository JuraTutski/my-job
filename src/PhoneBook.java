import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class PhoneBook {
    private Map<String, List<String>> phoneBook = new HashMap<>();

    public void add(String surname, String phoneNumber){
        if (phoneBook.containsKey(surname)){
            phoneBook.get(surname).add(phoneNumber);
        } else {
            List<String> phones = new ArrayList<>();
            phones.add(phoneNumber);
            phoneBook.put(surname, phones);

        }
    }
    public List<String> get(String surname){
        return phoneBook.getOrDefault(surname, new ArrayList<>());
    }

    public void printAll(){
        for (Map.Entry<String, List<String>> entry : phoneBook.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }

}
