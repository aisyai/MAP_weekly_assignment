package com.example.lab_week_09

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf // Added import for mutableStateListOf
import androidx.compose.runtime.mutableStateOf // Added import for mutableStateOf
import androidx.compose.runtime.remember // Added import for remember
import androidx.compose.runtime.snapshots.SnapshotStateList // Added import for SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    // We use Modifier.fillMaxSize() to make the surface fill the whole screen
                    modifier = Modifier.fillMaxSize(),
                    // We use MaterialTheme.colorScheme.background to get the background color
                    // and set it as the color of the surface
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    App(
                        navController = navController
                    )
                    // Home()
                }
            }
        }
    }
}

// Declare a data class called Student (Commit 2, Step 2)
data class Student(
    var name: String
)

// The old Home composable (from Commit 1) has been removed,
// and a new Home without parameters is declared. (Commit 2, Step 3 & 6)
@Composable
fun Home(
    navigateFromHomeToResult: (String) -> Unit
) {
    // Here, we create a mutable state List of Student
    // We use remember to make the List remember its value
    // This is so that the List won't be recreated when the composable recomposes
    // We use mutableStateListOf to make the List mutable
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    val context = LocalContext.current

    // Here, we create a mutable state of Student
    // This is so that we can get the value of the input field
    var inputField = remember { mutableStateOf(Student("")) }

    // We call the HomeContent composable
    // Here, we pass:
    // listData to show the List of items inside HomeContent
    // inputField.value to show the input field value inside HomeContent
    // A Lambda function to update the value of the inputField
    // A Lambda function to add the inputField to the ListData
    HomeContent(
        listData = listData,
        inputField = inputField.value,
        onInputValueChange = { input -> inputField.value = inputField.value.copy(name = input) },
        onButtonClick = {
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                inputField.value = Student("")
            } else {
                Toast.makeText(
                    context,
                    "Name cannot be blank!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },

        navigateFromHomeToResult = {
            // 1. Setup Moshi
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            // 2. Buat Adapter untuk List<Student>
            // Perhatikan: Kita harus menggunakan tipe List<Student> untuk adapter, bukan SnapshotStateList
            val type = com.squareup.moshi.Types.newParameterizedType(List::class.java, Student::class.java)
            val adapter = moshi.adapter<List<Student>>(type)

            // 3. Konversi listData ke JSON String
            val jsonList = adapter.toJson(listData.toList())

            // 4. Navigasi dengan String JSON
            navigateFromHomeToResult(jsonList)
        }
    )
}

// HomeContent is used to display the content of the Home composable (Commit 2, Step 4)
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    navigateFromHomeToResult: () -> Unit
) {

    // Here, we use LazyColumn to display a list of items Lazily
    LazyColumn {
        //Here, we use item to display an item inside the LazyColumn
        item {
            Column(
                //Modifier.padding(16.dp) is used to add padding to the Column
                //You can also use Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            //to add padding horizontally and vertically
            //or Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            //to add padding to each side
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            //Alignment.CenterHorizontally is used to align the Column horizontally
            //You can also use verticalArrangement = Arrangement.Center to align the Column vertically
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
            //Here, we call the OnBackgroundTitleText UI Element
            OnBackgroundTitleText(text = stringResource(
                id = R.string.enter_item)
            )

            //Here, we use TextField to display a text input field
            TextField(
                //Set the value of the input field
                value = inputField.name,
                //Set the keyboard type of the input field
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                //Set what happens when the value of the input field changes
                onValueChange = {
                    val cleanInput = it.replace("\n", "")
                    onInputValueChange(cleanInput)
                    //Here, we call the onInputValueChange lambda function
                    //and pass the value of the input field as a parameter
                    //This is so that we can update the value of the inputField
                    // onInputValueChange(it)
                }
            )

                //Here, we call the PrimaryTextButton UI Element
                Row {
                    PrimaryTextButton(text = stringResource(id =
                        R.string.button_click)) {
                        onButtonClick()
                    }
                    PrimaryTextButton(text = stringResource(id =
                        R.string.button_navigate)) {
                        navigateFromHomeToResult()
                    }
                }
            }
        }
        //Here, we use items to display a list of items inside the LazyColumn
        //This is the RecyclerView replacement
        //We pass the listData as a parameter
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Here, we call the OnBackgroundItemText UI Element
                OnBackgroundItemText(text = item.name)
            }
        }
    }

                }

// Here, we create a preview function of the Home composable (Commit 2 is before the new PreviewHome)
@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme { // Wrapped in theme for proper preview
        // Pass dummy data for preview
        HomeContent(
            listData = remember { mutableStateListOf(Student("Tanu"), Student("Tina"), Student("Tono"))},
            inputField = Student(""),
            onInputValueChange = {},
            onButtonClick = {},
            navigateFromHomeToResult = {}
        )
    }
}

//Here, we create a composable function called App
//This will be the root composable of the app
@Composable
fun App(navController: NavHostController) {
    //Here, we use NavHost to create a navigation graph
    //We pass the navController as a parameter
    //We also set the startDestination to "home"
    //This means that the app will start with the Home composable
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        //Here, we create a route called "home"
        //We pass the Home composable as a parameter
        //This means that when the app navigates to "home",
        //the Home composable will be displayed
        composable("home") {
            //Here, we pass a lambda function that navigates to "resultContent"
            //and pass the listData as a parameter
            Home { navController.navigate(
                "resultContent/?listData=$it")
            }
        }
//Here, we create a route called "resultContent"
//We pass the ResultContent composable as a parameter
//This means that when the app navigates to "resultContent",
//the ResultContent composable will be displayed
        //You can also define arguments for the route
        //Here, we define a String argument called "listData"
        //We use navArgument to define the argument
        //We use NavType.StringType to define the type of the argument
        composable(
            "resultContent/?listData={listData}",
            arguments = listOf(navArgument("listData") {
                type = NavType.StringType }
            )
        ) {
            //Here, we pass the value of the argument to the ResultContent  composable
            ResultContent(
                it.arguments?.getString("listData").orEmpty()
            )
        }
    }
}

//Here, we create a composable function called ResultContent
//ResultContent accepts a String parameter called listData from the Home composable
//then displays the value of listData to the screen
@Composable
fun ResultContent(listDataJson: String) {

    // 1. Setup Moshi (Sama seperti di Home)
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // 2. Buat Adapter untuk List<Student>
    val type = com.squareup.moshi.Types.newParameterizedType(List::class.java, Student::class.java)
    val adapter = moshi.adapter<List<Student>>(type)

    // 3. Deserialisasi JSON String kembali menjadi List<Student>?
    // Kita perlu menangani potensi error parsing, default ke list kosong jika gagal
    val studentList: List<Student> = adapter.fromJson(listDataJson) ?: emptyList()

    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tampilkan Judul (Opsional, Anda bisa menambahkan judul di sini)
        OnBackgroundTitleText(text = "Hasil Data Mahasiswa")

        // 4. Gunakan LazyColumn untuk menampilkan List<Student>
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(studentList) { item ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Here, we call the OnBackgroundItemText UI Element
                    OnBackgroundItemText(text = item.name)
                }
            }
        }
    }
}