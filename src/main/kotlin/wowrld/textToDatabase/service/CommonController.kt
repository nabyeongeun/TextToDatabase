package wowrld.textToDatabase.service

import jakarta.annotation.Resource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.Optional

@RestController
@RequestMapping("/uploadFile")
class CommonController {

    @Autowired
    lateinit var service: CommonService

    @GetMapping()
    fun uploadFile(@RequestParam("file", required=false) file:MultipartFile?) : Map<String,Any> {

        return service.uploadFile(file)
    }

}