data class Arguments(var h: Boolean,
                     val login: String?,
                     val pass: String?,
                     val role: String?,
                     val res: String?,
                     val ds: String?,
                     val de: String?,
                     val vol: String?) {
    fun isEmpty(): Boolean {
        return login == null && pass == null && role == null && res == null && ds == null && de == null && vol == null
    }
    fun hasHelp(): Boolean = h
}