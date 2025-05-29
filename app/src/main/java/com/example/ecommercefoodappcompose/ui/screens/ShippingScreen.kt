package com.example.ecommercefoodappcompose.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
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
    val name = remember { mutableStateOf(shippingState.name) }
    val phone = remember { mutableStateOf(shippingState.phone) }
    val email = remember { mutableStateOf(shippingState.email) }
    val county = remember { mutableStateOf(shippingState.county) }
    val city = remember { mutableStateOf(shippingState.city) }
    val street = remember { mutableStateOf(shippingState.street) }
    val streetNumber = remember { mutableStateOf(shippingState.streetNumber) }
    val apartmentDetails = remember { mutableStateOf(shippingState.apartmentDetails) }
    val postalCode = remember { mutableStateOf(shippingState.postalCode) }
    val selectedDeliveryOption = remember { mutableStateOf(shippingState.deliveryOption) }

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
            BottomNavigationBar(
                navController
            )
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val (
                nameField,
                phoneField,
                emailField,
                countyField,
                cityField,
                streetField,
                streetNumberField,
                apartmentNumberField,
                postalCodeField,
                deliveryOptions,
                continueBtn
            ) = createRefs()

            TextFieldWithIcon(
                fieldName = "Name",
                fieldValue = name,
                icon = Icons.Default.Person,
                iconDescription = "Person Icon",
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .constrainAs(nameField) {
                        top.linkTo(parent.top, margin = 25.dp)
                        start.linkTo(parent.start)
                    },
                focusRequester = focusRequesters[0],
                onNext = { focusRequesters[1].requestFocus() }
            )

            TextFieldWithIcon(
                fieldName = "Phone",
                fieldValue = phone,
                icon = Icons.Default.Phone,
                iconDescription = "Phone Icon",
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .constrainAs(phoneField) {
                        top.linkTo(nameField.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                    },
                focusRequester = focusRequesters[1],
                onNext = { focusRequesters[2].requestFocus() }
            )

            TextFieldWithIcon(
                fieldName = "Email",
                fieldValue = email,
                icon = Icons.Default.Email,
                iconDescription = "Email Icon",
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .constrainAs(emailField) {
                        top.linkTo(phoneField.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                    },
                focusRequester = focusRequesters[2],
                onNext = { focusRequesters[3].requestFocus() }
            )

            TextFieldWithIcon(
                fieldName = "County",
                fieldValue = county,
                icon = Icons.Default.LocationOn,
                iconDescription = "County Icon",
                modifier = Modifier
                    .width(160.dp)
                    .constrainAs(countyField) {
                        top.linkTo(emailField.bottom, margin = 10.dp)
                        start.linkTo(parent.start, margin = 25.dp)
                    },
                focusRequester = focusRequesters[3],
                onNext = { focusRequesters[4].requestFocus() }
            )

            TextFieldWithIcon(
                fieldName = "City",
                fieldValue = city,
                icon = Icons.Default.LocationOn,
                iconDescription = "City Icon",
                modifier = Modifier
                    .width(211.dp)
                    .padding(end = 20.dp)
                    .constrainAs(cityField) {
                        top.linkTo(emailField.bottom, margin = 10.dp)
                        start.linkTo(countyField.end, margin = 10.dp)
                    },
                focusRequester = focusRequesters[4],
                onNext = { focusRequesters[5].requestFocus() }
            )

            TextFieldWithIcon(
                fieldName = "Street",
                fieldValue = street,
                icon = Icons.Default.Home,
                iconDescription = "Street Icon",
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .constrainAs(streetField) {
                        top.linkTo(cityField.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                    },
                focusRequester = focusRequesters[5],
                onNext = { focusRequesters[6].requestFocus() }
            )
            TextFieldWithIcon(
                fieldName = "Street No.",
                fieldValue = streetNumber,
                icon = Icons.Default.Home,
                iconDescription = "Street Number",
                modifier = Modifier
                    .width(160.dp)
                    .constrainAs(streetNumberField) {
                        top.linkTo(streetField.bottom, margin = 10.dp)
                        start.linkTo(parent.start, margin = 25.dp)
                    },
                focusRequester = focusRequesters[6],
                onNext = { focusRequesters[7].requestFocus() }
            )
            TextFieldWithIcon(
                fieldName = "Apt, Suite, etc.",
                fieldValue = apartmentDetails,
                icon = Icons.Default.Home,
                iconDescription = "Apartment Number",
                modifier = Modifier
                    .width(211.dp)
                    .padding(end = 20.dp)
                    .constrainAs(apartmentNumberField) {
                        top.linkTo(streetField.bottom, margin = 10.dp)
                        start.linkTo(streetNumberField.end, margin = 10.dp)
                    },
                focusRequester = focusRequesters[7],
                onNext = { focusRequesters[8].requestFocus() }
            )

            TextFieldWithIcon(
                fieldName = "Postal Code",
                fieldValue = postalCode,
                icon = Icons.Default.Home,
                iconDescription = "Postal Code",
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .constrainAs(postalCodeField) {
                        top.linkTo(apartmentNumberField.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                    },
                focusRequester = focusRequesters[8],
                imeAction = ImeAction.Done,
                onDone = {}
            )

            DeliveryOptionsGroup(
                selectedOption = selectedDeliveryOption.value,
                onOptionSelected = { selectedDeliveryOption.value = it },
                modifier = Modifier.constrainAs(deliveryOptions) {
                    top.linkTo(postalCodeField.bottom, margin = 15.dp)
                    start.linkTo(parent.start, margin = 25.dp)
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .constrainAs(continueBtn) {
                        bottom.linkTo(parent.bottom, margin = 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    foodViewModel.updateShippingDetails(
                        ShippingDetailsItem(
                            name = name.value,
                            phone = phone.value,
                            email = email.value,
                            county = county.value,
                            city = city.value,
                            street = street.value,
                            streetNumber = streetNumber.value,
                            apartmentDetails = apartmentDetails.value,
                            postalCode = postalCode.value,
                            deliveryOption = selectedDeliveryOption.value
                        )
                    )
                    navController.navigate("checkout_screen")
                }
            ) {
                Text(text = "Continue")
            }
        }
    }
}
