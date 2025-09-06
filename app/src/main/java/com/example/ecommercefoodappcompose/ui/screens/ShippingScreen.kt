package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommercefoodappcompose.data.local.model.FieldState
import com.example.ecommercefoodappcompose.data.local.model.ShippingDetailsItem
import com.example.ecommercefoodappcompose.ui.components.DeliveryOptionsGroup
import com.example.ecommercefoodappcompose.ui.components.TextFieldWithIcon
import com.example.ecommercefoodappcompose.ui.components.bottomBar.BottomNavigationBar
import com.example.ecommercefoodappcompose.ui.theme.AppTypography
import com.example.ecommercefoodappcompose.ui.viewmodel.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingScreen(
    navController: NavController,
    foodViewModel: FoodViewModel
) {
    val focusRequesters = List(9) { remember { FocusRequester() } }
    val shippingState = foodViewModel.shippingDetailsState
    val name = remember { FieldState(shippingState.name, ::validateName) }
    val phone = remember { FieldState(shippingState.phone, ::validatePhone) }
    val email = remember { FieldState(shippingState.email, ::validateEmail) }
    val county = remember { FieldState(shippingState.county, ::validateCounty) }
    val city = remember { FieldState(shippingState.city, ::validateCity) }
    val street = remember { FieldState(shippingState.street, ::validateStreet) }
    val streetNumber = remember { FieldState(shippingState.streetNumber, ::validateStreetNumber) }
    val unitDetails = remember { FieldState(shippingState.unitDetails, ::validateUnitDetails) }
    val postalCode = remember { FieldState(shippingState.postalCode, ::validatePostalCode) }
    val selectedDeliveryOption = remember { mutableStateOf(shippingState.deliveryOption) }

    val isFormValid by remember {
        derivedStateOf {
            name.isValid() &&
                phone.isValid() &&
                email.isValid() &&
                county.isValid() &&
                city.isValid() &&
                street.isValid() &&
                streetNumber.isValid() &&
                unitDetails.isValid() &&
                postalCode.isValid()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Shipping Screen",
                        style = AppTypography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            TextFieldWithIcon(
                fieldName = "Name",
                fieldValue = name.state.value,
                onValueChange = { name.onValueChange(it) },
                icon = Icons.Default.Person,
                iconDescription = "Person Icon",
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp)
                    .fillMaxWidth(),
                focusRequester = focusRequesters[0],
                onNext = { focusRequesters[1].requestFocus() },
                isError = name.error.value != null,
                errorMessage = if (name.touched.value) name.error.value else null
            )

            TextFieldWithIcon(
                fieldName = "Phone",
                fieldValue = phone.state.value,
                onValueChange = { phone.onValueChange(it) },
                icon = Icons.Default.Phone,
                iconDescription = "Phone Icon",
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 10.dp)
                    .fillMaxWidth(),
                focusRequester = focusRequesters[1],
                onNext = { focusRequesters[2].requestFocus() },
                isError = phone.error.value != null,
                errorMessage = if (phone.touched.value) phone.error.value else null
            )

            TextFieldWithIcon(
                fieldName = "Email",
                fieldValue = email.state.value,
                onValueChange = { email.onValueChange(it) },
                icon = Icons.Default.Email,
                iconDescription = "Email Icon",
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 10.dp)
                    .fillMaxWidth(),
                focusRequester = focusRequesters[2],
                onNext = { focusRequesters[3].requestFocus() },
                isError = email.error.value != null,
                errorMessage = if (email.touched.value) email.error.value else null
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
            ) {
                TextFieldWithIcon(
                    fieldName = "County",
                    fieldValue = county.state.value,
                    onValueChange = { county.onValueChange(it) },
                    icon = Icons.Default.LocationOn,
                    iconDescription = "County Icon",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),
                    focusRequester = focusRequesters[3],
                    onNext = { focusRequesters[4].requestFocus() },
                    isError = county.error.value != null,
                    errorMessage = if (county.touched.value) county.error.value else null
                )

                TextFieldWithIcon(
                    fieldName = "City",
                    fieldValue = city.state.value,
                    onValueChange = { city.onValueChange(it) },
                    icon = Icons.Default.LocationOn,
                    iconDescription = "City Icon",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp),
                    focusRequester = focusRequesters[4],
                    onNext = { focusRequesters[5].requestFocus() },
                    isError = city.error.value != null,
                    errorMessage = if (city.touched.value) city.error.value else null
                )
            }

            TextFieldWithIcon(
                fieldName = "Street",
                fieldValue = street.state.value,
                onValueChange = { street.onValueChange(it) },
                icon = Icons.Default.Home,
                iconDescription = "Street Icon",
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 10.dp)
                    .fillMaxWidth(),
                focusRequester = focusRequesters[5],
                onNext = { focusRequesters[6].requestFocus() },
                isError = street.error.value != null,
                errorMessage = if (street.touched.value) street.error.value else null
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
            ) {
                TextFieldWithIcon(
                    fieldName = "Street No.",
                    fieldValue = streetNumber.state.value,
                    onValueChange = { streetNumber.onValueChange(it) },
                    icon = Icons.Default.Home,
                    iconDescription = "Street Number",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),
                    focusRequester = focusRequesters[6],
                    onNext = { focusRequesters[7].requestFocus() },
                    isError = streetNumber.error.value != null,
                    errorMessage = if (streetNumber.touched.value) {
                        streetNumber.error.value
                    } else {
                        null
                    }
                )

                TextFieldWithIcon(
                    fieldName = "Unit details",
                    fieldValue = unitDetails.state.value,
                    onValueChange = { unitDetails.onValueChange(it) },
                    icon = Icons.Default.Home,
                    iconDescription = "Unit Details",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp),
                    focusRequester = focusRequesters[7],
                    onNext = { focusRequesters[8].requestFocus() },
                    isError = unitDetails.error.value != null,
                    errorMessage = if (unitDetails.touched.value) unitDetails.error.value else null
                )
            }

            TextFieldWithIcon(
                fieldName = "Postal Code",
                fieldValue = postalCode.state.value,
                onValueChange = { postalCode.onValueChange(it) },
                icon = Icons.Default.Home,
                iconDescription = "Postal Code",
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 10.dp)
                    .fillMaxWidth(),
                focusRequester = focusRequesters[8],
                imeAction = ImeAction.Done,
                onDone = {},
                isError = postalCode.error.value != null,
                errorMessage = if (postalCode.touched.value) postalCode.error.value else null
            )

            DeliveryOptionsGroup(
                selectedOption = selectedDeliveryOption.value,
                onOptionSelected = { selectedDeliveryOption.value = it },
                modifier = Modifier.padding(start = 25.dp, top = 15.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 15.dp, bottom = 15.dp),
                onClick = {
                    foodViewModel.updateShippingDetails(
                        ShippingDetailsItem(
                            name = name.state.value,
                            phone = phone.state.value,
                            email = email.state.value,
                            county = county.state.value,
                            city = city.state.value,
                            street = street.state.value,
                            streetNumber = streetNumber.state.value,
                            unitDetails = unitDetails.state.value,
                            postalCode = postalCode.state.value,
                            deliveryOption = selectedDeliveryOption.value
                        )
                    )
                    navController.navigate("checkout_screen")
                },
                enabled = isFormValid
            ) {
                Text(text = "Continue")
            }
        }
    }
}

