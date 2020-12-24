package hazae41.wakeup

fun getLang(locale: String): Lang {
  val first = locale.split("_")[0]

  if (first == "en")
    return English
  if (first == "fr")
    return French
  if (first == "es")
    return Spanish
  return English
}

interface Lang {
  fun title(): String
  fun book(): Array<String>
}

object English : Lang {
  override fun title() = "Diary"

  // @formatter:off
  override fun book() = arrayOf("""
    I wake up in a new world, with a new life.
    
    I don't remember what happened, but I'm now here.
  """.trimIndent(), """
    I need to find the civilization, if it exists.
    
    Otherwise, let's not make the same mistakes.
  """.trimIndent(), """
    Maybe I could start by finding other people.
    
    But all I need right now is a bed to rest in.
  """.trimIndent())
  // @formatter:on
}

object French : Lang {
  override fun title() = "Journal"

  // @formatter:off
  override fun book() = arrayOf("""
    Je me réveille dans un nouveau monde, avec une nouvelle vie.
    
    Je ne me rappelle de rien, mais je suis désormais ici.
  """.trimIndent(), """
    Je dois trouver la civilisation, si elle existe.
    
    Sinon, ne faisons pas les mêmes erreurs qu'autrefois.
  """.trimIndent(), """
    Je pourrais peut-être commencer par retrouver mes semblables.
    
    Mais tout ce dont j'ai besoin là tout de suite c'est d'un lit pour me reposer.
  """.trimIndent())
  // @formatter:on
}

object Spanish : Lang {
  override fun title() = "Diario"

  // @formatter:off
  override fun book() = arrayOf("""
    Me despierto en un nuevo mundo, con una nueva vida.
    
    No recuerdo nada, pero estoy aquí ahora.
  """.trimIndent(), """
    Debo encontrar la civilización, si es que existe.
    
    Si no, no cometamos los mismos errores que hicimos en el pasado.
  """.trimIndent(), """
    Tal vez podría empezar por encontrar a mi gente.
    
    Pero todo lo que necesito ahora es una cama para descansar.
  """.trimIndent())
  // @formatter:on
}