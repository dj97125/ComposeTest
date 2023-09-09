import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danielcaballero.composetest.common.ConnectionStatus
import com.danielcaballero.composetest.common.StateAction
import com.danielcaballero.composetest.presentation.components.CountryListViewLandscape
import com.danielcaballero.composetest.presentation.components.CountryListViewPortrait
import com.danielcaballero.composetest.view_model.CountryViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun CountryListView(
    viewModel: CountryViewModel = koinViewModel(),
    networkStatus: ConnectionStatus
) {
    val state by viewModel.countryResponse.collectAsStateWithLifecycle()
    val configuration = LocalConfiguration.current
    val orientationMode by remember { mutableIntStateOf(configuration.orientation) }

    if (orientationMode == Configuration.ORIENTATION_PORTRAIT) {
        CountryListViewPortrait(
            networkStatus = networkStatus,
            viewModel = viewModel,
            state = state ?: StateAction.Loading
        )
    } else {
        CountryListViewLandscape(
            networkStatus = networkStatus,
            viewModel = viewModel,
            state = state ?: StateAction.Loading
        )
    }


}







