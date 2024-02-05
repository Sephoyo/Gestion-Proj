package action.data;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class NonEmptyVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JTextField) {
            JTextField textField = (JTextField) input;
            return !textField.getText().trim().isEmpty();
        }
        return false;
    }
}
