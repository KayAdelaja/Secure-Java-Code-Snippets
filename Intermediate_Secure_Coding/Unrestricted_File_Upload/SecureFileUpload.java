import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
public class SecureFileUpload extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "uploads";
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 5; // 5MB

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Form must have enctype=multipart/form-data.");
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);

        try {
            List<FileItem> formItems = upload.parseRequest(request);
            for (FileItem item : formItems) {
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String fileExtension = FilenameUtils.getExtension(fileName);

                    // Validate file type
                    if (!isValidFileExtension(fileExtension)) {
                        throw new ServletException("Invalid file type.");
                    }

                    // Prevent directory traversal attacks
                    fileName = FilenameUtils.getName(fileName);

                    String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    item.write(storeFile);

                    response.getWriter().println("File uploaded successfully: " + fileName);
                }
            }
        } catch (Exception ex) {
            throw new ServletException("File upload failed.", ex);
        }
    }

    private boolean isValidFileExtension(String fileExtension) {
        String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "pdf"};
        for (String ext : allowedExtensions) {
            if (ext.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Example usage in a servlet container
    }
}
