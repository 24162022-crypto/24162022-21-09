package vn.iotstar.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    /** Upload file len Cloudinary, tra ve secure_url. */
    String upload(MultipartFile file, String folder);
}
