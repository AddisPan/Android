package com.example.a10822040_readwrite;

// Fig. 15.11: ReadSequentialFile.java
// Reading a file of objects sequentially with ObjectInputStream
// and displaying each record.
import android.content.Context;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadSequentialFile {
	private ObjectInputStream input;
	private String sFileName = "";
	private Context context;

	public ReadSequentialFile(Context _context, String _sFileName) {
		context = _context;
		sFileName = _sFileName;
	}
	
	public Object getRSF() throws Exception {
		Object rd=null;
		try {
			openFile();
			rd = readRecords();
			closeFile();
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Error opening file."+e.getMessage());
		}
		return rd;
	}

	// enable user to select file to open
	public void openFile() throws IOException {
		try // open file
		{
			FileInputStream fis = context.openFileInput(sFileName);
			input = new ObjectInputStream(fis);
		} catch (IOException ioException) {
			throw new IOException("Error opening file.");
		}
	}

	// read record from file
	public Object readRecords() throws Exception {
		Object record=null;
		try {
			record= (Object) input.readObject();
		} catch (EOFException endOfFileException) {
			throw new Exception("%nNo more records%n");
		} catch (ClassNotFoundException classNotFoundException) {
			throw new Exception("Invalid object type. Terminating.");
		} catch (IOException ioException) {
			throw new Exception("Error reading from file. Terminating.");
		}
		return record;
	} // end method readRecords

	// close file and terminate application
	public void closeFile() throws Exception{
		try {
			if (input != null)
				input.close();
		} catch (IOException ioException) {
			throw new Exception("Error closing file. Terminating.");
		}
	}
} // end class ReadSequentialFile

/*************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 *************************************************************************/