package triphub.helpers;

import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class that provides functionality to handle image files, particularly
 * profile pictures.
 */
public class ImageHelper {

	/**
	 * The base path where the images will be stored. You may need to adjust this
	 * path based on your deployment environment.
	 */
	private static final String BASE_PATH = "/Users/brendan/EnvJEE/Tools/wildfly-18.0.0.Final/data/triphub/images";

//    private static final String BASE_PATH = "D/EnvJEE/Tools/wildfly-18.0.1.Final/data/triphub/images";

	/**
	 * Processes the provided profile picture, storing it in a predefined directory
	 * and returning the filename. If the profile picture is null or there's any
	 * issue during the processing, null will be returned.
	 *
	 * @param profilePicture The profile picture as a {@link Part} object.
	 * @return The filename of the stored image or null if the profile picture is
	 *         null or there was an error.
	 * @throws IOException if there's an error during reading the input stream or
	 *                     writing the file.
	 */
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
