package ttd.textToDatabase.service.common

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import kotlin.math.roundToInt

@RestController
class CommonController {

    @Autowired
    lateinit var service: CommonService

    @GetMapping("main")
    fun index(model: Model) : Model {
        model.addAttribute("data", "hello!!")
        return model
    }

    @GetMapping("/initPage.do")
    fun initPage() : Map<String,Any> {
        return service.initPage()
    }

}
