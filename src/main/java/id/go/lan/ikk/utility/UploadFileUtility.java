package id.go.lan.ikk.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class UploadFileUtility {
    @Value("${media.uploads.path}")
    private String localPath;

    public String uploadFile(MultipartFile file, String nip) {
        String filePath = "";

        if (file != null) {
            try {
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                String directory = getLocalPath() + nip + "/" + year + "/" + month + "/" + day + "/";
                File directory2 = new File(directory);
                if (!directory2.exists()) {
                    directory2.mkdirs();
                }
                if(!file.isEmpty()){
                String filename = UUID.randomUUID().toString() + file.getOriginalFilename().toString().substring(
                        file.getOriginalFilename().toString().lastIndexOf("."),
                        file.getOriginalFilename().toString().length());

                filePath = Paths.get(directory, filename).toString();
                }
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }

        if (filePath.equals("")) {
            return null;
        } else {
            return filePath.substring(getLocalPath().length());
        }
    }

    public String getLocalPath() {
        return localPath;
    }
}
