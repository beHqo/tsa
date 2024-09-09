package com.example.android.strikingarts.domain.usecase.combo

import app.cash.turbine.test
import com.example.android.strikingarts.data.local.util.assertCombosAreEqual
import com.example.android.strikingarts.data.rearHighKickStepForwardSlashingElbowNotInDB
import com.example.android.strikingarts.data.repository.FakeComboRepository
import com.example.android.strikingarts.data.stepForwardSpearElbowNotInDB
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RetrieveComboListUseCaseTest {
    private val repository = FakeComboRepository()
    private val useCase = RetrieveComboListUseCase(repository)

    @Test
    fun `Given the comboList Flow, When objects are inserted in the database, Then Flow should emit`() =
        runTest {
            val flow = useCase.comboList

            flow.test {
                repository.insert(stepForwardSpearElbowNotInDB, emptyList())
                assertCombosAreEqual(awaitItem().last(), stepForwardSpearElbowNotInDB)
                awaitComplete()
            }

            flow.test {
                repository.insert(rearHighKickStepForwardSlashingElbowNotInDB, emptyList())
                assertCombosAreEqual(
                    awaitItem().last(),
                    rearHighKickStepForwardSlashingElbowNotInDB
                )
                awaitComplete()
            }
        }
}