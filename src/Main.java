import javax.swing.SwingUtilities;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
		String file = "./Sxoles/thetiki_cleaned.csv";

		fileScanner scanner = new fileScanner();
		ArrayList<School> schools = scanner.getSchools(file);

	}

}
