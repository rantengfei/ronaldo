package aibili.ronaldo.utils;

import java.util.HashMap;
import java.util.Map;

public class MimeTypeUtil {

    public static String getSuffix(String mimeType) {
            Map<String, String> allow_mime_type = new HashMap();
            allow_mime_type.put("image/jpeg","jpg");
            allow_mime_type.put("image/jpg","jpg");
            allow_mime_type.put("image/gif","gif");
            allow_mime_type.put("image/webp","webp");
            allow_mime_type.put("text/markdown","md");
            allow_mime_type.put("application/pdf","pdf");
            allow_mime_type.put("application/msword","doc");
            allow_mime_type.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document","docx");
            allow_mime_type.put("application/vnd.ms-excel","xls");
            allow_mime_type.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","xlsx");
            allow_mime_type.put("application/rtf","rtf");
            allow_mime_type.put("application/zip","zip");
            allow_mime_type.put("application/x-rar","rar");
            allow_mime_type.put("application/x-7z-compressed","7z");
            allow_mime_type.put("audio/mpeg","mp3");
            allow_mime_type.put("vdeo/mp4","mp4");
            allow_mime_type.put("vdeo/quicktime","mov");
            allow_mime_type.put("audio/x-wav","wav");

            return allow_mime_type.get(mimeType);
    }
}
