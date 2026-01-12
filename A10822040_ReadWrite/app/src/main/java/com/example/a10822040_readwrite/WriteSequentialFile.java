package com.example.a10822040_readwrite;

// Fig. 15.10: CreateSequentialFile.java
// Writing objects sequentially to a file with class ObjectOutputStream.
import android.content.Context;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class WriteSequentialFile {
	private ObjectOutputStream output; // outputs data to file
	private String sFileName ;
	private Context context;
	
	public WriteSequentialFile(Context _context, String _sFileName) {
		context = _context;
		sFileName = _sFileName;
	}
	
	public void fnWSF(Object _rd) throws Exception {
		try {
			openFile();
			addRecords(_rd);
			closeFile();
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Error writing file."+e.getMessage());
		}
	}

	// open file clients.ser
	public void openFile() throws IOException {
		try {
			output = new ObjectOutputStream(context.openFileOutput(sFileName, Context.MODE_PRIVATE));
		} catch (IOException ioException) {
			throw new IOException("Error opening file. Terminating.");
		}
	}

	// add records to file
	public void addRecords(Object _rd) throws Exception {
		try {
			// serialize record object into file
			output.writeObject(_rd);
		} catch (NoSuchElementException elementException) {
			throw new Exception("Invalid input. Please try again.");
		} catch (IOException ioException) {
			throw new Exception("Error writing to file. Terminating.");
		}
	}

	// close file and terminate application
	public void closeFile() throws Exception {
		try {
			if (output != null)
				output.close();
		} catch (IOException ioException) {
			throw new Exception("Error closing file. Terminating.");
		}
	}
} // end class CreateSequentialFile

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