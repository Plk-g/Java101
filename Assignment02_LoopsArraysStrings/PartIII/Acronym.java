public class Acronym {

    // Method 
    public String makeAcronym(String text) {
        StringBuilder shortForm = new StringBuilder();
        String[] words = text.split(" ");
        
        for (String w : words) {
            if (!w.isEmpty()) {
                char ch = Character.toUpperCase(w.charAt(0));
                shortForm.append(ch);
            }
        }
        return shortForm.toString();
    }
    public static void main(String[] args) {
        Acronym helper = new Acronym();

        String first = "as soon as possible";
        String second = "The quick brown fox jumps over the lazy dog";

        System.out.println("Acronym of \"" + first + "\" is: " + helper.makeAcronym(first));
        System.out.println("Acronym of \"" + second + "\" is: " + helper.makeAcronym(second));
    }
}
