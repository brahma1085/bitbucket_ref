package com.techno.ehrp.execute.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.techno.ehrp.exception.ExceptionHandler;
import com.techno.ehrp.exception.HPException;
import com.techno.ehrp.utils.Constants;
import com.techno.ehrp.utils.ResultUtils;

/**
 * The Class HPExecutor.
 * 
 * @author brahma
 */
public class HPExecutor {

	/** The frm nhrp. */
	private JFrame frmNhrp;

	/** The file path. */
	private String filePath;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HPExecutor window = new HPExecutor();
					window.frmNhrp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Instantiates a new HP executor.
	 */
	public HPExecutor() {
		initialize();
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		this.frmNhrp = new JFrame();
		this.frmNhrp.getContentPane().setBackground(UIManager.getColor("TableHeader.background"));
		this.frmNhrp.getContentPane().setFont(new Font(Constants.CONSTANTIA, 1, 14));
		SpringLayout springLayout = new SpringLayout();
		this.frmNhrp.getContentPane().setLayout(springLayout);
		JTextPane txtpnNotePleaseSpecify = createTextPane(springLayout);
		JLabel lblNewLabel = createLabel(springLayout, txtpnNotePleaseSpecify);
		JTextField textField = createTextField(springLayout, txtpnNotePleaseSpecify);
		JComboBox<String> outputOptions = new JComboBox<>();
		JLabel lblComboLabel = createLabel(springLayout, outputOptions);
		createComboBox(springLayout, outputOptions);
		JTextArea textArea_1 = new JTextArea();
		JButton btnOpenFile = new JButton("Open file");
		btnOpenFile.setVisible(false);
		JButton btnSubmit = createSubmitButton(springLayout, textField, textArea_1, btnOpenFile, outputOptions);
		JButton btnReset = createResetButton(springLayout, textField, btnSubmit, textArea_1, btnOpenFile, outputOptions);
		updateTextArea(springLayout, txtpnNotePleaseSpecify, textArea_1, btnSubmit);
		springLayout.putConstraint(Constants.NORTH, btnOpenFile, 16, Constants.SOUTH, textArea_1);
		springLayout.putConstraint(Constants.WEST, btnOpenFile, 0, Constants.WEST, txtpnNotePleaseSpecify);
		this.frmNhrp.getContentPane().add(btnOpenFile);
		this.frmNhrp.getContentPane().setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { txtpnNotePleaseSpecify, lblNewLabel, textField, lblComboLabel, outputOptions, btnSubmit, btnReset, textArea_1 }));
		this.frmNhrp.setTitle("nHRP Product\r\n");
		this.frmNhrp.setIconImage(Toolkit.getDefaultToolkit().getImage(getIcon()));
		this.frmNhrp.setFont(new Font(Constants.CONSTANTIA, 1, 14));
		this.frmNhrp.setBounds(100, 100, 700, 400);
		this.frmNhrp.setDefaultCloseOperation(3);
	}

	/**
	 * Creates the combo box.
	 *
	 * @param springLayout
	 *            the spring layout
	 * @param outputOptions
	 *            the output options
	 */
	private void createComboBox(SpringLayout springLayout, JComboBox<String> outputOptions) {
		outputOptions.addItem(Constants.EMPTY);
		outputOptions.addItem("Excel Document");
		outputOptions.addItem("Word Document");
		outputOptions.setToolTipText("please select the output format: Excel sheet or Word document");
		outputOptions.setFont(new Font(Constants.CONSTANTIA, 1, 14));
		outputOptions.setBackground(UIManager.getColor("TextArea.background"));
		springLayout.putConstraint(Constants.NORTH, outputOptions, -25, Constants.SOUTH, outputOptions);
		outputOptions.setFont(new Font(Constants.CONSTANTIA, 1, 14));
		springLayout.putConstraint(Constants.SOUTH, outputOptions, 154, Constants.NORTH, this.frmNhrp.getContentPane());
		springLayout.putConstraint(Constants.WEST, outputOptions, 240, Constants.WEST, this.frmNhrp.getContentPane());
		outputOptions.setVisible(true);
		this.frmNhrp.getContentPane().add(outputOptions);
	}

	/**
	 * Creates the submit button.
	 *
	 * @param springLayout
	 *            the spring layout
	 * @param textField
	 *            the text field
	 * @param textArea_1
	 *            the text area 1
	 * @param btnOpenFile
	 *            the btn open file
	 * @param outputOptions
	 *            the output options
	 * @return the j button
	 */
	private JButton createSubmitButton(SpringLayout springLayout, final JTextField textField, final JTextArea textArea_1, final JButton btnOpenFile,
			final JComboBox<String> outputOptions) {
		JButton btnSubmit = new JButton("Submit");
		springLayout.putConstraint(Constants.NORTH, btnSubmit, 36, Constants.SOUTH, textField);
		springLayout.putConstraint(Constants.WEST, btnSubmit, 261, Constants.WEST, this.frmNhrp.getContentPane());
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = textField.getText();
				String outputFormat = (String) outputOptions.getSelectedItem();
				try {
					if ((data == null) || (StringUtils.isBlank(data)))
						throw new HPException("Please enter valid data");
					if ((outputFormat == null) || (StringUtils.isBlank(outputFormat))) {
						throw new HPException("Please select output format");
					}
					if ("Excel Document".equalsIgnoreCase(outputFormat))
						HPExecutor.this.filePath = ResultUtils.getXlsResult(data);
					else if ("Word Document".equalsIgnoreCase(outputFormat)) {
						HPExecutor.this.filePath = ResultUtils.getResult(data);
					}
					if (HPExecutor.this.filePath != null) {
						textArea_1.append("Please locate the result file at : \r\n\r\n");
						textArea_1.append(HPExecutor.this.filePath);
						btnOpenFile.setVisible(true);
						btnOpenFile.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Desktop desktop = Desktop.getDesktop();
								try {
									desktop.open(new File(HPExecutor.this.filePath));
								} catch (IOException e1) {
									ExceptionHandler.registerExceptionHandler();
									ExceptionHandler handler = new ExceptionHandler();
									handler.uncaughtException(null, e1);
								}
							}
						});
					}
				} catch (Exception e1) {
					ExceptionHandler.registerExceptionHandler();
					ExceptionHandler handler = new ExceptionHandler();
					handler.uncaughtException(null, e1);
				}
			}
		});
		btnSubmit.setToolTipText("submit the valid names");
		this.frmNhrp.getContentPane().add(btnSubmit);
		return btnSubmit;
	}

	/**
	 * Update text area.
	 *
	 * @param springLayout
	 *            the spring layout
	 * @param txtpnNotePleaseSpecify
	 *            the txtpn note please specify
	 * @param textArea_1
	 *            the text area 1
	 * @param btnSubmit
	 *            the btn submit
	 */
	private void updateTextArea(SpringLayout springLayout, JTextPane txtpnNotePleaseSpecify, JTextArea textArea_1, JButton btnSubmit) {
		textArea_1.setForeground(SystemColor.textHighlight);
		textArea_1.setFont(new Font(Constants.CONSTANTIA, 1, 14));
		springLayout.putConstraint(Constants.NORTH, textArea_1, 0, Constants.SOUTH, btnSubmit);
		springLayout.putConstraint(Constants.WEST, textArea_1, 0, Constants.WEST, txtpnNotePleaseSpecify);
		springLayout.putConstraint(Constants.SOUTH, textArea_1, -102, Constants.SOUTH, this.frmNhrp.getContentPane());
		springLayout.putConstraint("East", textArea_1, 0, "East", txtpnNotePleaseSpecify);
		textArea_1.setBackground(UIManager.getColor("TextArea.disabledBackground"));
		textArea_1.setEditable(false);
		textArea_1.setLineWrap(true);
		textArea_1.setToolTipText("results file path will be displayed here");
		this.frmNhrp.getContentPane().add(textArea_1);
	}

	/**
	 * Creates the reset button.
	 *
	 * @param springLayout
	 *            the spring layout
	 * @param textField
	 *            the text field
	 * @param btnSubmit
	 *            the btn submit
	 * @param textArea
	 *            the text area
	 * @param btnOpenFile
	 *            the btn open file
	 * @param outputOptions
	 *            the output options
	 * @return the j button
	 */
	private JButton createResetButton(SpringLayout springLayout, final JTextField textField, JButton btnSubmit, final JTextArea textArea, final JButton btnOpenFile,
			final JComboBox<String> outputOptions) {
		JButton btnReset = new JButton("Reset");
		springLayout.putConstraint(Constants.NORTH, btnReset, 36, Constants.SOUTH, textField);
		springLayout.putConstraint(Constants.WEST, btnReset, 16, "East", btnSubmit);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textArea.setText("");
				btnOpenFile.setVisible(false);
				outputOptions.setSelectedItem("");
			}
		});
		btnReset.setToolTipText("clear the names");
		this.frmNhrp.getContentPane().add(btnReset);
		return btnReset;
	}

	/**
	 * Creates the text field.
	 *
	 * @param springLayout
	 *            the spring layout
	 * @param txtpnNotePleaseSpecify
	 *            the txtpn note please specify
	 * @return the j text field
	 */
	private JTextField createTextField(SpringLayout springLayout, JTextPane txtpnNotePleaseSpecify) {
		JTextField textField = new JTextField();
		springLayout.putConstraint(Constants.SOUTH, textField, -234, Constants.SOUTH, this.frmNhrp.getContentPane());
		textField.setToolTipText("please specify the valid names");
		springLayout.putConstraint(Constants.NORTH, textField, 11, Constants.SOUTH, txtpnNotePleaseSpecify);
		springLayout.putConstraint(Constants.WEST, textField, 10, Constants.WEST, this.frmNhrp.getContentPane());
		springLayout.putConstraint("East", textField, -10, "East", this.frmNhrp.getContentPane());
		textField.setFont(new Font(Constants.CONSTANTIA, 1, 14));
		textField.setBackground(UIManager.getColor("TextArea.background"));
		this.frmNhrp.getContentPane().add(textField);
		return textField;
	}

	/**
	 * Creates the label.
	 *
	 * @param springLayout
	 *            the spring layout
	 * @param txtpnNotePleaseSpecify
	 *            the txtpn note please specify
	 * @return the j label
	 */
	private JLabel createLabel(SpringLayout springLayout, JTextPane txtpnNotePleaseSpecify) {
		JLabel lblNewLabel = new JLabel("Please Enter Horse Names : ");
		springLayout.putConstraint(Constants.NORTH, txtpnNotePleaseSpecify, 6, Constants.SOUTH, lblNewLabel);
		lblNewLabel.setFont(new Font(Constants.CONSTANTIA, 1, 14));
		springLayout.putConstraint(Constants.NORTH, lblNewLabel, 10, Constants.NORTH, this.frmNhrp.getContentPane());
		springLayout.putConstraint(Constants.WEST, lblNewLabel, 10, Constants.WEST, this.frmNhrp.getContentPane());
		this.frmNhrp.getContentPane().add(lblNewLabel);
		return lblNewLabel;
	}

	/**
	 * Creates the label.
	 *
	 * @param springLayout
	 *            the spring layout
	 * @param resultCombo
	 *            the result combo
	 * @return the j label
	 */
	private JLabel createLabel(SpringLayout springLayout, JComboBox<String> resultCombo) {
		JLabel lblNewLabel1 = new JLabel("Please Select The Output Format : ");
		springLayout.putConstraint(Constants.NORTH, resultCombo, 6, Constants.SOUTH, lblNewLabel1);
		lblNewLabel1.setFont(new Font(Constants.CONSTANTIA, 1, 14));
		springLayout.putConstraint(Constants.SOUTH, lblNewLabel1, 150, Constants.NORTH, this.frmNhrp.getContentPane());
		springLayout.putConstraint(Constants.WEST, lblNewLabel1, 10, Constants.WEST, this.frmNhrp.getContentPane());
		this.frmNhrp.getContentPane().add(lblNewLabel1);
		return lblNewLabel1;
	}

	/**
	 * Creates the text pane.
	 *
	 * @param springLayout
	 *            the spring layout
	 * @return the j text pane
	 */
	private JTextPane createTextPane(SpringLayout springLayout) {
		JTextPane txtpnNotePleaseSpecify = new JTextPane();
		springLayout.putConstraint(Constants.WEST, txtpnNotePleaseSpecify, 10, Constants.WEST, this.frmNhrp.getContentPane());
		springLayout.putConstraint("East", txtpnNotePleaseSpecify, -10, "East", this.frmNhrp.getContentPane());
		txtpnNotePleaseSpecify.setFont(new Font("Arial", 0, 12));
		txtpnNotePleaseSpecify.setBackground(SystemColor.controlHighlight);
		txtpnNotePleaseSpecify.setForeground(Color.RED);
		springLayout.putConstraint(Constants.SOUTH, txtpnNotePleaseSpecify, 82, Constants.NORTH, this.frmNhrp.getContentPane());
		txtpnNotePleaseSpecify.setText(
				"Note: Please specify the valid horse names sequentially with comma(,) seperated. For example: dani,miltron,johnny.\r\n\r\nNow, Please specify the valid names : \r\n\r\n");
		this.frmNhrp.getContentPane().add(txtpnNotePleaseSpecify);
		return txtpnNotePleaseSpecify;
	}

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	private String getIcon() {
		return HPExecutor.class.getClassLoader().getResource(Constants.HORSE_ICON_JPG).getFile();
	}
}