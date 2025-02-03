package com.trainee.project.swiggy.location

interface SavedProfileAddressListener {

    fun deleteSavedAddrress(id:Int,phone:String)
    fun editSavedAddrress(id:Int,address:String)


}