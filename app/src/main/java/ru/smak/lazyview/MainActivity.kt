package ru.smak.lazyview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.smak.lazyview.ui.theme.LazyViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lst = SnapshotStateList<String>()
            LazyViewTheme {
                MainUI(
                    Modifier.fillMaxSize(),
                    onAddItemToRow = {
                        lst.add("Строка ${lst.size+1}")
                    }
                ){
                    LazyR(
                        lst.reversed(),
                        Modifier.fillMaxWidth()
                    )
                    LazyC(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    modifier: Modifier = Modifier,
    onAddItemToRow: ()->Unit = {},
    content: @Composable ColumnScope.()->Unit,
){
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_title))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_splitscreen_24),
                        contentDescription = null,
                        modifier = Modifier.padding(4.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    FilledIconButton(onClick = onAddItemToRow) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_playlist_add_24),
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            content()
        }
    }
}

@Composable
fun LazyC(
    modifier: Modifier = Modifier,
){
    val lst = List(50){
        "Строка ${it+1}."
    }
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        item{
            Text(
                text = "Ленивый столбец:",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,

                )
        }
        items(lst){
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = it,
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(horizontal = 8.dp, vertical = 32.dp),
                    fontSize = 14.sp
                )
            }

        }
    }
}

@Preview
@Composable
fun LazyCPreview(){
    LazyC(Modifier.fillMaxSize())
}

@Composable
fun LazyR(
    lst: List<String>,
    modifier: Modifier = Modifier,
){
    Row(modifier = modifier) {
        LazyRow(
            modifier = Modifier.height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .rotate(-90f)
                        .width(100.dp)
                ) {
                    Text(
                        text = "Ленивая\nстрока:",
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            items(lst) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(horizontal = 8.dp, vertical = 32.dp),
                        fontSize = 14.sp
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun LazyRPreview(){
    LazyR(
        listOf("1", "2", "3"),
        Modifier.fillMaxWidth()
    )
}
