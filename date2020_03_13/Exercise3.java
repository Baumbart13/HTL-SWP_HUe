package date2020_03_13;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

public class Exercise3 {
    public static void foo(){
        LocalDateTime time = LocalDateTime.now();

        StringBuilder text = new StringBuilder(Exercise1.foo());
        StringBuilder dateText = new StringBuilder(time.getDayOfMonth() + "." + time.getMonthValue() + "." + time.getYear());
        StringBuilder timeText = new StringBuilder(time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());

        text.append("Erstellt am " + dateText + " um " + timeText + System.lineSeparator());
        text.append("unter " + System.getProperty("os.name") + " Version " + System.getProperty("os.version"));

        Path path = Paths.get("measurements.txt").toAbsolutePath();
        if(!path.toFile().isFile() || !path.toFile().exists()){
            try {
                Files.createFile(path);
                Files.write(path, stringBuilderToByteArray(text), StandardOpenOption.WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] stringBuilderToByteArray(StringBuilder stringBuilder){
        byte[] out = new byte[stringBuilder.length()];

        for(int i = 0; i < stringBuilder.length(); ++i){
            out[i] = (byte)(stringBuilder.charAt(i));
        }

        return out;
    }
}
