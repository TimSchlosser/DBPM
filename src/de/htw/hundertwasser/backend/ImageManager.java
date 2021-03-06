package de.htw.hundertwasser.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.htw.hundertwasser.custom.error.ChoosenFileNotAFolder;
import de.htw.hundertwasser.custom.error.InsufficientPrivilegesException;

/**
 * This class will manage the Images of the current Project
 * @author daniel
 *
 */
public class ImageManager {

	private static final String ERROR_NO_FILE_PATH = "Der angegebene Pfad darf nicht null sein.";

	private static final String ERROR_EMPTY_PATH = "The path can't be empty.";
	private static final String ERROR_NULL_PATH = "The path can't be null.";
	
	
	public ImageManager()
	{
		
	}
	
	/**
	 * Lese das Bild aus einer Datei
	 * @param absouluteFile Der absoulute Pfad zur Datei.
	 * @return picture of the image.
	 * @throws IOException Wird geworfen wenn das Bild nicht gefunden werden kann.
	 * @throws FileNotFoundException Wird geworfen wenn das Bild nicht gefunden werden kann.
	 * @throws IllegalArgumentException Wenn die Datei leer oder null ist.
	 */
	public BufferedImage getImage(String absoluteFile) throws IOException,FileNotFoundException,InsufficientPrivilegesException
	{
		if (absoluteFile== null) throw new IllegalArgumentException(ERROR_NO_FILE_PATH);
		if (absoluteFile.trim().isEmpty()) throw new IllegalArgumentException("The path of the file can't be emtpy.");
		File file = new File(absoluteFile);
		if (!file.exists()) throw new FileNotFoundException("File" + absoluteFile + " can't be found on your System.");
		if (!file.canRead()) throw new InsufficientPrivilegesException("I'm not allowed to open the file" + absoluteFile);
		return ImageIO.read(file);
	}
	
	/**
	 * Returns an ArrayList of ImageFiles within the current directory
	 * @param Path
	 * @return
	 */
	public File[] getImagesListInFolder(String path) throws IllegalArgumentException,ChoosenFileNotAFolder,FileNotFoundException
	{
		//TODO:Evtl. Rekursives auslesen notwendig.
		File imageList =null;
		FileAcceptor fileAcceptor = new FileAcceptor();
		if (path==null) throw new IllegalArgumentException(ERROR_NULL_PATH);
		if (path.trim().isEmpty()) throw new IllegalArgumentException(ERROR_EMPTY_PATH);
		
			imageList = new File(path);
			if (imageList.exists())
			{
				if (imageList.isDirectory())
				{
				return imageList.listFiles(fileAcceptor);
				}
				else
				{
					throw new ChoosenFileNotAFolder("The given path " + path + " is not a Folder.");
				}
			}
			else
			{
				throw new FileNotFoundException("The file" + path + " doesn't exists.");
			}
	}
}
