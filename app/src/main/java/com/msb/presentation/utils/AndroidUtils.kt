package xyz.teknol.preto3.presentation.utils

import android.os.Build
import android.os.Build.VERSION_CODES

class AndroidUtils {
    companion object {
        fun getDeviceOS(): String {
            val builder = StringBuilder()
            try {
                builder.append("android : ").append(Build.VERSION.RELEASE)
                val fields = VERSION_CODES::class.java.fields
                for (field in fields) {
                    val fieldName = field.name
                    var fieldValue = -1
                    try {
                        fieldValue = field.getInt(Any())
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    }
                    if (fieldValue == Build.VERSION.SDK_INT) {
                        builder.append(" : ").append(fieldName).append(" : ")
                        builder.append("sdk=").append(fieldValue)
                    }
                }
            } catch (e: Throwable) {
                builder.append("OS Not Found")
                e.printStackTrace()
            }
            return when (Build.VERSION.SDK_INT) {
                11 -> "Honeycomb" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                12 -> "Honeycomb" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                13 -> "Honeycomb" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                14 -> "Ice Cream Sandwich" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                15 -> "Ice Cream Sandwich" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                16 -> "Jelly Bean" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                17 -> "Jelly Bean" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                18 -> "Jelly Bean" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                19 -> "KitKat" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                20 -> "KitKat" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                21 -> "Lollipop" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                22 -> "Lollipop" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                23 -> "Marshmallow" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                24 -> "Nougat" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                25 -> "Nougat" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                26 -> "Oreo" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                27 -> "Oreo" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                28 -> "Pie" + " " + Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"
                else -> builder.toString()
            }
        }

        fun getDeviceModelName(): String {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else capitalize(manufacturer) + " - " + model
        }

        private fun capitalize(str: String): String {
            if (str.isEmpty()) {
                return str
            }
            val arr = str.toCharArray()
            var capitalizeNext = true
            val phrase = java.lang.StringBuilder()
            for (c in arr) {
                if (capitalizeNext && Character.isLetter(c)) {
                    phrase.append(Character.toUpperCase(c))
                    capitalizeNext = false
                    continue
                } else if (Character.isWhitespace(c)) {
                    capitalizeNext = true
                }
                phrase.append(c)
            }
            return phrase.toString()
        }
    }
}