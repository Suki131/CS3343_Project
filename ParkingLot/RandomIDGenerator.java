
public class RandomIDGenerator {
    public static String generateID(int length) {
        String characters = "0123456789";
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            id.append(characters.charAt(index));
        }
        return id.toString();
    }
}
