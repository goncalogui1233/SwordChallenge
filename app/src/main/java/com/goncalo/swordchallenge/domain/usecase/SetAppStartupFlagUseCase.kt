package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.domain.repository.AppSettingsRepository
import javax.inject.Inject

class SetAppStartupFlagUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) {

    suspend operator fun invoke(isAppFirstRun: Boolean) {
        appSettingsRepository.setAppStartupFlag(isAppFirstRun)
    }

}