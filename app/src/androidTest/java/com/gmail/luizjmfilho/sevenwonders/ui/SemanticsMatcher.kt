package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher

fun hasEditableText(editableText: String): SemanticsMatcher {
    return SemanticsMatcher(
        description = "Editable Text = [$editableText]",
        matcher = { node ->
            node.config.getOrNull(SemanticsProperties.EditableText)?.text == editableText
        }
    )
}