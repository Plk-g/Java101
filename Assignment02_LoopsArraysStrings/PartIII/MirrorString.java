public class MirrorString {

    // Method 
    public static String makeMirror(String text) {
        StringBuilder rev = new StringBuilder();

        for (int i = text.length() - 1; i >= 0; i--) {
            rev.append(text.charAt(i));
        }

        return text + rev.toString();
    }
    public static void main(String[] args) {
        String sample = "hello";
        String mirrored = makeMirror(sample);

        System.out.println("Mirror string of \"" + sample + "\" is: " + mirrored);
    }
}
