package com.example.telephonedirectory

class contact {
    var id : Int? = null
    var name:String? = null
    var phoneNumber : String?= null
    var image : String? = null
    override fun toString(): String {
        return "id:$id   name:$name   phonenumber:$phoneNumber image:$image"
    }
}