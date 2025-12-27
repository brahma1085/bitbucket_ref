import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // for ActionListener
import java.util.*;    // for ArrayList

public class QuizCardBuilder {
  private JFrame frame;
  private JTextArea question;
  private JTextArea answer;
  private ArrayList<QuizCard> cardList;

  public static void main(String[] args) {
	QuizCardBuilder builder = new QuizCardBuilder();
	builder.go();
  }

  public void go() {
	cardList = new ArrayList<QuizCard>();

    	// build and display gui
	frame = new JFrame("Quiz Card Builder");

	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenuItem newMenuItem = new JMenuItem("New");
	JMenuItem saveMenuItem = new JMenuItem("Save");
	newMenuItem.addActionListener(new NewMenuListener());
	saveMenuItem.addActionListener(new SaveMenuListener());
	fileMenu.add(newMenuItem);
	fileMenu.add(saveMenuItem);
	menuBar.add(fileMenu);
	frame.setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel();
	Font bigFont = new Font("sanserif", Font.BOLD, 24);

	question = new JTextArea(6, 20);
	question.setLineWrap(true);
	question.setFont(bigFont);
	JScrollPane qScroller = new JScrollPane(question);
	qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	answer = new JTextArea(6,20);
        answer.setLineWrap(true);
	answer.setFont(bigFont);
	JScrollPane aScroller = new JScrollPane(answer);
	aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next Card");
	nextButton.addActionListener(new NextCardListener());

	JLabel qLabel = new JLabel("Question:");
	JLabel aLabel = new JLabel("Answer:");

	mainPanel.add(qLabel);
	mainPanel.add(qScroller);
	mainPanel.add(aLabel);
	mainPanel.add(aScroller);
	mainPanel.add(nextButton);

	frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
	frame.setSize(500, 600);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
  }

  private class NextCardListener implements ActionListener {
	public void actionPerformed(ActionEvent ev) {
		// add the current card to the list and clear the text areas
		QuizCard card = new QuizCard(question.getText(), answer.getText());
		cardList.add(card);
		clearCard(); 
	}
  }

  private class SaveMenuListener implements ActionListener {
	public void actionPerformed(ActionEvent ev) {
		// bring up a file dialog box
		// let the user input file name and save the set
		QuizCard card = new QuizCard(question.getText(), answer.getText());
		cardList.add(card);

		JFileChooser fileSave = new JFileChooser();
		fileSave.showSaveDialog(frame);
		saveFile(fileSave.getSelectedFile());		
	}
  }

  private class NewMenuListener implements ActionListener {
	public void actionPerformed(ActionEvent ev) {
		// clear out the card list, and clear out the text areas
		clearCard();
		cardList.clear();
	}
  }

  private void clearCard() {
	question.setText("");
	answer.setText("");
	question.requestFocus();
  }

  private void saveFile(File file) {
	// iterate through the list of cards, and write each one out to a tex file
	// in a parseable way (with clear separations between parts)
	try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		for (QuizCard card:cardList) {
			writer.write(card.getQuestion() + "/");
			writer.write(card.getAnswer() + "\n");
		}
		writer.close();
	} 
	catch (IOException ex) {
		System.out.println("Couldn't write the cardList out");
		ex.printStackTrace();
	}
  }
}