package com.example.myapplication

// Residence class
data class Residence(
    var id_res: Int = 0,
    var apartment_total: Int = 0,
    var address: String = ""
) {
    // No-argument constructor required for Firebase serialization
    constructor() : this(0, 0, "")
}

// Apartment class
data class Apartment(
    var id_apart: Int = 0,
    var build_state: Int = 0,
    var floor_id: Int = 0,
    var price: Double = 0.0,
    var esti_time_ready: Int = 0,
    var apart_img: String = "",
    var d_prototype: String = "" ,
    var description : String = ""
) {
    // No-argument constructor required for Firebase serialization
    constructor() : this(0, 0, 0, 0.0, 0, "", "")
}

// Client class
data class Client(
    var id_client: Int = 0,
    var id_apart: Int = 0,
    var id_res: Int = 0,
    var name: String = "",
    var family_name: String = ""
) {
    // No-argument constructor required for Firebase serialization
    constructor() : this(0, 0, 0, "", "")
}

data class ChatMessage(val text: String, val isUser: Boolean)

