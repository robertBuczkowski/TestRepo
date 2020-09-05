import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CRCUI {
    private JPanel panel1;
    private JTextField messageText;
    private JTextField dividerText;
    private JTextField reminderText;
    private JTextField kodCRCText;
    private JButton obliczButton;
    private JButton weryfikujButton;
    private JButton wyczyscButton;
    private JButton wygenerujKodCRCButton;
    private JTextField sentMesageText;
    private JFormattedTextField wynik;
    private CRC crcHelper = new CRC();

    private CRCUI() {
        //oblicz
        obliczButton.addActionListener(e -> {
            try {
                if(StringValidation.validateString((messageText.getText())))
                    JOptionPane.showMessageDialog(panel1, "Podaj wiadomość w postaci binarnej");
                else if (StringValidation.validateString(dividerText.getText()))
                    JOptionPane.showMessageDialog(panel1, "Podaj wielomian w postaci binarnej.");
                else if (dividerText.getText().length() >= messageText.getText().length())
                    JOptionPane.showMessageDialog(panel1, "Wiadomość musi być dłuższa od wielomianu.");
                else
                reminderText.setText(crcHelper.printRemainder(crcHelper.divide(crcHelper.inputMessage(messageText.getText()), crcHelper.inputDivisor(dividerText.getText()))));
            }catch (NumberFormatException x){
                JOptionPane.showMessageDialog(panel1, "Podano niekompletne dane dane, spróbuj ponownie");
            }
        });
        //wygeneruj
        wygenerujKodCRCButton.addActionListener(e -> {
            try {
                if (reminderText.getText().length() == 0)
                    JOptionPane.showMessageDialog(panel1, "Aby wygenerować kod CRC musisz najpierw obliczyć resztę.");
                else{
                kodCRCText.setText(Arrays.toString(crcHelper.generateCRC(crcHelper.inputMessage(messageText.getText()), crcHelper.divide(crcHelper.inputMessage(messageText.getText()), crcHelper.inputDivisor(dividerText.getText())))));  //XD
                sentMesageText.setText((crcHelper.generateMessage(crcHelper.inputMessage(messageText.getText()), crcHelper.divide(crcHelper.inputMessage(messageText.getText()), crcHelper.inputDivisor(dividerText.getText())))));
                }
            }catch (NumberFormatException x){
                JOptionPane.showMessageDialog(panel1, "Przed generacją CRC wprowadź wiadomość i wielomian");
            }
        });
        //weryfikuj
        weryfikujButton.addActionListener(e -> {
            try {
                if (!crcHelper.receive(crcHelper.inputMessage(sentMesageText.getText()), crcHelper.inputDivisor(dividerText.getText()))) {
                    wynik.setText("Transmisja przebiegła poprawnie");
                    wynik.setForeground(Color.green);
                } else {
                    wynik.setText("Transmisja z błędem");
                    wynik.setForeground(Color.red);
                }
            }catch (NumberFormatException x){
                JOptionPane.showMessageDialog(panel1, "Wcześniej wprowadź wszystkie dane.");
            }
        });
        //wyczysc
        wyczyscButton.addActionListener(e -> {
            wynik.setText("");
            kodCRCText.setText("");
            messageText.setText("");
            dividerText.setText("");
            reminderText.setText("");
            sentMesageText.setText("");
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wizualizacja szyfrowania danych algorytmem CRC");
        frame.setContentPane(new CRCUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


        }
    }



