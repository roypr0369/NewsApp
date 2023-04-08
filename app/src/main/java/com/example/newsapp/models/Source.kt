package com.example.newsapp.models

class Source : java.io.Serializable{
    var id: String
    var name: String
    constructor(){
        id = ""
        name = ""
    }
    constructor(id :String?, name: String?){
        this.id = id ?: ""
        this.name = name ?: ""
    }
}