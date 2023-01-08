package wowrld.textToDatabase.service

import org.springframework.web.multipart.MultipartFile

interface CommonService {

    fun uploadFile(file:MultipartFile?) : Map<String, Any>;

}