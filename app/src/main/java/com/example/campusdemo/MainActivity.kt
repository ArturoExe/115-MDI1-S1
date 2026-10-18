package  com.example.campusdemo

// Import Packages
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import  androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.items

// Session 2 imports

import androidx.compose.ui.platform.LocalContext
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import  java.time.format.FormatStyle
import java.util.Locale

// Update Data Class

data class CampusResource(
    @param:StringRes val nameRes: Int,
    @param:StringRes val category: Int,
    @param:StringRes val hoursRes: Int,
    val  lastUpdate: LocalDate,
    val  reviewCount: Int,
    val capacity: Int,
    val fee: Double
    )

val sampleResources =  listOf(
    CampusResource(
         nameRes = R.string.resource_intl_office_name,
         category = R.string.resource_intl_office_category,
         hoursRes = R.string.resource_intl_office_hours,
         lastUpdate = LocalDate.of(2026,8,3),
        reviewCount = 1,
        capacity = 50,
        fee = 0.99
    ),
    CampusResource(
        nameRes = R.string.resource_library_name,
        category = R.string.resource_library_category,
        hoursRes = R.string.resource_library_hours,
        lastUpdate = LocalDate.of(2026,2,4),
        reviewCount = 1,
        capacity = 120,
        fee = 1.99
    ),
    CampusResource(
        nameRes = R.string.resource_veterans_name,
        category = R.string.resource_veterans_category,
        hoursRes = R.string.resource_veterans_hours,
        lastUpdate = LocalDate.of(2026,6,10),
        reviewCount = 1,
        capacity = 10,
        fee = 2.99
    )


)

class MainActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                CampusConnectScreen()
            }
        }
    }
}


@Composable
fun CampusConnectScreen (){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.app_title), style = MaterialTheme.typography.headlineMedium)
        Text(stringResource(R.string.app_subtitle), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))


        Text(stringResource(R.string.resources_section_header),style = MaterialTheme.typography.titleSmall)


        Spacer(modifier = Modifier.height(16.dp))

        // We create some resources to iterate the language
        LazyColumn {
            items(sampleResources) { resource ->
                ResourceCard(resource)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

    }

}


@Composable
fun ResourceCard(resource: CampusResource){

    // what is LocalContext
    val context = LocalContext.current

    val locale = Locale.getDefault()

    // date format
    val dateFullFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(locale)
    val dateShortFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale)

    // currency format
    val  currencyFormatter = NumberFormat.getCurrencyInstance(locale)
    val integerFormatter = NumberFormat.getIntegerInstance(locale)


    val name = stringResource(resource.nameRes)
    val category = stringResource(resource.category)

    val  hours = stringResource(
        R.string.hours_label,
        stringResource(resource.hoursRes))

    // what is context and why are we using getQuantityFucntion
    val reviewText = context.resources.getQuantityString(
        R.plurals.review_count,
        resource.reviewCount,
        resource.reviewCount
    )

    val capacityFormatted = integerFormatter.format(resource.capacity)
    val contentDesc = stringResource(R.string.content_desc_resource_card,name,category,hours)

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                name,
                style = MaterialTheme.typography.titleMedium
            )


            Text(stringResource(R.string.capacity_label),
                style = MaterialTheme.typography.bodySmall
            )


            Text(hours)

            Text(stringResource(R.string.capacity_label,capacityFormatted))

            Text(reviewText)

            if (resource.fee > 0.0){
             Text(stringResource(
                 R.string.fee_label,
                 currencyFormatter.format(resource.fee)))
            }

            Text(
                stringResource(R.string.updated_short_label,resource.lastUpdate.format(dateShortFormatter)),
                style=MaterialTheme.typography.bodySmall
            )


            Text(stringResource(
                R.string.last_updated_label,resource.lastUpdate.format(dateFullFormatter)),
                style = MaterialTheme.typography.bodySmall
            )


        }
    }

}