import javax.swing.*;
import java.awt.*;

public class URLShortenerGUI extends JFrame {

    private JTextField inputField;
    private JTextField outputField;
    private JButton encodeButton;
    private JButton decodeButton;

    // Map of base-62
    static char[] map = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public URLShortenerGUI() {
        setTitle("URL Shortener");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 5));

        inputField = new JTextField();
        outputField = new JTextField();
        outputField.setEditable(false);

        encodeButton = new JButton("Encode");
        decodeButton = new JButton("Decode");

        add(new JLabel("Input (ID or Short URL):"));
        add(inputField);
        add(encodeButton);
        add(decodeButton);
        add(new JLabel("Output:"));
        add(outputField);
        //Encode
        encodeButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(inputField.getText().trim());
                String shortUrl = idToShortURL(id);
                outputField.setText(shortUrl);
            } catch (NumberFormatException ex) {
                outputField.setText("Invalid integer ID!");
            }
        });
        //Decode
        decodeButton.addActionListener(e -> {
            String shortUrl = inputField.getText().trim();
            int id = shortURLtoID(shortUrl);
            outputField.setText(String.valueOf(id));
        });

        setVisible(true);
    }
    static String idToShortURL(int n) {
        StringBuilder shorturl = new StringBuilder();
        while (n > 0) {
            shorturl.append(map[n % 62]);
            n = n / 62;
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
        }
        return id;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(URLShortenerGUI::new);
    }
}
