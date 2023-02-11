package ttd.textToDatabase.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class CommonController {

    @Autowired
    lateinit var service: CommonService

    @GetMapping("main")
    fun index(model: Model) : Model {
        model.addAttribute("data", "hello!!");
        return model;
    }

    @PostMapping("/uploadFile.do")
    fun uploadFile(model: Model, @RequestParam("file") file:MultipartFile) : Map<String,Any?> {

        val returnObject = service.uploadFile(file);

        model.addAttribute("file", returnObject);
        return returnObject;
    }

}
