package ImageProcessing;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.FileWriter;

public class FileSave implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		BufferedImage saveimage = MainFrame.imgPanel2.getImage();
		FileDialog fd = new FileDialog(Main.frame, "Save a file", FileDialog.SAVE);
		fd.setVisible(true);
		String path = fd.getDirectory() + fd.getFile();
		
		try {	
			ImageIO.write(saveimage, "jpeg", new File(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
