package pet.project.tastetips.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ProductDateTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val productDate = productDateToPattern(text.text)

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = when (offset) {
                in 0 until FIRST_DOT_INDEX -> offset
                in FIRST_DOT_INDEX until SECOND_DOT_INDEX -> offset + 1
                in SECOND_DOT_INDEX..8 -> offset + 2
                else -> 10
            }

            override fun transformedToOriginal(offset: Int): Int = when (offset) {
                in 0..FIRST_DOT_INDEX -> offset
                in FIRST_DOT_INDEX + 1..SECOND_DOT_INDEX + 1 -> offset - 1
                in SECOND_DOT_INDEX + 2..MAX_OFFSET -> offset - 2
                else -> 8
            }
        }

        return TransformedText(AnnotatedString(productDate), numberOffsetTranslator)
    }

    companion object {
        const val FIRST_DOT_INDEX = 2
        const val SECOND_DOT_INDEX = 4
        const val MAX_OFFSET = 10
    }
}

internal fun productDateToPattern(productDate: String): String {
    val out = StringBuilder()
    for (i in productDate.indices) {
        out.append(productDate[i])
        when (i) {
            ProductDateTransformation.FIRST_DOT_INDEX - 1,
            ProductDateTransformation.SECOND_DOT_INDEX - 1 -> out.append(".")
        }
    }
    return out.toString()
}