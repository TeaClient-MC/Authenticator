import tk.teaclient.auth.*

fun main() {
    val authenticator = Authenticator("4479bf0e-4968-426e-92fa-b2890572c4f7", logging = true)
    val minecraftSessionResult = authenticator.login().join()
    println(minecraftSessionResult)
}