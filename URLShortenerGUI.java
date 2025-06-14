import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class URLShortenerGUI extends JFrame {

    private JTextField inputURLField;
    private JTextField shortCodeField;
    private JTextField outputField;
    private JButton encodeButton;
    private JButton decodeButton;

    private static final char[] map = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final HashMap<Integer, String> idToURLMap = new HashMap<>();
    private static int idCounter = 1;

    public URLShortenerGUI() {
        setTitle("URL Shortener");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        inputURLField = new JTextField();
        shortCodeField = new JTextField();
        outputField = new JTextField();
        outputField.setEditable(false);

        encodeButton = new JButton("Encode URL");
        decodeButton = new JButton("Decode URL");

        add(new JLabel("Enter Long URL to Encode:"));
        add(inputURLField);

        add(encodeButton);
        add(decodeButton);

        add(new JLabel("Enter Short Code to Decode:"));
        add(shortCodeField);

        add(new JLabel("Result:"));
        add(outputField);

        // ENCODE
        encodeButton.addActionListener(e -> {
            String longURL = inputURLField.getText().trim();
            if (longURL.isEmpty()) {
                outputField.setText("Please enter a URL.");
                return;
            }
            int id = idCounter++;
            idToURLMap.put(id, longURL);
            String shortCode = idToShortURL(id);
            outputField.setText("Short URL: " + shortCode);
        });

        // DECODE
        decodeButton.addActionListener(e -> {
            String shortCode = shortCodeField.getText().trim();
            if (shortCode.isEmpty()) {
                outputField.setText("Please enter a short code.");
                return;
            }
            int id = shortURLtoID(shortCode);
            String originalURL = idToURLMap.get(id);
            if (originalURL != null) {
                outputField.setText("Original URL: " + originalURL);
            } else {
                outputField.setText("Short code not found.");
            }
        });

        setVisible(true);
    }

    static String idToShortURL(int n) {
        StringBuilder shorturl = new StringBuilder();
        while (n > 0) {
            shorturl.append(map[n % 62]);
            n /= 62;
        }
        return shorturl.reverse().toString();
    }

    static int shortURLtoID(String shortURL) {
        int id = 0;
        for (int i = 0; i < shortURL.length(); i++) {
            char ch = shortURL.charAt(i);
            if ('a' <= ch && ch <= 'z')
                id = id * 62 + ch - 'a';
            else if ('A' <= ch && ch <= 'Z')
                id = id * 62 + ch - 'A' + 26;
            else if ('0' <= ch && ch <= '9')
                id = id * 62 + ch - '0' + 52;
            else
                return -1; // Invalid character
        }
        return id;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(URLShortenerGUI::new);
    }
}
