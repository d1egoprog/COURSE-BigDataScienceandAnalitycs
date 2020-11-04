package co.d1egoprog.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PDFFileUtilities {

	public static String extractPDFasText(String path, String file) {
		String st = "";
		try {
			PDDocument document = null;
			document = PDDocument.load(new File(path + "\\" + file));
			document.getClass();
			if (!document.isEncrypted()) {
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper Tstripper = new PDFTextStripper();
				st = Tstripper.getText(document);
			}
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return st;
	}

}
