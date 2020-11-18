import models.Roles

data class RoleResource(
        val role: Roles,
        val resource: String,
        val idUser: Int,
)