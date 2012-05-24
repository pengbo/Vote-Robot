import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class AutoVote {
  private static final String UTF_8 = "UTF-8";
  private static Map<String, Integer> proxyMap = new HashMap<String, Integer>();

  public static void main(String[] args) throws IOException {
    StringTokenizer st;
    String proxyString;
    BufferedReader f = new BufferedReader(new FileReader("Proxies"), 8192 * 3);
    while (null != (proxyString = f.readLine())) {
      st = new StringTokenizer(proxyString);
      proxyMap.put(st.nextToken(), Integer.parseInt(st.nextToken()));
    }
    f.close();
    new Timer().schedule(new AutoVote().new Task(), 0, 3600 * 1000);
  }

  class Task extends TimerTask {
    public void run() {
      for (Iterator<Entry<String, Integer>> it = proxyMap.entrySet().iterator(); it.hasNext();) {
        Entry<String, Integer> entry = it.next();
        vote(entry.getKey(), entry.getValue());
      }
    }
  }

  private void vote(String ip, int port) {
    try {
      URL url = new URL("xxx");
      Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
      HttpURLConnection conn = (HttpURLConnection)url.openConnection(proxy);
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setConnectTimeout(4000);
      conn.setRequestMethod("POST");
      OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
      wr.write(getDataString());
      wr.flush();
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
	  while ((line = rd.readLine()) != null) {
		  System.out.print(line);  
	  }  
	  rd.close();
      wr.close();
      //Thread.sleep((long)(Math.random() * 10000));
    }
    catch (Exception e) {
    }
  }

  private String getDataString() throws UnsupportedEncodingException {
    StringBuffer data = new StringBuffer();
    data.append(URLEncoder.encode("current_jiabin_id", UTF_8));
    data.append("=");
    data.append(URLEncoder.encode("1855", UTF_8));
    data.append("&");
    data.append(URLEncoder.encode("vote_time", UTF_8));
    data.append("=");
    data.append(URLEncoder.encode("undefined", UTF_8));
    data.append("&");
    data.append(URLEncoder.encode("current_ip", UTF_8));
    data.append("=");
    data.append(URLEncoder.encode("undefined", UTF_8));
    return data.toString();
  }
}
