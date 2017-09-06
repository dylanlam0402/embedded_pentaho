package vn.kms.rabbitholes.web.util;

/**
 * @author kietlam
 */
public class FileExtension {
    private static final String[] whileList = {"IMG", "TXT", "JPG", "DOC", "DOCX", "PNG", "XLS"};

    public static boolean checkFileExtension(String fileName) {
        String[] fileType = fileName.split(Constant.DOT_SEPARATOR);
        int index = fileType.length;
        for (String type : whileList) {
            if (fileType[index - 1].toUpperCase().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
