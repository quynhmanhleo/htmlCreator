package htmlCreator;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Creator extends JFrame{
	private static JButton button1;
	private static JButton button2;
	private static JButton button3;
	private TextField textField1, textField2;
	
	public Creator(){
		button1 = new JButton("Browser File CSV");
		button2 = new JButton("Browser Folder");
		button3 = new JButton("Start");
		textField1 = new TextField();
		textField2 = new TextField();
		textField1.setSize(400, 50);
		textField2.setSize(400, 50);
		textField1.setEditable(false);
		textField2.setEditable(false);
		textField2.setBounds(0, 100, 400, 50);
		textField1.setText("Nhan vao nut ben duoi de chon file CSV");
		textField2.setText("Nhan vao nut ben duoi de chon folder chua hinh anh");
	    button1.setBounds(100, 50, 200, 50);
	    button2.setBounds(100, 150, 200, 50);
	    button3.setBounds(100, 200, 200, 50);
	    add(textField1);
	    add(button1);
	    add(textField2);
	    add(button2);
	    add(button3);
	    
	    
	    button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File selectedFile = fileChooser.getSelectedFile();
		          textField1.setText(selectedFile.getPath());
		        }
			}
		});
	    
	    button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File selectedFile = fileChooser.getSelectedFile();
		          textField2.setText(selectedFile.getPath());
		        }
			}
		});
	    
	    button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					new Generator(textField1.getText(), textField2.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.toString());
					e1.printStackTrace();
				}
			}
		});
	    
		setTitle("Do an giua ky - Cu nhan tai nang 2014");
		setSize(400, 300);
		setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(null);
	    setResizable(false);
	    setVisible(true);
	}
	
	public static void main(String[] args){
		new Creator();
	}
}
