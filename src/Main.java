import javax.swing.SwingUtilities;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
	}

}