fun validateName(name: String): String? = when {
    name.isBlank() -> "Name is required"
    !name.matches(Regex("^[A-Za-z ]+$")) -> "Name can contain only letters"
    else -> null
}

fun validatePhone(phone: String): String? = when {
    phone.isBlank() -> "Phone is required"
    !phone.matches(Regex("^\\d{10,15}$")) -> "Invalid phone number"
    else -> null
}

fun validateEmail(email: String): String? = when {
    email.isBlank() -> "Email is required"
    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email"
    else -> null
}

fun validateCounty(county: String): String? = when {
    county.isBlank() -> "County is required"
    !county.matches(Regex("^[A-Za-z ]+[0-9]*$"))
    -> "County can contain letters and digits only after letters"

    else -> null
}

fun validateCity(city: String): String? = when {
    city.isBlank() -> "City is required"
    !city.matches(Regex("^[A-Za-z ]+$")) -> "City can contain only letters"
    else -> null
}

fun validateStreet(street: String): String? = when {
    street.isBlank() -> "Street is required"
    !street.matches(Regex("^[A-Za-zĂÂÎȘȚăâîșț'\\- ]+$"))
    -> "Street can contain only letters, spaces, apostrophes or hyphens"

    else -> null
}

fun validateStreetNumber(number: String): String? = when {
    number.isBlank() -> "Street number is required"
    !number.matches(Regex("^\\d+[A-Za-z]?(-\\d+)?$")) -> "Invalid street number"
    else -> null
}

fun validateUnitDetails(details: String): String? = when {
    details.isBlank() -> "Unit details are required"
    !details.matches(Regex("^[A-Za-zĂÂÎȘȚăâîșț0-9 ,.#-]+$")) -> "Invalid unit details"
    else -> null
}

fun validatePostalCode(postalCode: String): String? = when {
    postalCode.isBlank() -> "Postal code is required"
    !postalCode.matches(Regex("^[A-Za-z0-9 ]{4,10}$")) -> "Invalid postal code"
    else -> null
}
