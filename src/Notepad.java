import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Notepad extends JFrame implements ActionListener {
	private TextArea notes = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	private MenuBar menuBar = new MenuBar();
	private Menu file = new Menu();
	private MenuItem load = new MenuItem();
	private MenuItem save = new MenuItem();
	private MenuItem font = new MenuItem();
	private MenuItem exit = new MenuItem();
	JFileChooser saver = new JFileChooser();

	public Notepad() {
		setSize(600, 400);
		setTitle("Noah's Notepad");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFont(new Font("Helvetica", Font.BOLD, 14));
		notes.setFont(new Font("Helvetica", Font.ROMAN_BASELINE, 18));
		add(notes);

		setMenuBar(menuBar);
		menuBar.add(this.file);
		file.setLabel("File");

		load.setLabel("Load File");
		load.addActionListener(this);
		load.setShortcut(new MenuShortcut(KeyEvent.VK_0, false));
		file.add(load);

		save.setLabel("Save File");
		save.addActionListener(this);
		save.setShortcut(new MenuShortcut(KeyEvent.VK_1, false));
		file.add(save);

		font.setLabel("Font Size");
		font.addActionListener(this);
		font.setShortcut(new MenuShortcut(KeyEvent.VK_2, false));
		file.add(font);

		exit.setLabel("Exit");
		exit.addActionListener(this);
		exit.setShortcut(new MenuShortcut(KeyEvent.VK_3, false));
		file.add(exit);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == load) {
			JFileChooser open = new JFileChooser();
			int fileChoice = open.showOpenDialog(this);

			if (fileChoice == JFileChooser.APPROVE_OPTION) {
				notes.setText("");
				try {
					Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
					while (scan.hasNext()) {
						notes.append(scan.nextLine() + "\n");
					}

				} catch (Exception error) {
					JOptionPane.showMessageDialog(null, "Error: " + e);
					System.out.println("Error: " + error);
					dispose();
				}
			}
		}

		if (e.getSource() == save) {

			int saveChoice = saver.showSaveDialog(this);

			if (saveChoice == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(saver.getSelectedFile().getPath()));
					out.write(notes.getText());
					out.close();
				} catch (Exception error) {
					JOptionPane.showMessageDialog(null, "Error: " + e);
					System.out.println("Error: " + error);
				}
			}
		}

		if (e.getSource() == font) {
			try {
				String sizeStr = JOptionPane.showInputDialog(null, "Enter desired font size:");
				int size = Integer.parseInt(sizeStr);
				notes.setFont(new Font("Helvetica", Font.ROMAN_BASELINE, size));
			} catch (Exception error) {
				JOptionPane.showMessageDialog(null, "Error: " + e);
				System.out.println("Error: " + error);
			}
		}

		if (e.getSource() == exit) {
			int saveQues = JOptionPane.showConfirmDialog(null, "Would you like to save?");

			if (saveQues == JOptionPane.YES_OPTION) {
				int saveChoice = saver.showSaveDialog(this);

				if (saveChoice == JFileChooser.APPROVE_OPTION) {
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(saver.getSelectedFile().getPath()));
						out.write(notes.getText());
						out.close();
					} catch (Exception error) {
						JOptionPane.showMessageDialog(null, "Error: " + e);
						System.out.println("Error: " + error);
					}
				}
				dispose();

			}
			if (saveQues == JOptionPane.NO_OPTION) {
				dispose();
			} else {
				// cancel exit
			}

		}
	}

	public static void main(String args[]) {
		Notepad noteApp = new Notepad();
		noteApp.setVisible(true);
	}
}