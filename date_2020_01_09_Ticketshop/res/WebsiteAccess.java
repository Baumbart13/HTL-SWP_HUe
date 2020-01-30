package date2020_01_09_Ticketshop.res;

import date2020_01_09_Ticketshop.Places;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WebsiteAccess {
    // long geonameId,

    public String foo2(String url, String searchFor) throws IOException{
        if(!url.endsWith(".zip")){
            return null;
        }

        String absoluteFilePath = download(url);

        ZipFile zipFile = new ZipFile(absoluteFilePath);

        absoluteFilePath = convert(zipFile);

        return absoluteFilePath;
    }

    public String convert(ZipFile zipFile) throws IOException{
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while(entries.hasMoreElements()){
            InputStream stream = zipFile.getInputStream(entries.nextElement());
            System.out.println(stream.read());
        }

        String out = zipFile.getName();
        try {
            out = Paths.get(getClass().getClassLoader().getResource(zipFile.getName()).toURI()).toFile().getAbsolutePath();
        }catch(URISyntaxException e){
            e.printStackTrace();
        }

        return out;
    }

    public String download(String url){
        String outputFileName = "";
        try {
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());

            // get the filename only
            outputFileName = url.substring(url.lastIndexOf("\\"));
            FileOutputStream fos = new FileOutputStream(outputFileName);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            try {
                outputFileName = Paths.get(getClass().getClassLoader().getResource(outputFileName).toURI()).toFile().getAbsolutePath();
            }catch(URISyntaxException e){
                e.printStackTrace();
            }

            rbc.close();
            fos.close();
        }catch(MalformedURLException e){
            e.printStackTrace();
            System.err.println("404");

        }catch(IOException e){
            e.printStackTrace();
        }
        return outputFileName;
    }

    public void foo(){
        URL url;

        try{
            url = new URL("https://download.geonames.org/export/dump/");

            URLConnection connection = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine = null;
            while((inputLine = br.readLine()) != null){
                if(inputLine.contains(Places.AT.name())) {
                    System.out.println(inputLine);
                }
            }
            br.close();

            System.out.println("Done!");
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
