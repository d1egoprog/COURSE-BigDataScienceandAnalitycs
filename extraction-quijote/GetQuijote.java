import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

public class GetQuijote {
	public static void main(String[] args) {
		int pag = 0;
		while (pag <= 35) {
			try {
				String pagina = "https://www.elmundo.es/quijote/capitulo.html?cual=" + pag;
				URL url = new URL(pagina);
				
				InputStreamReader reader = new InputStreamReader(url.openStream());
				BufferedReader in = new BufferedReader(reader);
				String inputLine;
				StringBuilder sb = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					sb.append(inputLine);
				}
				 
				PrintWriter writer = new PrintWriter("Capitulo"+pag+".txt", "UTF-8");
				writer.write(sb.toString());
				writer.flush();
				writer.close();
				
				in.close();
				pag++;
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}
}