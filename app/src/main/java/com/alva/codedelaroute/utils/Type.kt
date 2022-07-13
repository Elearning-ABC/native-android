package com.alva.codedelaroute.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.alva.codedelaroute.R

val PoppinsFont = FontFamily(
    Font(R.font.poppins_black, weight = FontWeight.Black),
    Font(R.font.poppins_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.poppins_bold, weight = FontWeight.Bold),
    Font(R.font.poppins_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.poppins_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.poppins_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.poppins_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.poppins_extralightitalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(R.font.poppins_light, weight = FontWeight.Light),
    Font(R.font.poppins_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.poppins_medium, weight = FontWeight.Medium),
    Font(R.font.poppins_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.poppins_regular, weight = FontWeight.Normal),
    Font(R.font.poppins_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.poppins_semibold, weight = FontWeight.SemiBold),
    Font(R.font.poppins_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.poppins_thin, weight = FontWeight.Thin),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = PoppinsFont,
//    body1 = MaterialTheme.typography.body1.copy(fontFamily = PoppinsFont),
//    body2 = MaterialTheme.typography.body2.copy(fontFamily = PoppinsFont),
//    button = MaterialTheme.typography.button.copy(fontFamily = PoppinsFont),
//    caption = MaterialTheme.typography.caption.copy(fontFamily = PoppinsFont),
//    h1 = MaterialTheme.typography.h1.copy(fontFamily = PoppinsFont),
//    h2 = MaterialTheme.typography.h2.copy(fontFamily = PoppinsFont),
//    h3 = MaterialTheme.typography.h3.copy(fontFamily = PoppinsFont),
//    h4 = MaterialTheme.typography.h4.copy(fontFamily = PoppinsFont),
//    h5 = MaterialTheme.typography.h5.copy(fontFamily = PoppinsFont),
//    h6 = MaterialTheme.typography.h6.copy(fontFamily = PoppinsFont),
//    overline = MaterialTheme.typography.overline.copy(fontFamily = PoppinsFont),
//    subtitle1 = MaterialTheme.typography.subtitle1.copy(fontFamily = PoppinsFont),
//    subtitle2 = MaterialTheme.typography.subtitle2.copy(fontFamily = PoppinsFont),
)