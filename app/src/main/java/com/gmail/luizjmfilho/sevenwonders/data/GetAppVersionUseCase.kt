package com.gmail.luizjmfilho.sevenwonders.data

import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException
import com.gmail.luizjmfilho.sevenwonders.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetAppVersionUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: Logger,
) {
    operator fun invoke(): String? =
        try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (ex: NameNotFoundException) {
            logger.exception(ex)
            null
        }
}