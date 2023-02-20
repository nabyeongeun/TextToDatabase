package ttd.textToDatabase.service

import org.springframework.web.multipart.MultipartFile

interface CommonService {

    fun initPage() : Map<String,Any>

    fun uploadFile(filePath:String) : MutableMap<String, Any>

}