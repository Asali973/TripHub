package triphub.helpers;

import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;

public class ImageHelper {

   private static final String BASE_PATH = "/Users/brendan/EnvJEE/Tools/wildfly-18.0.0.Final/data/triphub/images";
    
    //private static final String BASE_PATH = "D/EnvJEE/Tools/wildfly-18.0.1.Final/data/triphub/images";
    
    public static String processProfilePicture(Part profilePicture) throws IOException {
        if (profilePicture != null) {
            byte[] bytes = IOUtils.toByteArray(profilePicture.getInputStream());
            String filename = profilePicture.getSubmittedFileName();
            String path = BASE_PATH + "/" + filename;

            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(bytes);
            }

            return filename;
        }
        return null;
    }
}
