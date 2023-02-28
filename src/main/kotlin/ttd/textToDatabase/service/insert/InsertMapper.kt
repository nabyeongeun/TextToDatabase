package ttd.textToDatabase.service.insert

import org.apache.ibatis.annotations.Mapper

@Mapper
interface InsertMapper {

    fun insert(map : Map<String,Any>)


}