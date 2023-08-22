package com.example.kotlin_cookingapp.presentation.screen.meal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_cookingapp.R
import com.example.kotlin_cookingapp.presentation.component.*
import com.example.kotlin_cookingapp.presentation.screen.base.Screen
import com.example.kotlin_cookingapp.presentation.screen.home.HomeScreen
import com.example.kotlin_cookingapp.presentation.theme.Grey20
import com.example.kotlin_cookingapp.presentation.theme.Red40
import com.example.kotlin_cookingapp.util.LOADING

@Composable
fun MealDetailScreen(
    viewModel: MealDetailViewModel,
    navController: NavHostController,
    onMealClick: (String) -> Unit
) {
    val mealDetailState by viewModel.mealDetailState
        .collectAsState()
    val mealTitleState by viewModel.mealTitle

    Column(modifier = Modifier.fillMaxSize()) {
        NavBar(
            title = stringResource(id = R.string.lbl_detail, mealTitleState),
            onBackPress = {
                navController.navigateUp()
            }
        )

        Spacer(modifier = Modifier.height(height = 20.dp))

        StateHandler(
            state = mealDetailState,
            onLoading = {
                LoadingMealDetail(
                    onLoading = { viewModel.setMealTitle(title = LOADING) }
                )
            },
            onFailure = {
                WarningMessage(textId = R.string.txt_empty_result)
            }
        ) { resource ->
            resource.data?.let { mealDetail ->

                val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp /
                        LocalDensity.current.density

                viewModel.setMealTitle(title = mealDetail.title)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = rememberScrollState())
                ) {

                    if (mealDetail.screenShots?.isEmpty() == false) {
                        NetworkImage(
                            url = mealDetail.thumbnail,
                            crossFade = 1000,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeight(height = screenHeight * 0.6f)
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .align(alignment = Alignment.CenterHorizontally)
                                .clip(shape = MaterialTheme.shapes.medium),
                            onLoading = {
                                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                                    val indicatorRef = createRef()
                                    CircularProgressIndicator(
                                        modifier = Modifier.constrainAs(indicatorRef) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                    )
                                }
                            },
                            onError = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_warning),
                                    contentDescription = "",
                                    tint = Red40
                                )
                            }
                        )
                    } else {
                        mealDetail.screenShots?.let { it ->
                            CarouselView(
                                urls = it.map { it.image },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(height = screenHeight * 0.6f)
                                    .padding(vertical = 8.dp, horizontal = 12.dp)
                                    .align(alignment = Alignment.CenterHorizontally),
                                shape = MaterialTheme.shapes.medium,
                                crossFade = 1000
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(height = 30.dp))
                    Column(modifier = Modifier.padding(horizontal = 5.dp)) {

                        Text(
                            text = mealDetail.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        ExpandableText(
                            text = mealDetail.shortDescription,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(height = 30.dp))
                        Text(
                            text = stringResource(id = R.string.lbl_more),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        ExtraInformationRow(
                            firstTitle = stringResource(id = R.string.lbl_recipe),
                            secondTitle = stringResource(id = R.string.lbl_cuisine),
                            textColor = MaterialTheme.colorScheme.onSurface
                        )
                        ExtraInformationRow(
                            firstTitle = mealDetail.title,
                            secondTitle = mealDetail.tag[0],
                            textColor = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(height = 20.dp))
                        ExtraInformationRow(
                            firstTitle = stringResource(id = R.string.lbl_author),
                            secondTitle = stringResource(id = R.string.lbl_release_date),
                            textColor = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(height = 20.dp))
                        mealDetail.ingredientGroup?.let { it ->
                            Text(
                                text = stringResource(id = R.string.lbl_required_ingredients),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Column {
                                it.forEach {
                                    it.ingredient.forEach{
                                        Text("tbd")
                                    }
                                }

                            }
                        }
                        Spacer(modifier = Modifier.height(height = 20.dp))
                        mealDetail.notes?.let { notes ->
                            Text(
                                text = stringResource(id = R.string.lbl_more),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = notes,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
//
                        LeadingIconButton(
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                            iconResId = R.drawable.ic_xmark_solid,
                            textButton = stringResource(id = R.string.lbl_start_cooking),
                            onClick = {
                                mealDetail.mealUrl?.let { onMealClick(it) }
                            }
                        )
                        Spacer(modifier = Modifier.height(height = 20.dp))
                    }
                }
            }
        }
    }
}