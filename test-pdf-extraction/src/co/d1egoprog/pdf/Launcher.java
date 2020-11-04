package co.d1egoprog.pdf;

public class Launcher {

	public static void main(String[] args) {
		String text = PDFFileUtilities.extractPDFasText("c:\\temp", "test.pdf");
		System.out.println(text);
	}

}
