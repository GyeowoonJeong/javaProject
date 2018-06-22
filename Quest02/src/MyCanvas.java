//import java.awt.Canvas;
import java.awt.*;
import javax.swing.*;

//import com.sun.webkit.ColorChooser;

public class MyCanvas extends JFrame {
	JFrame frame = new JFrame();
	//JFrame colorFrame;
	JPanel CanvasPanel = new JPanel();
	JPanel SelectPanel = new JPanel();
	//JPanel ColorPanel = new JPanel();
	Drawing draw = new Drawing();
	
	MyCanvas() {
		frame.setName("My Canvas");
		frame.setSize(1200, 800);
		
		setMenuBar(frame);
		setSelectPanel(SelectPanel);
		
		frame.add(CanvasPanel);
		frame.add(SelectPanel, "North");
		//frame.add(ColorPanel);
		frame.add(draw);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
	}
	
	public void setMenuBar(JFrame frame) {
		JMenuBar mb = new JMenuBar(); 
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu toolMenu = new JMenu("Tool");
		JMenuItem newFile, openFile, save, exit;
		JMenuItem undo, redo, delete, erase, copy, paste;
		JMenuItem line, pen, rect, circle;

		newFile = new JMenuItem("New");
		openFile = new JMenuItem("Open File");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
		
		fileMenu.add(newFile);
		fileMenu.add(openFile);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.addSeparator();
		fileMenu.add(exit);		
		
		undo = new JMenuItem("Undo");
		redo = new JMenuItem("Redo");
		delete = new JMenuItem("Delete Object");
		erase = new JMenuItem("Eraser");
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		
		editMenu.add(undo);
		editMenu.add(redo);
		editMenu.addSeparator();
		editMenu.add(delete);
		editMenu.add(erase);
		editMenu.addSeparator();
		editMenu.add(copy);
		editMenu.add(paste);
		
		line = new JMenuItem("Line");
		pen = new JMenuItem("Pen");
		rect = new JMenuItem("Rectangle");
		circle = new JMenuItem("Circle");
		
		toolMenu.add(line);
		toolMenu.add(pen);
		toolMenu.add(rect);
		toolMenu.add(circle);
		
		line.addActionListener(draw);
		pen.addActionListener(draw);
		rect.addActionListener(draw);
		circle.addActionListener(draw);
		
		mb.add(fileMenu);
		mb.add(editMenu);
		mb.add(toolMenu);
		frame.setJMenuBar(mb);
	}
	
	public void setSelectPanel (JPanel selectPanel) {
		JButton color = new JButton("COLOR");
		JButton upSize = new JButton("UP");
		JButton downSize = new JButton("DOWN");
		
		upSize.addActionListener(draw);
		downSize.addActionListener(draw);
		color.addActionListener(draw);
	
		selectPanel.add(color);		
		selectPanel.add(upSize);
		selectPanel.add(downSize);
	}
	
	public static void main(String args[]) {
		new MyCanvas();
	}

	

}


