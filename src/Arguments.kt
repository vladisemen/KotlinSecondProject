data class Arguments(var h: Boolean,
                     val login: String?,
                     val pass: String?,
                     val role: String?,
                     /**
                      * ресурс
                      */
                     val res: String?,
                     /**
                      *дата начальная
                      */
                     val ds: String?,
                     /**
                      *дата конечная
                      */
                     val de: String?,
                     /**
                      *число
                      */
                     val vol: String?) {
    /**
     * Пустой?
     */
    fun isEmpty(): Boolean {
        return login == null && pass == null && role == null && res == null && ds == null && de == null && vol == null
    }

    /**
     * содержит справку?
     */
    fun hasHelp(): Boolean = h

    /**
     * Содержит логин и пароль?
     */
    fun hasAuthentification(): Boolean = (login != null && pass != null)
}