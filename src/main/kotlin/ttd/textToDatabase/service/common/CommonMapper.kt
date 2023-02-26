package ttd.textToDatabase.service.common

import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommonMapper {

    public fun insert(param : Map<String,Any?>) : Int

}