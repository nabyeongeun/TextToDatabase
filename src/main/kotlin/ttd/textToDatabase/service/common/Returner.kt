package ttd.textToDatabase.service.common

fun returnSuccess(successMsg: String): Map<String, Any> {
    val returnObject: MutableMap<String, Any> = HashMap()
    returnObject["status"] = "SUCCESS"
    returnObject["statusDescription"] = successMsg
    return returnObject
}
